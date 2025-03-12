package com.alex.loaders.images

import com.alex.io.InputStream
import com.alex.io.OutputStream
import com.alex.store.Store
import java.awt.image.BufferedImage

/**
 * The type Indexed color image file.
 */
class IndexedColorImageFile {
    /**
     * Get images buffered image [ ].
     *
     * @return the buffered image [ ]
     */
    lateinit var images: Array<BufferedImage?>
        private set
    private var pallete: IntArray? = null
    private var pixelsIndexes: Array<IntArray?>? = null
    private var alpha: Array<ByteArray?>? = null
    private var usesAlpha: BooleanArray? = null
    /**
     * Gets biggest width.
     *
     * @return the biggest width
     */
    /**
     * Sets biggest width.
     *
     * @param biggestWidth the biggest width
     */
    var biggestWidth: Int = 0
    /**
     * Gets biggest height.
     *
     * @return the biggest height
     */
    /**
     * Sets biggest height.
     *
     * @param biggestHeight the biggest height
     */
    var biggestHeight: Int = 0

    /**
     * Instantiates a new Indexed color image file.
     *
     * @param images the images
     */
    constructor(vararg images: BufferedImage?) {
        this.images = images as Array<BufferedImage?>
    }

    /**
     * Instantiates a new Indexed color image file.
     *
     * @param cache     the cache
     * @param archiveId the archive id
     * @param fileId    the file id
     */
    constructor(cache: Store, archiveId: Int, fileId: Int) : this(cache, 8, archiveId, fileId)

    /**
     * Instantiates a new Indexed color image file.
     *
     * @param cache     the cache
     * @param idx       the idx
     * @param archiveId the archive id
     * @param fileId    the file id
     */
    constructor(cache: Store, idx: Int, archiveId: Int, fileId: Int) {
        this.decodeArchive(cache, idx, archiveId, fileId)
    }

    /**
     * Decode archive.
     *
     * @param cache     the cache
     * @param idx       the idx
     * @param archiveId the archive id
     * @param fileId    the file id
     */
    fun decodeArchive(cache: Store, idx: Int, archiveId: Int, fileId: Int) {
        val data = cache.indexes[idx].getFile(archiveId, fileId)
        if (data != null) {
            val stream = InputStream(data)
            stream.offset = data.size - 2
            val count = stream.readUnsignedShort()
            this.images = arrayOfNulls(count)
            this.pixelsIndexes = arrayOfNulls(images.size)
            this.alpha = arrayOfNulls(images.size)
            this.usesAlpha = BooleanArray(images.size)
            val imagesMinX = IntArray(images.size)
            val imagesMinY = IntArray(images.size)
            val imagesWidth = IntArray(images.size)
            val imagesHeight = IntArray(images.size)
            stream.offset = data.size - 7 - images.size * 8
            this.biggestWidth = stream.readShort()
            this.biggestHeight = stream.readShort()
            val palleteLength = (stream.readUnsignedByte() and 255) + 1
            var i_20_ = 0
            while (i_20_ < images.size) {
                imagesMinX[i_20_] = stream.readUnsignedShort()
                ++i_20_
            }

            i_20_ = 0
            while (i_20_ < images.size) {
                imagesMinY[i_20_] = stream.readUnsignedShort()
                ++i_20_
            }

            i_20_ = 0
            while (i_20_ < images.size) {
                imagesWidth[i_20_] = stream.readUnsignedShort()
                ++i_20_
            }

            i_20_ = 0
            while (i_20_ < images.size) {
                imagesHeight[i_20_] = stream.readUnsignedShort()
                ++i_20_
            }

            stream.offset = data.size - 7 - images.size * 8 - (palleteLength - 1) * 3
            this.pallete = IntArray(palleteLength)

            i_20_ = 1
            while (i_20_ < palleteLength) {
                pallete!![i_20_] = stream.read24BitInt()
                if (pallete!![i_20_] == 0) {
                    pallete!![i_20_] = 1
                }
                ++i_20_
            }

            stream.offset = 0

            i_20_ = 0
            while (i_20_ < images.size) {
                val pixelsIndexesLength = imagesWidth[i_20_] * imagesHeight[i_20_]
                pixelsIndexes!![i_20_] = IntArray(pixelsIndexesLength)
                alpha!![i_20_] = ByteArray(pixelsIndexesLength)
                val maskData = stream.readUnsignedByte()
                var i_31_: Int
                if ((maskData and 2) == 0) {
                    var var201: Int
                    if ((maskData and 1) == 0) {
                        var201 = 0
                        while (var201 < pixelsIndexesLength) {
                            pixelsIndexes!![i_20_]!![var201] = stream.readByte().toByte().toInt()
                            ++var201
                        }
                    } else {
                        var201 = 0
                        while (var201 < imagesWidth[i_20_]) {
                            i_31_ = 0
                            while (i_31_ < imagesHeight[i_20_]) {
                                pixelsIndexes!![i_20_]!![var201 + i_31_ * imagesWidth[i_20_]] =
                                    stream.readByte().toByte().toInt()
                                ++i_31_
                            }
                            ++var201
                        }
                    }
                } else {
                    usesAlpha!![i_20_] = true
                    var var20 = false
                    if ((maskData and 1) == 0) {
                        i_31_ = 0
                        while (i_31_ < pixelsIndexesLength) {
                            pixelsIndexes!![i_20_]!![i_31_] = stream.readByte().toByte().toInt()
                            ++i_31_
                        }

                        i_31_ = 0
                        while (i_31_ < pixelsIndexesLength) {
                            alpha!![i_20_]!![i_31_] = stream.readByte().toByte()
                            val var21 = alpha!![i_20_]!![i_31_]
                            var20 = var20 or (var21.toInt() != -1)
                            ++i_31_
                        }
                    } else {
                        var var211: Int
                        i_31_ = 0
                        while (i_31_ < imagesWidth[i_20_]) {
                            var211 = 0
                            while (var211 < imagesHeight[i_20_]) {
                                pixelsIndexes!![i_20_]!![i_31_ + var211 * imagesWidth[i_20_]] = stream.readByte()
                                ++var211
                            }
                            ++i_31_
                        }

                        i_31_ = 0
                        while (i_31_ < imagesWidth[i_20_]) {
                            var211 = 0
                            while (var211 < imagesHeight[i_20_]) {
                                alpha!![i_20_]!![i_31_ + var211 * imagesWidth[i_20_]] = stream.readByte().toByte()
                                val i_33_ = alpha!![i_20_]!![i_31_ + var211 * imagesWidth[i_20_]]
                                var20 = var20 or (i_33_.toInt() != -1)
                                ++var211
                            }
                            ++i_31_
                        }
                    }

                    if (!var20) {
                        alpha!![i_20_] = null
                    }
                }

                images[i_20_] = this.getBufferedImage(
                    imagesWidth[i_20_],
                    imagesHeight[i_20_],
                    pixelsIndexes!![i_20_]!!,
                    alpha!![i_20_],
                    usesAlpha!![i_20_]
                )
                ++i_20_
            }
        }
    }

