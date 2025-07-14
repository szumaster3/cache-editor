package com.cache.defs

import com.cache.store.Cache
import com.cache.io.InputStream
import com.cache.io.OutputStream

class MagnetDefinition(
    val id: Int,
) : Cloneable {
    var intensity: Int = 0
    var positionX: Int = 0
    var positionY: Int = 0
    var positionZ: Int = 0
    var velocityX: Int = 0
    var velocityY: Int = 0
    var velocityZ: Int = 0
    var unknownParam: Int = 0
    var radius: Int = 0
    var type: Int = 0
    var isActive: Boolean = false

    init {
        type = 0
        isActive = false
    }

    fun decode(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) break
            when (opcode) {
                1 -> intensity = stream.readUnsignedShort()
                2 -> stream.readUnsignedByte()
                3 -> {
                    positionX = stream.readInt()
                    positionY = stream.readInt()
                    positionZ = stream.readInt()
                }

                4 -> {
                    velocityX = stream.readUnsignedByte()
                    velocityY = stream.readInt()
                }

                6 -> radius = stream.readUnsignedByte()
                8 -> type = 4
                9 -> unknownParam = -1
                10 -> isActive = true
            }
        }
    }

    fun encode(): ByteArray {
        val stream = OutputStream()

        stream.writeByte(1)
        stream.writeShort(intensity)

        stream.writeByte(3)
        stream.writeInt(positionX)
        stream.writeInt(positionY)
        stream.writeInt(positionZ)

        stream.writeByte(4)
        stream.writeByte(velocityX)
        stream.writeInt(velocityY)

        stream.writeByte(6)
        stream.writeByte(radius)

        if (type == 4) stream.writeByte(8)

        if (unknownParam == -1) stream.writeByte(9)

        if (isActive) stream.writeByte(10)

        stream.writeByte(0)
        val flipped = ByteArray(stream.offset)
        stream.setOffset(0)
        stream.getBytes(flipped, 0, flipped.size)
        return flipped
    }

    fun write(cache: Cache): Boolean = cache.indexes[27].putFile(1, id, encode())
}
