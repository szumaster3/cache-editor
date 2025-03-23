package com.alex.defs.clientscripts

import com.alex.filestore.Cache
import com.alex.io.InputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ClientScripts {
    var Cache: Cache? = null
    var aCharArray6195: CharArray? = null
    var aString6198: String? = null
    var aCharArray6205: CharArray? = null
    var anIntArray6209: IntArray? = null
    var anIntArray6210: IntArray? = null
    var anIntArray6356: IntArray? = null
    var anIntArray6369: IntArray? = null
    var interfaceText: Array<String?>? = null
    var anInt6361: Int = 0
    var anInt6362: Int = 0
    var anInt6366: Int = 0
    var anInt6359: Int = 0

    var scripts: ArrayList<Any?> = ArrayList()
    var id: Int = 0

    fun write(cache: Cache) {
        cache.indexes[12].putFile(id, 0, encode())
    }

    fun encode(): ByteArray {
        val stream = com.alex.io.OutputStream()

        stream.writeByte(1) // start

        stream.writeByte(0) // end

        val data = ByteArray(stream.getOffset())
        stream.setOffset(0)
        stream.getBytes(data, 0, data.size)
        return data
    }

    fun loadScripts(id: Int) {
        try {
            this.id = id
            val data: ByteArray? =
                Cache!!
                    .indexes[12]
                    .getFile(id, 0)
            if (data!!.isEmpty()) return
            method231(data, -2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    @Suppress("unused")
    fun method231(
        data: ByteArray?,
        arg1: Int,
    ) {
        try {
            val stream = InputStream(data)
            stream.offset = stream.buffer.size - 2
            val i: Int = stream.readUnsignedShort()
            val i_0_: Int = stream.buffer.size - 12 + arg1 - i
            stream.offset = i_0_
            val size: Int = stream.readInt()
            anInt6361 = stream.readUnsignedShort()
            anInt6362 = stream.readUnsignedShort()
            anInt6366 = stream.readUnsignedShort()
            anInt6359 = stream.readUnsignedShort()

            val i_2_: Int = stream.readUnsignedByte()
            if (i_2_ > 0) {
                for (i_3_ in 0 until i_2_) {
                    var i_4_: Int = stream.readUnsignedShort()
                    while ((i_4_-- xor -0x1) < -1) {
                        val i_5_: Int = stream.readInt()
                        val i_6_: Int = stream.readInt()
                    }
                }
            }
            stream.offset = 0
            val aString6352: String = stream.readString()
            anIntArray6356 = IntArray(size)
            anIntArray6369 = IntArray(size)
            interfaceText = arrayOfNulls(size)
            var i_7_ = 0
            while (stream.offset xor -0x1 > (i_0_ xor -0x1)) { // ClientScripts
                val i_8_: Int = stream.readUnsignedShort()
                if (i_8_ != 3) {
                    if (i_8_ < 100 && i_8_ != 21 && i_8_ != 38 && i_8_ != 39) {
                        anIntArray6356!![i_7_] = stream.readInt()
                        scripts.add(anIntArray6356!![i_7_])
                    } else {
                        anIntArray6356!![i_7_] = stream.readUnsignedByte()
                        scripts.add(anIntArray6356!![i_7_].toByte())
                    }
                } else {
                    interfaceText!![i_7_] = stream.readString().intern()
                    scripts.add(interfaceText!![i_7_])
                }
                anIntArray6369!![i_7_++] = i_8_
            }
            var dump = "Script ID $id: "
            for (j in scripts.indices) {
                dump += scripts[j].toString() + " "
            }
            dump += "\n"
            writeData(dump)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
    }

    companion object {
        var anIntArrayArrayArray6200: Array<Array<IntArray>?> = arrayOfNulls(2)

        fun writeData(text: String) {
            var bw: BufferedWriter? = null
            try {
                val file = File("client_scripts.txt")
                if (!file.exists()) file.createNewFile()
                bw = BufferedWriter(FileWriter(file, true))
                bw.write(text)
                bw.newLine()
                bw.flush()
                bw.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
