package com.editor.cache.iface.sprites

import com.editor.cache.iface.util.PropertyValues
import console.Main.log
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object SpriteDumper {
    @JvmStatic
    fun dump(cachePath: String?, progressMonitor: javax.swing.ProgressMonitor) {
        try {
            SpriteLoader.initStore(cachePath!!)

            val directory = File(PropertyValues.dump_path)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val size = SpriteLoader.numSprites
            val sizeDouble = size.toDouble()
            val noteFormat = "Dumping sprite %d of %d - %d%%"

            for (i in 0 until size) {
                if (progressMonitor.isCanceled) {
                    log("Sprite Dumper", "Sprite dumping canceled.")
                    break
                }

                val s = SpriteLoader.getArchive(i) ?: continue

                for (frame in 0 until s.size()) {
                    val file = File(PropertyValues.dump_path, "${i}_$frame.png")
                    val image = s.getSprite(frame)
                    try {
                        ImageIO.write(image, "png", file)
                    } catch (e: Exception) {
                        log("Sprite Dumper", "Could not dump sprite $i error -> ${e.message}")
                        continue
                    }
                }

                if (i % 5 == 0 || i == size - 1) {
                    val progress = ((i + 1) / sizeDouble) * 100
                    progressMonitor.setProgress(i + 1)
                    progressMonitor.setNote(String.format(noteFormat, i + 1, size, Math.round(progress)))
                }
            }
        } catch (e: IOException) {
            log("Sprite Dumper", "Error initializing store: ${e.message}")
        }
    }
}