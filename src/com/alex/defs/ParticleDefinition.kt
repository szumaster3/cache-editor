package com.alex.defs

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.io.OutputStream

data class ParticleDefinition(
    val id: Int,
) : Cloneable {
    var isActiveFirst: Boolean = true
    var opcode2: Int = 0
    var minimumAngleH: Short = 0
    var maximumAngleH: Short = 0
    var minimumAngleV: Short = 0
    var maximumAngleV: Short = 0
    var minimumSpeed: Int = 0
    var maximumSpeed: Int = 0
    var minSize: Int = 0
    var maxSize: Int = 0
    var startColorMin: Int = 0
    var startColorMax: Int = 0
    var minLifetime: Int = 0
    var maxLifetime: Int = 0
    var minParticleRate: Int = 0
    var maxParticleRate: Int = 0
    var intensity: Int = 0
    var texture: Int = 0
    var isPeriodic: Boolean = true
    var fadeColor: Int = 0
    var fadeAlpha: Int = 0
    var changeSpeed: Int = 0
    var endSpeed: Int = 0
    var sizeChange: Int = 0
    var sizeVariance: Int = 0
    var particleType: Int = 0
    var uniformColorVariance: Boolean = true
    var lifetime: Int = 0
    var minimumSetting: Int = 0
    var fadingColor: Int = 0
    var fadingAlpha: Int = 0
    var angleH: Int = 0
    var angleV: Int = 0
    var particleColor: Int = 0
    var isUniform: Boolean = true
    var particleRate: Int = 0
    var particleArray1: IntArray? = null
    var particleArray2: IntArray? = null
    var particleArray3: IntArray? = null

    fun decode(stream: InputStream) {
        try {
            while (true) {
                val opcode = stream.readUnsignedByte()
                if (opcode == 0) break
                when (opcode) {
                    1 -> {
                        minimumAngleH = stream.readUnsignedShort().toShort()
                        maximumAngleH = stream.readUnsignedShort().toShort()
                        minimumAngleV = stream.readUnsignedShort().toShort()
                        maximumAngleV = stream.readUnsignedShort().toShort()
                    }

                    2 -> opcode2 = stream.readUnsignedByte()
                    3 -> {
                        minimumSpeed = stream.readInt()
                        maximumSpeed = stream.readInt()
                    }

                    4 -> {
                        particleType = stream.readUnsignedByte()
                        intensity = stream.readByte()
                    }

                    5 -> {
                        minSize = stream.readUnsignedShort()
                        maxSize = minSize
                    }

                    6 -> {
                        startColorMin = stream.readInt()
                        startColorMax = stream.readInt()
                    }

                    7 -> {
                        minLifetime = stream.readUnsignedShort()
                        maxLifetime = stream.readUnsignedShort()
                    }

                    8 -> {
                        minParticleRate = stream.readUnsignedShort()
                        maxParticleRate = stream.readUnsignedShort()
                    }

                    9 -> {
                        val length = stream.readUnsignedByte()
                        particleArray1 = IntArray(length) { stream.readUnsignedShort() }
                    }

                    10 -> {
                        val length = stream.readUnsignedByte()
                        particleArray2 = IntArray(length) { stream.readUnsignedShort() }
                    }

                    12 -> particleArray3 = IntArray(1) { stream.readByte() }
                    13 -> intensity = stream.readByte()
                    14 -> texture = stream.readUnsignedShort()
                    15 -> {
                        isActiveFirst = stream.readUnsignedByte() == 1
                        sizeVariance = stream.readUnsignedShort()
                        lifetime = stream.readUnsignedShort()
                        isPeriodic = stream.readUnsignedByte() == 1
                    }

                    17 -> angleH = stream.readUnsignedShort()
                    18 -> fadeColor = stream.readInt()
                    19 -> minimumSetting = stream.readUnsignedByte()
                    20 -> fadingColor = stream.readUnsignedByte()
                    21 -> fadingAlpha = stream.readUnsignedByte()
                    22 -> endSpeed = stream.readInt()
                    23 -> changeSpeed = stream.readUnsignedByte()
                    24 -> uniformColorVariance = false
                    25 -> {
                        val length = stream.readUnsignedByte()
                        particleArray3 = IntArray(length) { stream.readUnsignedShort() }
                    }

                    26 -> uniformColorVariance = false
                    27 -> sizeChange = stream.readUnsignedShort()
                    28 -> particleRate = stream.readShort()
                    29 -> isUniform = true
                    30 -> uniformColorVariance = false
                    32 -> uniformColorVariance = false
                    33 -> isUniform = true
                    34 -> uniformColorVariance = false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun encode(): ByteArray {
        val stream = OutputStream()
        var length: Int

        if (minimumAngleH.toInt() != 0 || maximumAngleH.toInt() != 0 || minimumAngleV.toInt() != 0 || maximumAngleV.toInt() != 0) {
            stream.writeByte(1)
            stream.writeShort(minimumAngleH.toInt())
            stream.writeShort(maximumAngleH.toInt())
            stream.writeShort(minimumAngleV.toInt())
            stream.writeShort(maximumAngleV.toInt())
        }

        if (opcode2 != 0) {
            stream.writeByte(2)
            stream.writeByte(opcode2)
        }

        if (maximumSpeed > 0) {
            stream.writeByte(3)
            stream.writeInt(minimumSpeed)
            stream.writeInt(maximumSpeed)
        }

        if (particleType != 0 && intensity != 0) {
            stream.writeByte(4)
            stream.writeByte(particleType)
            stream.writeByte(intensity)
        }

        if (minSize == maxSize) {
            stream.writeByte(5)
            stream.writeShort(minSize)
        } else {
            stream.writeByte(31)
            stream.writeShort(minSize)
            stream.writeShort(maxSize)
        }

        if (startColorMin != 0 || startColorMax != 0) {
            stream.writeByte(6)
            stream.writeInt(startColorMin)
            stream.writeInt(startColorMax)
        }

        if (maxLifetime > 0) {
            stream.writeByte(7)
            stream.writeShort(minLifetime)
            stream.writeShort(maxLifetime)
        }

        if (maxParticleRate > 0) {
            stream.writeByte(8)
            stream.writeShort(minParticleRate)
            stream.writeShort(maxParticleRate)
        }

        if (particleArray1 != null) {
            stream.writeByte(9)
            length = particleArray1!!.size
            stream.writeByte(length)
            for (i in 0 until length) {
                stream.writeShort(particleArray1!![i])
            }
        }

        if (particleArray2 != null) {
            stream.writeByte(10)
            length = particleArray2!!.size
            stream.writeByte(length)
            for (i in 0 until length) {
                stream.writeShort(particleArray2!![i])
            }
        }

        if (particleArray3 != null) {
            stream.writeByte(12)
            length = particleArray3!!.size
            stream.writeByte(length)
            for (i in 0 until length) {
                stream.writeByte(particleArray3!![i])
            }
        }

        stream.writeByte(16)
        stream.writeByte(if (isActiveFirst) 1 else 0)
        stream.writeShort(sizeVariance)
        stream.writeShort(lifetime)
        stream.writeByte(if (isPeriodic) 1 else 0)

        if (angleH != 0) {
            stream.writeByte(17)
            stream.writeShort(angleH)
        }

        if (fadeColor != 0) {
            stream.writeByte(18)
            stream.writeInt(fadeColor)
        }

        if (minimumSetting != 0) {
            stream.writeByte(19)
            stream.writeByte(minimumSetting)
        }

        if (fadingColor != 0) {
            stream.writeByte(20)
            stream.writeByte(fadingColor)
        }

        if (fadingAlpha != 0) {
            stream.writeByte(21)
            stream.writeByte(fadingAlpha)
        }

        if (endSpeed != 0) {
            stream.writeByte(22)
            stream.writeInt(endSpeed)
        }

        if (changeSpeed != 0) {
            stream.writeByte(23)
            stream.writeByte(changeSpeed)
        }

        if (!uniformColorVariance) {
            stream.writeByte(24)
        }

        if (particleArray3 != null) {
            stream.writeByte(25)
            length = particleArray3!!.size
            stream.writeByte(length)
            for (i in 0 until length) {
                stream.writeShort(particleArray3!![i])
            }
        }

        if (sizeChange != 0) {
            stream.writeByte(27)
            stream.writeShort(sizeChange)
        }

        if (particleRate != 0) {
            stream.writeByte(28)
            stream.writeShort(particleRate)
        }

        if (isUniform) {
            stream.writeByte(30)
        }

        if (uniformColorVariance) {
            stream.writeByte(32)
        }

        if (isUniform) {
            stream.writeByte(33)
        }

        if (uniformColorVariance) {
            stream.writeByte(34)
        }

        stream.writeByte(0)
        return ByteArray(stream.offset).also { payload ->
            stream.setOffset(0)
            stream.getBytes(payload, 0, payload.size)
        }
    }

    fun write(cache: Cache): Boolean = cache.indexes[27].putFile(0, id, encode())

    override fun toString(): String = "$id"
}
