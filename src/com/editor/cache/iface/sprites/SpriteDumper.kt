package com.editor.cache.iface.sprites

import com.editor.cache.iface.util.PropertyValues
import console.Main.log
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object SpriteDumper {
    @JvmStatic
    fun dump(cachePath: String?) {
        try {
            SpriteLoader.initStore(cachePath)

            val directory = File(PropertyValues.dump_path)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val size = SpriteLoader.numSprites
            for (i in 0 until size) {
                val s = SpriteLoader.getArchive(i) ?: continue

                for (frame in 0 until s.size()) {
                    val file = File(PropertyValues.dump_path, i.toString() + "_" + frame + ".png")
                    val image = s.getSprite(frame)
                    try {
                        ImageIO.write(image, "png", file)
                    } catch (e: Exception) {
                        log("Sprite Dumper", "Could not dump sprite " + i + " error ->" + e.message)
                        continue
                    }
                }

                val progress = (i + 1).toDouble() / size * 100
                log("Sprite Dumper", (i + 1).toString() + " out of " + size + " " + Math.round(progress) + "%")
            }
        } catch (e: IOException) {
            log("Sprite Dumper", "Error initializing store: " + e.message)
        }
    }
}