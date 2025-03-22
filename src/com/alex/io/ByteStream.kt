package com.alex.io

class ByteStream
    (@JvmField var Buffer: ByteArray) {
    @JvmField
    var Offset: Int = 0

    fun readDWord(): Int {
        return ((Buffer[(Offset++)].toInt() and 0xFF) shl 24) + ((Buffer[(Offset++)].toInt() and 0xFF) shl 16) + ((Buffer[(Offset++)].toInt() and 0xFF) shl 8) + (Buffer[(Offset++)].toInt() and 0xFF)
    }

    fun readUnsignedByte(): Int {
        return Buffer[(Offset++)].toInt() and 0xFF
    }

    fun readSignedByte(): Byte {
        return Buffer[(Offset++)]
    }

    fun readUnsignedWord(): Int {
        if (this.Offset + 1 < Buffer.size) {
            return ((Buffer[(Offset++)].toInt() and 0xFF) shl 8) + (Buffer[(Offset++)].toInt() and 0xFF)
        }
        return 0
    }

    fun readSignedWord(): Int {
        var i = ((Buffer[(Offset++)].toInt() and 0xFF) shl 8) + (Buffer[(Offset++)].toInt() and 0xFF)
        if (i > 32767) i -= 65536
        return i
    }

    fun readQWord(paramInt: Int): Long {
        val l1 = (readDWord() and -0x1).toLong()
        if (paramInt >= 0) {
            throw NullPointerException()
        }

        val l2 = (readDWord() and -0x1).toLong()
        return (l1 shl 32) + l2
    }

    fun readString(): String {
        val i = this.Offset
        while (Buffer[(Offset++)].toInt() != 10);
        return String(this.Buffer, i, this.Offset - i - 1)
    }

    fun read3Bytes(): Int {
        return ((Buffer[(Offset++)].toInt() and 0xFF) shl 16) + ((Buffer[(Offset++)].toInt() and 0xFF) shl 8) + (Buffer[(Offset++)].toInt() and 0xFF)
    }

    fun readBytes(paramInt: Int): ByteArray {
        val arrayOfByte = ByteArray(paramInt)
        for (i in 0 until paramInt) {
            arrayOfByte[i] = Buffer[(Offset++)]
        }
        return arrayOfByte
    }

    fun readBytes(paramInt1: Int, paramInt2: Int, paramInt3: Int, paramArrayOfByte: ByteArray) {
        for (i in paramInt2 until paramInt2 + paramInt1) paramArrayOfByte[i] = Buffer[(Offset++)]
    }
}

