package com.alex.defs

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.io.OutputStream

data class SpotDefinition(
    var id: Int,
) : Cloneable {
    var modelId: Int = -1
    var animation: Int = 0
    var resizeX: Int = 128
    var resizeY: Int = 128
    var rotation: Int = 0
    var ambient: Int = 0
    var contrast: Int = 0
    var isVisible: Boolean = false
    var visibilityType: Byte = 0
    var defaultSize: Int = 128
    var recolorToFind: IntArray? = null
    var recolorToReplace: IntArray? = null
    var retextureToFind: IntArray? = null
    var retextureToReplace: IntArray? = null

    fun decode(stream: InputStream) {
        try {
            while (true) {
                val opcode = stream.readUnsignedByte()
                if (opcode == 0) break

                when (opcode) {
                    1 -> modelId = stream.readSmartInt()
                    2 -> animation = stream.readSmartInt()
                    4 -> resizeX = stream.readUnsignedShort()
                    5 -> resizeY = stream.readUnsignedShort()
                    6 -> rotation = stream.readUnsignedShort()
                    7 -> ambient = stream.readUnsignedByte()
                    8 -> contrast = stream.readUnsignedByte()
                    9 -> {
                        visibilityType = 3.toByte()
                        defaultSize = 0
                    }
                    10 -> isVisible = true
                    11 -> visibilityType = 1.toByte()
                    12 -> visibilityType = 4.toByte()
                    13 -> visibilityType = 5.toByte()
                    14 -> {
                        visibilityType = 2.toByte()
                        defaultSize = stream.readUnsignedByte()
                    }
                    15 -> {
                        visibilityType = 3.toByte()
                        defaultSize = stream.readUnsignedShort()
                    }
                    16 -> {
                        visibilityType = 3.toByte()
                        defaultSize = stream.readInt()
                    }
                    40 -> {
                        val length = stream.readUnsignedByte()
                        recolorToFind = IntArray(length)
                        recolorToReplace = IntArray(length)
                        repeat(length) { index ->
                            recolorToFind!![index] = stream.readUnsignedShort()
                            recolorToReplace!![index] = stream.readUnsignedShort()
                        }
                    }
                    41 -> {
                        val length = stream.readUnsignedByte()
                        retextureToFind = IntArray(length)
                        retextureToReplace = IntArray(length)
                        repeat(length) { index ->
                            retextureToFind!![index] = stream.readUnsignedShort()
                            retextureToReplace!![index] = stream.readUnsignedShort()
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun encode(): ByteArray {
        val stream = OutputStream()

        if (modelId != -1) {
            stream.writeByte(1)
            stream.writeSmartInt(modelId)
        }

        if (animation != -1) {
            stream.writeByte(2)
            stream.writeSmartInt(animation)
        }

        if (resizeX != 0) {
            stream.writeByte(4)
            stream.writeShort(resizeX)
        }

        if (resizeY != 0) {
            stream.writeByte(5)
            stream.writeShort(resizeY)
        }

        if (rotation != 0) {
            stream.writeByte(6)
            stream.writeShort(rotation)
        }

        if (ambient != 0) {
            stream.writeByte(7)
            stream.writeByte(ambient)
        }

        if (contrast != 0) {
            stream.writeByte(8)
            stream.writeByte(contrast)
        }

        if (visibilityType == 3.toByte() && defaultSize == 0) {
            stream.writeByte(9)
        }

        if (isVisible) {
            stream.writeByte(10)
        }

        if (visibilityType == 1.toByte()) {
            stream.writeByte(11)
        }

        if (visibilityType == 4.toByte()) {
            stream.writeByte(12)
        }

        if (visibilityType == 5.toByte()) {
            stream.writeByte(13)
        }

        if (visibilityType == 4.toByte() && defaultSize > 0) {
            stream.writeByte(14)
            stream.writeByte(defaultSize)
        }

        // 15 & 16 missing :(

        recolorToFind?.let {
            retextureToReplace?.let {
                stream.writeByte(40)
                stream.writeByte(recolorToFind!!.size)
                for (i in recolorToFind!!.indices) {
                    stream.writeShort(recolorToFind!![i])
                    stream.writeShort(recolorToReplace!![i])
                }
            }
        }

        retextureToFind?.let {
            retextureToReplace?.let {
                stream.writeByte(41)
                stream.writeByte(retextureToFind!!.size)
                for (i in retextureToFind!!.indices) {
                    stream.writeShort(retextureToFind!![i])
                    stream.writeShort(retextureToReplace!![i])
                }
            }
        }

        stream.writeByte(0)
        val payload = ByteArray(stream.offset)
        stream.setOffset(0)
        stream.getBytes(payload, 0, payload.size)
        return payload
    }

    fun write(cache: Cache): Boolean = cache.getIndexes()[21].putFile(id shr 8, id and 0xff, encode())

    override fun toString(): String = id.toString()

    @Suppress("UNCHECKED_CAST")
    override fun clone(): SpotDefinition = this.copy()
}
