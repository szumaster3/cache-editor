package openrs.cache.tools;

import com.cache.util.XTEAManager;
import openrs.cache.*;
import openrs.cache.ReferenceTable.Entry;
import openrs.cache.type.CacheIndex;
import openrs.cache.util.CompressionUtils;
import openrs.util.crypto.Djb2;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * Utility tool to dump maps.
 */
public class MapDumper {

    public enum Format {
        BINARY,
        CSV
    }

    /**
     * Entry point prompt.
     */
    public static void launch(JFrame parent) {
        String[] options = {"BINARY", "CSV"};
        int choice = JOptionPane.showOptionDialog(
                parent,
                "Select output format for map_index",
                "Choose Format",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.CLOSED_OPTION) {
            System.out.println("Cancelled operation.");
            return;
        }

        Format format = (choice == 1) ? Format.CSV : Format.BINARY;

        Thread dumpThread = new Thread(() -> {
            try {
                runDump(format);
                System.out.println("Map Dumper finished.");
            } catch (Exception e) {
                System.err.println("Map Dumper failed: " + e.getMessage());
                e.printStackTrace();
            }
        });

        dumpThread.start();
        System.out.println("Map Dumper started.");
    }

    /**
     * Main method to dump maps and landscapes from cache.
     * @param format output format for map_index (BINARY or CSV)
     * @throws IOException on IO failure
     */
    public static void runDump(Format format) throws IOException {
        File mapDir = new File(Constants.MAP_PATH);
        if (!mapDir.exists()) mapDir.mkdirs();

        if (!XTEAManager.load(new File(Constants.XTEAS_PATH))) {
            throw new IOException("Failed to load XTEA keys");
        }

        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ReferenceTable index = cache.getReferenceTable(5);
            if (index == null) {
                throw new IOException("Index 5 not found");
            }

            Map<Integer, Integer> mapFiles = new HashMap<>();
            Map<Integer, Integer> landFiles = new HashMap<>();

            int capacity = index.capacity();

            // Map region IDs to map and landscape file IDs.
            for (int fileId = 0; fileId < capacity; fileId++) {
                Entry entry = index.getEntry(fileId);
                if (entry == null) continue;

                int ident = entry.getIdentifier();
                if (ident == -1) continue;

                for (int x = 0; x <= 255; x++) {
                    for (int y = 0; y <= 255; y++) {
                        String mapName = "m" + x + "_" + y;
                        String landName = "l" + x + "_" + y;
                        int mapHash = Djb2.hash(mapName);
                        int landHash = Djb2.hash(landName);

                        if (ident == mapHash) {
                            mapFiles.put((x << 8) | y, fileId);
                        }
                        if (ident == landHash) {
                            landFiles.put((x << 8) | y, fileId);
                        }
                    }
                }
            }

            Set<Integer> regionIds = new HashSet<>(mapFiles.keySet());
            regionIds.retainAll(landFiles.keySet());

            System.out.println("Regions with both map and landscape: " + regionIds.size());

            saveMapIndex(new File(Constants.DUMP_PATH), regionIds, landFiles, mapFiles, format);

            int mapCount = 0;
            int landCount = 0;

            for (int regionId : regionIds) {
                int x = (regionId >> 8) & 0xFF;
                int y = regionId & 0xFF;

                int[] keys = XTEAManager.lookup(regionId);

                int mapFileId = mapFiles.get(regionId);
                int landFileId = landFiles.get(regionId);

                try {
                    byte[] mapRawData = readRawFile(cache, CacheIndex.MAPS.getID(), mapFileId);
                    byte[] mapData = tryDecompress(mapRawData);
                    try (FileOutputStream fos = new FileOutputStream(new File(mapDir, "m" + x + "_" + y + ".gz"))) {
                        fos.write(CompressionUtils.gzip(mapData));
                    }
                    mapCount++;
                } catch (Exception e) {
                    System.err.println("Failed to dump map: m" + x + "_" + y);
                    e.printStackTrace();
                }

                try {
                    byte[] landRawData = readRawFile(cache, CacheIndex.MAPS.getID(), landFileId);
                    byte[] landData = tryDecompress(landRawData);
                    try (FileOutputStream fos = new FileOutputStream(new File(mapDir, "l" + x + "_" + y + ".gz"))) {
                        fos.write(CompressionUtils.gzip(landData));
                    }
                    landCount++;
                } catch (Exception e) {
                    System.err.println("Failed to dump landscape: l" + x + "_" + y);
                    e.printStackTrace();
                }
            }

            System.out.printf("Dumped %d map files and %d landscape files.%n", mapCount, landCount);
        }
    }

    /**
     * Reads raw file bytes from the cache without decompressing.
     * @param cache Cache instance
     * @param index Cache index (e.g. 5 for maps)
     * @param fileId file id in index
     * @return raw file bytes
     * @throws IOException on failure
     */
    private static byte[] readRawFile(Cache cache, int index, int fileId) throws IOException {
        FileStore store = cache.getStore();
        ByteBuffer buffer = store.read(index, fileId);
        if (buffer == null) {
            throw new IOException("File data is null for index " + index + " fileId " + fileId);
        }
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return data;
    }

    /**
     * Tries to decompress data as gzip or bzip2. If both fail, returns original data.
     * @param data input data bytes
     * @return decompressed bytes or original if not compressed
     * @throws IOException on decompression failure
     */
    private static byte[] tryDecompress(byte[] data) throws IOException {
        try {
            return decompressGzip(data);
        } catch (IOException e) {
            try {
                return decompressBzip2(data);
            } catch (IOException e2) {
                return data;
            }
        }
    }

    private static byte[] decompressGzip(byte[] data) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             GZIPInputStream gzipIn = new GZIPInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = gzipIn.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        }
    }

    private static byte[] decompressBzip2(byte[] data) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             BZip2CompressorInputStream bzipIn = new BZip2CompressorInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = bzipIn.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        }
    }

    /**
     * Saves map index data.
     *
     * @param dumpDir   output folder
     * @param regionIds set of region ids
     * @param landFiles landscape file id
     * @param mapFiles  map file id
     * @param format    save format (BINARY or CSV)
     */
    private static void saveMapIndex(File dumpDir, Set<Integer> regionIds, Map<Integer, Integer> landFiles, Map<Integer, Integer> mapFiles, Format format) {
        switch (format) {
            case BINARY: {
                File mapIndexFile = new File(dumpDir, "map_index");
                try (RandomAccessFile raf = new RandomAccessFile(mapIndexFile, "rw")) {
                    raf.setLength(0);
                    raf.writeShort(regionIds.size());
                    for (int regionId : regionIds) {
                        raf.writeShort(regionId);
                        raf.writeShort(landFiles.get(regionId));
                        raf.writeShort(mapFiles.get(regionId));
                    }
                    System.out.println("Binary map_index generated with " + regionIds.size() + " entries.");
                } catch (IOException e) {
                    System.err.println("Failed to save binary map_index");
                    e.printStackTrace();
                }
                break;
            }
            case CSV: {
                File csvFile = new File(dumpDir, "map_index.csv");
                try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
                    writer.println("regionId,x,y,landscapeFileId,mapFileId");
                    for (int regionId : regionIds) {
                        int x = (regionId >> 8) & 0xFF;
                        int y = regionId & 0xFF;
                        int landFileId = landFiles.get(regionId);
                        int mapFileId = mapFiles.get(regionId);
                        writer.printf("%d,%d,%d,%d,%d%n", regionId, x, y, landFileId, mapFileId);
                    }
                    System.out.println("CSV map_index.csv generated with " + regionIds.size() + " entries.");
                } catch (IOException e) {
                    System.err.println("Failed to save CSV map_index.csv");
                    e.printStackTrace();
                }
                break;
            }
            default:
                System.err.println("Unsupported format: " + format);
        }
    }
}
