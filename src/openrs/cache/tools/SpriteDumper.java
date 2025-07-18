package openrs.cache.tools;

import openrs.cache.*;
import openrs.cache.Container;
import openrs.cache.sprite.Sprite;
import openrs.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Dumps all sprite frames.
 */
public class SpriteDumper {

    /**
     * Main method for dumping sprite images and the title background.
     *
     * @param args not used
     * @throws IOException if reading or writing files fails
     */
    public static void main(String[] args) throws IOException {
        File outputDir = new File(Constants.SPRITE_PATH);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ReferenceTable spriteTable = cache.getReferenceTable(8);

            for (int spriteId = 0; spriteId < spriteTable.capacity(); spriteId++) {
                if (spriteTable.getEntry(spriteId) == null)
                    continue;

                Container container = cache.read(8, spriteId);
                Sprite sprite = Sprite.decode(container.getData());

                for (int frame = 0; frame < sprite.size(); frame++) {
                    BufferedImage image = ImageUtils.createColoredBackground(
                            ImageUtils.makeColorTransparent(sprite.getFrame(frame), Color.WHITE),
                            new Color(0xFF00FF, false)
                    );

                    File outFile = new File(Constants.SPRITE_PATH, spriteId + ".png");
                    ImageIO.write(image, "png", outFile);
                }

                double progress = (double) (spriteId + 1) / spriteTable.capacity() * 100;
                System.out.printf("%d out of %d %.2f%%%n", (spriteId + 1), spriteTable.capacity(), progress);
            }

            // Container titleContainer = cache.read(10, cache.getFileId(10, "title.jpg"));
            // byte[] titleBytes = new byte[titleContainer.getData().remaining()];
            // titleContainer.getData().get(titleBytes);
            // Files.write(Paths.get(Constants.SPRITE_PATH).resolve("title.jpg"), titleBytes);
        }
    }
}
