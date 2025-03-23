package com.alex.defs

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.io.OutputStream

data class TextureDefinition(
    val id: Int,
) : Cloneable {
    var textureId: Int = 0
    var isVisible: Boolean = false
    var textureIndexes: IntArray? = null
    var textureTypes: IntArray? = null
    var textureValues: IntArray? = null
    var textureRGB: IntArray? = null
    var width: Int = 0
    var height: Int = 0
    var pixels: IntArray? = null

    fun decode(stream: InputStream) {
        textureId = stream.readUnsignedShort()
        isVisible = stream.readByte() != 0

        val count = stream.readUnsignedByte()
        textureIndexes = IntArray(count) { stream.readUnsignedShort() }

        if (count > 1) {
            textureTypes = IntArray(count - 1) { stream.readUnsignedByte() }
            textureValues = IntArray(count - 1) { stream.readUnsignedByte() }
        }

        textureRGB = IntArray(count) { stream.readInt() }

        width = stream.readUnsignedByte()
        height = stream.readUnsignedByte()
    }

    fun encode(): ByteArray {
        val stream = OutputStream()
        stream.writeShort(textureId)
        stream.writeByte(if (isVisible) 1 else 0)

        val count = textureIndexes?.size ?: 0
        stream.writeByte(count)

        textureIndexes?.forEach { stream.writeShort(it) }

        if (count > 1) {
            textureTypes?.forEach { stream.writeByte(it) }
            textureValues?.forEach { stream.writeByte(it) }
        }

        textureRGB?.forEach { stream.writeInt(it) }

        stream.writeByte(width)
        stream.writeByte(height)

        return ByteArray(stream.offset).also {
            stream.setOffset(0)
            stream.getBytes(it, 0, it.size)
        }
    }

    fun write(cache: Cache): Boolean = cache.indexes[9].putFile(id, 0, 2, encode(), null, true, false, -1, -1)

    override fun toString(): String = id.toString()
}
