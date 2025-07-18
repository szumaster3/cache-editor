package openrs.cache.tools;

import com.cache.util.XTEAManager;
import com.displee.cache.CacheLibrary;
import openrs.cache.*;
import com.displee.cache.index.Index;
import com.displee.cache.index.archive.Archive;
import openrs.cache.ReferenceTable.Entry;
import openrs.cache.type.CacheIndex;
import openrs.cache.util.CompressionUtils;
import openrs.util.crypto.Djb2;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
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
        String url = "https://mejrs.github.io/historical?era=rs2_2009_02_01";

        String info = "<html><body width='350px'>" +
                "<b>Information</b>" +
                "<br>" +
                "Dumps map and landscape files from cache to <b>data/dump/maps/</b>." +
                "<br>" +
                "<ul>" +
                "<li><b>mX_Y.gz</b>: compressed map data.</li>" +
                "<li><b>lX_Y.gz</b>: compressed landscape data.</li>" +
                "</ul>" +

                "<b>Buttons</b><br>" +
                "<ul>" +
                "<li><b>BINARY</b>: Generate <b>map_index</b> in binary format.</li>" +
                "<li><b>CSV</b>: Generate <b>map_index</b> in CSV format.</li>" +
                "<li><b>Add Region</b>: Add region from files by coordinates.</li>" +
                "</ul>" +
                "</body></html>";

        JLabel label = new JLabel(info);

        JButton linkButton = new JButton("Open Map");
        linkButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Failed to open link: " + url);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(linkButton);

        String[] options = {"BINARY", "CSV", "Add Region"};

        int choice = JOptionPane.showOptionDialog(
                parent,
                panel,
                "Choose Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.CLOSED_OPTION) {
            System.out.println("Cancelled operation.");
            return;
        }

        if (choice == 0 || choice == 1) {
            Format format = (choice == 1) ? Format.CSV : Format.BINARY;
            startDumpThread(format);
        } else if (choice == 2) {
            String input = JOptionPane.showInputDialog(parent, "Enter region coordinates as x,y (e.g. 50,50):");
            if (input == null || input.trim().isEmpty()) {
                System.out.println("No region specified.");
                return;
            }
            String[] parts = input.trim().split(",");
            if (parts.length != 2) {
                JOptionPane.showMessageDialog(parent, "Invalid input format. Use x,y");
                return;
            }
            try {
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                addRegion(parent, x, y);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(parent, "Coordinates must be numbers.");
            }
        }
    }

    /**
     * Starts a new thread to run the map dump.
     * @param format The format of map_index.
     */
    private static void startDumpThread(Format format) {
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
     * Starts a new thread to add a map region.
     * @param parent The parent JFrame used to display dialogs.
     * @param x The x-coordinate of the region.
     * @param y The y-coordinate of the region.
     */
    private static void addRegion(JFrame parent, int x, int y) {
        Thread addRegionThread = new Thread(() -> {
            try {
                CacheLibrary cache = new CacheLibrary("data/cache/", false, null);
                byte[] mapData = load("data/dump/maps/m" + x + "_" + y + ".gz");
                byte[] landData = load("data/dump/maps/l" + x + "_" + y + ".gz");
                addRegion(cache, x, y, mapData, landData);
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(parent, "Region (" + x + "," + y + ") added to cache."));
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(parent, "Failed to add region: " + e.getMessage()));
            }
        });
        addRegionThread.start();
    }

    /**
     * Adds a new map region to the cache
     * @param cache The instance representing the cache.
     * @param x The x-coordinate of the region.
     * @param y The y-coordinate of the region.
     * @param mapData The raw compressed map data bytes.
     * @param landData The raw compressed landscape data bytes.
     * @throws IOException If the cache index or archive is not found, or IO fails.
     */
    public static void addRegion(CacheLibrary cache, int x, int y, byte[] mapData, byte[] landData) throws IOException {
        final int MAPS_INDEX = 5;
        final int regionId = (x << 8) | y;

        Index mapsIndex = cache.index(MAPS_INDEX);
        if (mapsIndex == null) {
            throw new IOException("Index 5 (maps) not found in cache.");
        }

        Archive regionArchive = mapsIndex.archive(regionId);
        if (regionArchive == null) {
            regionArchive = mapsIndex.add(regionId);
        }

        int compressionType = 2;

        regionArchive.add(0, mapData, compressionType, true);  // true = overwrite
        regionArchive.add(1, landData, compressionType, true);

        mapsIndex.update();
        cache.update();

        System.out.printf("Region (%d,%d) added (regionId=%d)%n", x, y, regionId);
    }

    /**
     * Loads the contents of a file as a byte array.
     * @param path The file path to load.
     * @return The byte contents of the file.
     * @throws IOException If the file does not exist or cannot be read.
     */
    public static byte[] load(String path) throws IOException {
        Path p = new File(path).toPath();
        if (!Files.exists(p)) {
            throw new IOException("File not found: " + path);
        }
        return Files.readAllBytes(p);
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
            openrs.cache.ReferenceTable index = cache.getReferenceTable(5);
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
