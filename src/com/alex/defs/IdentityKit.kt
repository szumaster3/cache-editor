package com.alex.defs

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.io.OutputStream

class IdentityKit(
    val id: Int,
) : Cloneable {
    var bodyPartId: Int = -1
    var bodyModels: IntArray? = null
    var headModels: IntArray = IntArray(5) { -1 }
    var nonSelectable: Boolean = false
    var recolorToFind: IntArray? = null
    var recolorToReplace: IntArray? = null
    var retextureToFind: IntArray? = null
    var retextureToReplace: IntArray? = null

    fun decode(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) break
            when (opcode) {
                1 -> bodyPartId = stream.readUnsignedByte()
                2 -> {
                    val length = stream.readUnsignedByte()
                    bodyModels = IntArray(length)
                    for (index in 0 until length) {
                        bodyModels!![index] = stream.readSmartInt()
                    }
                }
                3 -> nonSelectable = true
                40 -> {
                    val length = stream.readUnsignedByte()
                    recolorToFind = IntArray(length)
                    recolorToReplace = IntArray(length)
                    for (index in 0 until length) {
                        recolorToFind!![index] = stream.readUnsignedShort()
                        recolorToReplace!![index] = stream.readUnsignedShort()
                    }
                }
                41 -> {
                    val length = stream.readUnsignedByte()
                    retextureToFind = IntArray(length)
                    retextureToReplace = IntArray(length)
                    for (index in 0 until length) {
                        retextureToFind!![index] = stream.readUnsignedShort()
                        retextureToReplace!![index] = stream.readUnsignedShort()
                    }
                }
                in 60..69 -> headModels[opcode - 60] = stream.readSmartInt()
            }
        }
    }

    fun encode(): ByteArray? {
        val stream = OutputStream()

        if (bodyPartId != -1) {
            stream.writeByte(1)
            stream.writeByte(bodyPartId)
        }

        bodyModels?.let {
            stream.writeByte(2)
            stream.writeByte(it.size)
            for (index in it.indices) {
                stream.writeSmartInt(it[index])
            }
        }

        if (nonSelectable) {
            stream.writeByte(3)
        }

        if (recolorToFind != null && recolorToReplace != null) {
            stream.writeByte(40)
            stream.writeByte(recolorToFind!!.size)
            for (index in recolorToFind!!.indices) {
                stream.writeShort(recolorToFind!![index])
                stream.writeShort(recolorToReplace!![index])
            }
        }

        if (retextureToFind != null && retextureToReplace != null) {
            stream.writeByte(41)
            stream.writeByte(retextureToFind!!.size)
            for (index in retextureToFind!!.indices) {
                stream.writeShort(retextureToFind!![index])
                stream.writeShort(retextureToReplace!![index])
            }
        }

        for (index in headModels.indices) {
            stream.writeByte(60 + index)
            stream.writeSmartInt(headModels[index])
        }

        return null
    }

    fun write(cache: Cache): Boolean = cache.indexes[2].putFile(3, id, encode()!!)
}