    /**
     * Gets buffered image.
     *
     * @param width          the width
     * @param height         the height
     * @param pixelsIndexes  the pixels indexes
     * @param extraPixels    the extra pixels
     * @param useExtraPixels the use extra pixels
     * @return the buffered image
     */
    fun getBufferedImage(
        width: Int, height: Int, pixelsIndexes: IntArray, extraPixels: ByteArray?, useExtraPixels: Boolean
    ): BufferedImage? {
        if (width > 0 && height > 0) {
            val image = BufferedImage(width, height, 6)
            val rgbArray = IntArray(width * height)
            var i = 0
            var i_43_ = 0
            var i_46_: Int
            var i_47_: Int
            if (useExtraPixels && extraPixels != null) {
                i_46_ = 0
                while (i_46_ < height) {
                    i_47_ = 0
                    while (i_47_ < width) {
                        rgbArray[i_43_++] = extraPixels[i].toInt() shl 24 or pallete!![pixelsIndexes[i] and 255]
                        ++i
                        ++i_47_
                    }
                    ++i_46_
                }
            } else {
                i_46_ = 0
                while (i_46_ < height) {
                    i_47_ = 0
                    while (i_47_ < width) {
                        val i_48_ = pallete!![pixelsIndexes[i++] and 255]
                        rgbArray[i_43_++] = if (i_48_ != 0) -16777216 or i_48_ else 0
                        ++i_47_
                    }
                    ++i_46_
                }
            }

            image.setRGB(0, 0, width, height, rgbArray, 0, width)
            image.flush()
            return image
        } else {
            return null
        }
    }

