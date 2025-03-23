package com.editor.cache.iface.text

import com.alex.defs.interfaces.ComponentDefinition
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object FontDecoding {
    @JvmStatic
    fun getTextArray(component: ComponentDefinition): Array<BufferedImage?> {
        val arr = arrayOfNulls<BufferedImage>(component.text.length)
        var counter = 0
        for (c in component.text.toCharArray()) {
            try {
                arr[counter] =
                    ImageIO.read(File("data/export/fonts/" + component.fontId + "_" + getCharIndex(c) + ".png"))
            } catch (e: IOException) {
                // e.printStackTrace();
            }

            counter++
        }
        return arr
    }

    fun getCharIndex(c: Char): Int {
        if (Character.isDigit(c)) return 48 + Character.getNumericValue(c)
        if (Character.isUpperCase(c)) return 55 + Character.getNumericValue(c)
        if (Character.isLowerCase(c)) return 87 + Character.getNumericValue(c)
        return 0
    }
}
