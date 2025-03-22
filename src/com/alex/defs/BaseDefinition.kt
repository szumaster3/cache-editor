package com.alex.defs

import com.alex.filestore.Store
import com.alex.io.InputStream
import com.alex.io.OutputStream

class BaseDefinition(val id: Int) : Cloneable {

    var count: Int = 0
    lateinit var shadowed: BooleanArray
    lateinit var transformations: IntArray
    lateinit var skinList: Array<IntArray>
    lateinit var settings: IntArray

    fun decode(stream: InputStream) {
        count = stream.readUnsignedByte()
        transformations = IntArray(count)
        skinList = Array(count) { IntArray(0) }
        shadowed = BooleanArray(count)
        settings = IntArray(count)
        
        for (opcode in 0 until count) {
            transformations[opcode] = stream.readUnsignedByte()
            if (transformations[opcode] == 6) {
                transformations[opcode] = 2
            }
        }
        
        for (index in 0 until count) {
            shadowed[index] = stream.readUnsignedByte() == 1
        }
        
        for (setting in 0 until count) {
            settings[setting] = stream.readUnsignedShort()
        }
        
        for (skin in 0 until count) {
            skinList[skin] = IntArray(stream.readUnsignedByte())
        }
        
        for (skin in 0 until count) {
            for (subSkin in skinList[skin].indices) {
                skinList[skin][subSkin] = stream.readUnsignedByte()
            }
        }
    }

    fun encode(): ByteArray {
        val stream = OutputStream()
        stream.writeByte(transformations.size)
        
        for (opcode in transformations) {
            stream.writeByte(opcode)
        }
        
        for (index in shadowed.indices) {
            stream.writeByte(if (shadowed[index]) 1 else 0)
        }
        
        for (index in settings.indices) {
            stream.writeShort(settings[index])
        }
        
        for (skin in skinList) {
            stream.writeByte(skin.size)
        }
        
        for (skin in skinList) {
            for (subSkin in skin) {
                stream.writeByte(subSkin)
            }
        }
        
        val flipped = ByteArray(stream.offset)
        stream.setOffset(0)
        stream.getBytes(flipped, 0, flipped.size)
        return flipped
    }

    fun write(cache: Store): Boolean {
        return cache.getIndexes()[0].putFile(id, 0, encode())
    }
}