    /**
     * Encode file byte [ ].
     *
     * @return the byte [ ]
     */
    fun encodeFile(): ByteArray {
        if (this.pallete == null) {
            this.generatePallete()
        }

        val stream = OutputStream()
        var `len$`: Int
        var `i$`: Int
        var container = 0
        while (container < images.size) {
            `len$` = 0
            if (usesAlpha!![container]) {
                `len$` = `len$` or 2
            }

            stream.writeByte(`len$`)

            `i$` = 0
            while (`i$` < pixelsIndexes!![container]!!.size) {
                stream.writeByte(pixelsIndexes!![container]!![`i$`])
                ++`i$`
            }

            if (usesAlpha!![container]) {
                `i$` = 0
                while (`i$` < alpha!![container]!!.size) {
                    stream.writeByte(alpha!![container]!![`i$`].toInt())
                    ++`i$`
                }
            }
            ++container
        }

        container = 0
        while (container < pallete!!.size) {
            stream.write24BitInt(pallete!![container])
            ++container
        }

        if (this.biggestWidth == 0 && this.biggestHeight == 0) {
            val var7 = this.images
            `len$` = var7.size

            `i$` = 0
            while (`i$` < `len$`) {
                val image = var7[`i$`]
                if (image!!.width > this.biggestWidth) {
                    this.biggestWidth = image.width
                }

                if (image.height > this.biggestHeight) {
                    this.biggestHeight = image.height
                }
                ++`i$`
            }
        }

        stream.writeShort(this.biggestWidth)
        stream.writeShort(this.biggestHeight)
        stream.writeByte(pallete!!.size - 1)

        container = 0
        while (container < images.size) {
            stream.writeShort(images[container]!!.minX)
            ++container
        }

        container = 0
        while (container < images.size) {
            stream.writeShort(images[container]!!.minY)
            ++container
        }

        container = 0
        while (container < images.size) {
            stream.writeShort(images[container]!!.width)
            ++container
        }

        container = 0
        while (container < images.size) {
            stream.writeShort(images[container]!!.height)
            ++container
        }

        stream.writeShort(images.size)
        val var71 = ByteArray(stream.offset)
        stream.offset = 0
        stream.getBytes(var71, 0, var71.size)
        return var71
    }

    /**
     * Gets pallete index.
     *
     * @param rgb the rgb
     * @return the pallete index
     */
    fun getPalleteIndex(rgb: Int): Int {
        if (this.pallete == null) {
            this.pallete = IntArray(1)
        }

        for (var3 in pallete!!.indices) {
            if (pallete!![var3] == rgb) {
                return var3
            }
        }

        if (pallete!!.size == 256) {
            println("Pallete to big, please reduce images quality.")
            return 0
        } else {
            val var31 = IntArray(pallete!!.size + 1)
            System.arraycopy(this.pallete, 0, var31, 0, pallete!!.size)
            var31[pallete!!.size] = rgb
            this.pallete = var31
            return pallete!!.size - 1
        }
    }

    /**
     * Add image int.
     *
     * @param image the image
     * @return the int
     */
    fun addImage(image: BufferedImage?): Int {
        val newImages = images.copyOf(images.size + 1)
        newImages[images.size] = image
        this.images = newImages
        this.pallete = null
        this.pixelsIndexes = null
        this.alpha = null
        this.usesAlpha = null
        return images.size - 1
    }

    /**
     * Replace image.
     *
     * @param image the image
     * @param index the index
     */
    fun replaceImage(image: BufferedImage?, index: Int) {
        images[index] = image
        this.pallete = null
        this.pixelsIndexes = null
        this.alpha = null
        this.usesAlpha = null
    }

    /**
     * Generate pallete.
     */
    fun generatePallete() {
        this.pixelsIndexes = arrayOfNulls(images.size)
        this.alpha = arrayOfNulls(images.size)
        this.usesAlpha = BooleanArray(images.size)

        for (index in images.indices) {
            val image = images[index]
            val rgbArray = IntArray(image!!.width * image.height)
            image.getRGB(0, 0, image.width, image.height, rgbArray, 0, image.width)
            pixelsIndexes!![index] = IntArray(image.width * image.height)
            alpha!![index] = ByteArray(image.width * image.height)

            for (pixel in pixelsIndexes!![index]!!.indices) {
                val rgb = rgbArray[pixel]
                val medintrgb = this.convertToMediumInt(rgb)
                val i = this.getPalleteIndex(medintrgb)
                pixelsIndexes!![index]!![pixel] = i
                if (rgb shr 24 != 0) {
                    alpha!![index]!![pixel] = (rgb shr 24).toByte()
                    usesAlpha!![index] = true
                }
            }
        }
    }

    /**
     * Convert to medium int int.
     *
     * @param rgb the rgb
     * @return the int
     */
    fun convertToMediumInt(rgb: Int): Int {
        var rgb = rgb
        val out = OutputStream(4)
        out.writeInt(rgb)
        val stream = InputStream(out.buffer!!)
        stream.offset = 1
        rgb = stream.read24BitInt()
        return rgb
    }
}
