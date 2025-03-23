/**
 * Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.editor.cache.iface.sprites

import java.awt.Color
import java.awt.Image
import java.awt.RenderingHints
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.awt.image.FilteredImageSource
import java.awt.image.ImageFilter
import java.awt.image.RGBImageFilter

/**
 * Utility functions for manipulating images, including flipping, resizing, and color transformations.
 */
object ImageUtils {
    /**
     * Flips the provided image horizontally.
     *
     * @param img The image to flip.
     * @return A new `BufferedImage` that is the horizontal flip of the provided image.
     */
    @JvmStatic
    fun horizontalFlip(img: BufferedImage): BufferedImage {
        val w = img.width
        val h = img.height
        val flippedImage = BufferedImage(w, h, img.type)
        val g = flippedImage.createGraphics()
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null)
        g.dispose()
        return flippedImage
    }

    /**
     * Flips the provided image vertically.
     *
     * @param img The image to flip.
     * @return A new `BufferedImage` that is the vertical flip of the provided image.
     */
    @JvmStatic
    fun verticalFlip(img: BufferedImage): BufferedImage {
        val w = img.width
        val h = img.height
        val flippedImage = BufferedImage(w, h, img.colorModel.transparency)
        val g = flippedImage.createGraphics()
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null)
        g.dispose()
        return flippedImage
    }

    /**
     * Scales the image by a given factor, maintaining aspect ratio.
     *
     * @param src The source image to scale.
     * @param factor The factor by which to scale the image.
     * @return A new `BufferedImage` that is the scaled version of the provided image.
     */
    fun getScaledImage(
        src: BufferedImage,
        factor: Double,
    ): BufferedImage {
        var factor = factor
        var finalw = src.width
        var finalh = src.height
        if (src.width > src.height) {
            factor = (src.height.toDouble() / src.width.toDouble())
            finalh = (finalw * factor).toInt()
        } else {
            factor = (src.width.toDouble() / src.height.toDouble())
            finalw = (finalh * factor).toInt()
        }

        val resizedImg = BufferedImage(finalw, finalh, BufferedImage.TRANSLUCENT)
        val g2 = resizedImg.createGraphics()
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        g2.drawImage(src, 0, 0, finalw, finalh, null)
        g2.dispose()
        return resizedImg
    }

    /**
     * Makes the specified color in the image transparent.
     *
     * @param im The image to modify.
     * @param color The color to make transparent.
     * @return A new `BufferedImage` with the specified color made transparent.
     */
    fun makeColorTransparent(
        im: BufferedImage,
        color: Color,
    ): BufferedImage {
        val filter: ImageFilter =
            object : RGBImageFilter() {
                var markerRGB: Int = color.rgb or -0x1000000

                override fun filterRGB(
                    x: Int,
                    y: Int,
                    rgb: Int,
                ): Int =
                    if ((rgb or -0x1000000) == markerRGB) {
                        0x00FFFFFF and rgb
                    } else {
                        rgb
                    }
            }

        return imageToBufferedImage(
            Toolkit.getDefaultToolkit().createImage(FilteredImageSource(im.source, filter)),
        )
    }

    /**
     * Converts an `Image` object to a `BufferedImage`.
     *
     * @param img The image to convert.
     * @return A `BufferedImage` representing the provided image.
     */
    @JvmStatic
    fun imageToBufferedImage(img: Image): BufferedImage {
        if (img is BufferedImage) {
            return img
        }

        val bimage = BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB)

        val bGr = bimage.createGraphics()
        bGr.drawImage(img, 0, 0, null)
        bGr.dispose()
        return bimage
    }

    /**
     * Converts a `BufferedImage` to the specified image type.
     *
     * @param src The source image to convert.
     * @param bufImgType The image type to convert to (e.g., `BufferedImage.TYPE_INT_ARGB`).
     * @return A new `BufferedImage` of the specified type.
     */
    fun convert(
        src: BufferedImage,
        bufImgType: Int,
    ): BufferedImage {
        val img = BufferedImage(src.width, src.height, bufImgType)
        val g2d = img.createGraphics()
        g2d.drawImage(src, 0, 0, null)
        g2d.dispose()
        return img
    }

    /**
     * Creates a new image by drawing the original image on top of a colored background.
     *
     * @param image The original image.
     * @param color The background color.
     * @return A new `BufferedImage` with the original image overlaid on a colored background.
     */
    fun createColoredBackground(
        image: BufferedImage,
        color: Color?,
    ): BufferedImage {
        val copy = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
        val g2d = copy.createGraphics()
        g2d.color = color
        g2d.fillRect(0, 0, copy.width, copy.height)
        g2d.drawImage(image, 0, 0, null)
        g2d.dispose()
        return copy
    }

    /**
     * Resizes an image to the specified width and height.
     *
     * @param img The image to resize.
     * @param newW The new width of the image.
     * @param newH The new height of the image.
     * @return A new `BufferedImage` with the specified dimensions.
     */
    @JvmStatic
    fun resize(
        img: BufferedImage,
        newW: Int,
        newH: Int,
    ): BufferedImage {
        var newW = newW
        var newH = newH
        if (newW <= 0) newW = img.width
        if (newH <= 0) newH = img.height
        val tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH)
        val dimg = BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB)
        val g2d = dimg.createGraphics()
        g2d.drawImage(tmp, 0, 0, null)
        g2d.dispose()
        return dimg
    }

    /**
     * Recolors an entire image to a specified color.
     *
     * @param image The image to recolor.
     * @param col The color to apply to the image.
     * @return A new `BufferedImage` with the recolored image.
     */
    @JvmStatic
    fun colorImage(
        image: BufferedImage,
        col: Color,
    ): BufferedImage {
        val width = image.width
        val height = image.height
        val raster = image.raster

        for (xx in 0 until width) {
            for (yy in 0 until height) {
                val pixels = raster.getPixel(xx, yy, null as IntArray?)
                pixels[0] = col.red
                pixels[1] = col.green
                pixels[2] = col.blue
                raster.setPixel(xx, yy, pixels)
            }
        }
        return image
    }
}
