package com.alex.defs

import com.alex.filestore.Store
import com.alex.io.InputStream
import java.io.ByteArrayOutputStream

class BASDefinition(var id: Int) : Cloneable {
    var animationData: Array<IntArray?>? = null
    var standAnimation: Int
    var walkAnimation: Int
    var runAnimation: Int
    var backwardsAnimation: Int = 0
    var strafeRightAnimation: Int
    var strafeLeftAnimation: Int
    var idleAnimation: Int = -1
    var idleWalkingAnimation: Int = -1
    var runWalkingAnimation: Int = 0
    var walkBackwardsAnimation: Int
    var runningAnimation: Int = 0
    var runSpeed: Int = -1
    var walkSpeed: Int = -1
    var movementSpeed: Int
    var strafeSpeed: Int
    var turnSpeed: Int
    var walkingSpeed: Int
    var runningSpeed: Int
    var walkSpeedBackward: Int = 0
    var strafeAnimationData: IntArray? = null
    var backwardStrafeSpeed: Int = 0
    var diagonalMovement: Int = 0
    var animationIndices: Array<IntArray?>? = null
    var animationDataIndices: IntArray? = null

    init {
        standAnimation = -1
        walkAnimation = -1
        runAnimation = -1
        strafeRightAnimation = -1
        strafeLeftAnimation = -1
        idleWalkingAnimation = -1
        walkBackwardsAnimation = -1
        walkingSpeed = -1
        runningSpeed = -1
        strafeSpeed = -1
        movementSpeed = -1
        turnSpeed = -1
    }

    fun decode(buffer: InputStream) {
        try {
            while (true) {
                val opcode = buffer.readUnsignedByte()
                if (opcode == 0) break
                when (opcode) {
                    1 -> {
                        standAnimation = buffer.readSmartInt()
                        walkAnimation = buffer.readSmartInt()
                    }

                    2 -> runAnimation = buffer.readSmartInt()
                    3 -> idleWalkingAnimation = buffer.readSmartInt()
                    4 -> runningAnimation = buffer.readSmartInt()
                    5 -> strafeRightAnimation = buffer.readSmartInt()
                    6 -> strafeLeftAnimation = buffer.readSmartInt()
                    7 -> {
                        walkSpeed = buffer.readSmartInt()
                        backwardStrafeSpeed = buffer.readSmartInt()
                    }

                    8 -> movementSpeed = buffer.readSmartInt()
                    9 -> animationIndices = arrayOf(buffer.readIntArray())
                    10 -> {
                        diagonalMovement = buffer.readSmartInt()
                    }

                    11 -> animationDataIndices = buffer.readIntArray()
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    private fun InputStream.readIntArray(): IntArray? {
        val size = this.readUnsignedByte()
        return if (size > 0) {
            IntArray(size) {
                this.readInt()
            }
        } else {
            null
        }
    }

    fun encode(): ByteArray {
        val stream = ByteArrayOutputStream()

        stream.write(1)
        stream.writeSmartInt(standAnimation)
        stream.writeSmartInt(walkAnimation)
        stream.writeSmartInt(runAnimation)
        stream.writeSmartInt(strafeRightAnimation)
        stream.writeSmartInt(strafeLeftAnimation)
        stream.writeSmartInt(idleWalkingAnimation)
        stream.writeSmartInt(walkBackwardsAnimation)
        stream.writeSmartInt(runningAnimation)
        stream.writeSmartInt(walkSpeed)
        stream.writeSmartInt(backwardStrafeSpeed)
        stream.writeSmartInt(movementSpeed)
        stream.writeIntArray(animationIndices)
        stream.writeSmartInt(diagonalMovement)
        stream.writeIntArray(arrayOf(animationDataIndices))

        return stream.toByteArray()
    }

    private fun ByteArrayOutputStream.writeIntArray(array: Array<IntArray?>?) {
        if (array == null) {
            this.write(0)
            return
        }
        this.write(array.size)
        for (subArray in array) {
            if (subArray == null) {
                this.write(0)
                continue
            }
            this.write(subArray.size)
            for (item in subArray) {
                this.writeInt(item)
            }
        }
    }

    fun ByteArrayOutputStream.writeInt(value: Int) {
        this.write((value shr 24) and 0xFF)
        this.write((value shr 16) and 0xFF)
        this.write((value shr 8) and 0xFF)
        this.write(value and 0xFF)
    }

    private fun ByteArrayOutputStream.writeSmartInt(value: Int) {
        this.write((value shr 8) and 0xFF)
        this.write(value and 0xFF)
    }

    fun write(cache: Store): Boolean {
        return cache.indexes[2].putFile(32, id, encode())
    }

    override fun toString(): String {
        return "$id"
    }
}
