package com.alex.defs.npcs

import com.alex.filestore.Store
import com.alex.io.InputStream
import com.alex.io.OutputStream
import com.alex.util.Utils.getConfigArchive
import com.alex.util.Utils.getConfigFile

class NPCDefinition(var id: Int) : Cloneable {
    var unknownInt13: Int = 0
    var unknownInt6: Int = 0
    var unknownInt15: Int = 0
    @JvmField
    var respawnDirection: Byte = 7
    @JvmField
    var size: Int = 1
    var unknownArray3: Array<IntArray>? = null
    var unknownBoolean2: Boolean = false
    var unknownInt9: Int = 0
    var unknownInt4: Int = 0
    var unknownArray2: IntArray? = null
    var unknownInt7: Int = 0
    @JvmField
    var renderEmote: Int
    var unknownBoolean5: Boolean = false
    var unknownInt20: Int = 0
    var unknownByte1: Byte = 0
    var unknownBoolean3: Boolean = false
    var unknownInt3: Int = 0
    var unknownByte2: Byte = 0
    var unknownBoolean6: Boolean = false
    var unknownBoolean4: Boolean = false
    @JvmField
    var originalModelColors: IntArray? = null
    @JvmField
    var combatLevel: Int
    var unknownArray1: ByteArray? = null
    var unknownShort1: Short = 0
    var unknownBoolean1: Boolean = false
    @JvmField
    var npcHeight: Int = 128
    @JvmField
    var name: String = "null"
    var modifiedTextureColors: IntArray? = null
    @JvmField
    var walkMask: Byte = 0
    @JvmField
    var modelIds: IntArray?
    var unknownInt1: Int = 0
    var unknownInt21: Int = 0
    var unknownInt11: Int = 0
    var unknownInt17: Int = 0
    var unknownInt14: Int = 0
    var unknownInt12: Int = 0
    var unknownInt8: Int = 0
    @JvmField
    var headIcon: Int
    var unknownInt19: Int = 0
    var originalTextureColors: IntArray? = null
    var anIntArrayArray882: Array<IntArray>? = null
    var unknownInt10: Int = 0
    var unknownArray4: IntArray? = null
    var unknownInt5: Int = 0
    var unknownInt16: Int = 0
    @JvmField
    var invisibleOnMap: Boolean = true
    var npcChatHeads: IntArray? = null
    var unknownShort2: Short = 0
    @JvmField
    var options: Array<String?>
    var modifiedModelColors: IntArray?? = null
    var unknownInt2: Int = 0
    var npcId: Int = 0
    var unknownInt18: Int = 0
    var unknownBoolean7: Boolean = false
    var unknownArray5: IntArray? = null
    var params: MutableMap<Int, Any>? = null
    var anInt833: Int
    var anInt836: Int
    var anInt837: Int
    var anIntArrayArray840: Array<IntArray?>? = null
    var aBoolean841: Boolean = true
    var anInt842: Int
    var bConfig: Int
    var transformTo: IntArray? = null
    var anInt846: Int
    var aBoolean849: Boolean = false
    var anInt850: Int = 255
    var aByte851: Byte
    var aBoolean852: Boolean = true
    var anInt853: Int = 32
    var aByte854: Byte
    var aBoolean856: Boolean = false
    var aBoolean857: Boolean = true
    var recolorToFind: ShortArray? = null
    var aByteArray861: ByteArray? = null
    var aShort862: Short = 0
    var renderPriority: Boolean = false
    var retextureToReplace: ShortArray? = null
    var ambient: Int = 0
    var anInt870: Int
    var anInt871: Int
    var anInt872: Int
    var anInt874: Int
    var anInt875: Int = 0
    var anInt876: Int
    var anInt879: Int
    var retextureToFind: ShortArray? = null
    var anInt884: Int
    var anIntArray885: IntArray? = null
    var config: Int
    var anInt889: Int
    var anIntArray892: IntArray? = null
    var aShort894: Short = 0
    var recolorToReplace: ShortArray? = null
    var contrast: Int = 0
    @JvmField
    var npcWidth: Int = 128
    var anInt901: Int
    var aBoolean3190: Boolean = false
    private val aByteArray1293: ByteArray? = null
    private val aByteArray12930: ByteArray? = null
    private val anIntArray2930: IntArray? = null

    init {
        this.anInt842 = -1
        this.bConfig = -1
        this.anInt837 = -1
        this.anInt846 = -1
        this.combatLevel = -1
        this.anInt836 = -1
        this.anInt871 = -1
        this.anInt876 = -1
        this.aByte851 = -96
        this.anInt872 = -1
        this.renderEmote = -1
        this.anInt870 = -1
        this.anInt874 = -1
        this.anInt833 = -1
        this.headIcon = -1
        this.config = -1
        this.aByte854 = -16
        this.anInt889 = -1
        this.anInt884 = -1
        this.anInt879 = -1
        this.options = arrayOfNulls(5)
        this.anInt901 = -1
        this.modelIds = intArrayOf(0)
    }

    fun decode(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) break
            if (opcode != 1) {
                if (opcode == 2) name = stream.readString()
                else if ((opcode xor -0x1) != -13) {
                    if (opcode >= 30 && (opcode xor -0x1) > -36) {
                        options[opcode - 30] = stream.readString()
                        if (options[-30 + opcode].equals("Hidden", ignoreCase = true)) options[-30 + opcode] = null
                    } else if ((opcode xor -0x1) != -41) {
                        if (opcode == 41) {
                            val i = stream.readUnsignedByte()
                            retextureToFind = ShortArray(i)
                            retextureToReplace = ShortArray(i)
                            var i_54_ = 0
                            while ((i_54_ xor -0x1) > (i xor -0x1)) {
                                retextureToFind!![i_54_] = stream.readUnsignedShort().toShort()
                                retextureToReplace!![i_54_] = stream.readUnsignedShort().toShort()
                                i_54_++
                            }
                        } else if (opcode == 44) {
                            val i_24_ = stream.readUnsignedShort().toShort().toInt()
                            var i_25_ = 0
                            var i_26_ = i_24_
                            while (i_26_ > 0) {
                                i_25_++
                                i_26_ = i_26_ shr 1
                            }
                            // aByteArray12930 = new byte[i_25_];
                            var i_27_: Byte = 0
                            for (i_28_ in 0 until i_25_) {
                                if ((i_24_ and (1 shl i_28_)) > 0) {
                                    // aByteArray12930[i_28_] = i_27_;
                                    i_27_++
                                }
                                // aByteArray12930[i_28_] = (byte) -1;
                            }
                        } else if (45 == opcode) {
                            val i_29_ = stream.readUnsignedShort().toShort().toInt()
                            var i_30_ = 0
                            var i_31_ = i_29_
                            while (i_31_ > 0) {
                                i_30_++
                                i_31_ = i_31_ shr 1
                            }
                            // aByteArray1293 = new byte[i_30_];
                            var i_32_: Byte = 0
                            for (i_33_ in 0 until i_30_) {
                                if ((i_29_ and (1 shl i_33_)) > 0) {
                                    // aByteArray1293[i_33_] = i_32_;
                                    i_32_++
                                }
                                // aByteArray1293[i_33_] = (byte) -1;
                            }
                        } else if ((opcode xor -0x1) == -43) {
                            val i = stream.readUnsignedByte()
                            aByteArray861 = ByteArray(i)
                            var i_55_ = 0
                            while (i > i_55_) {
                                aByteArray861!![i_55_] = stream.readByte().toByte()
                                i_55_++
                            }
                        } else if ((opcode xor -0x1) != -61) {
                            if (opcode == 93) invisibleOnMap = false
                            else if ((opcode xor -0x1) == -96) combatLevel = stream.readUnsignedShort()
                            else if (opcode != 97) {
                                if ((opcode xor -0x1) == -99) npcWidth = stream.readUnsignedShort()
                                else if ((opcode xor -0x1) == -100) renderPriority = true
                                else if (opcode == 100) ambient = stream.readByte()
                                else if ((opcode xor -0x1) == -102) contrast = stream.readByte() * 5
                                else if ((opcode xor -0x1) == -103) headIcon = stream.readUnsignedShort()
                                else if (opcode != 103) {
                                    if (opcode == 106 || opcode == 118) {
                                        bConfig = stream.readUnsignedShort()
                                        if (bConfig == 65535) bConfig = -1
                                        config = stream.readUnsignedShort()
                                        if (config == 65535) config = -1
                                        var i = -1
                                        if ((opcode xor -0x1) == -119) {
                                            i = stream.readUnsignedShort()
                                            if ((i xor -0x1) == -65536) i = -1
                                        }
                                        val i_56_ = stream.readUnsignedByte()
                                        transformTo = IntArray(2 + i_56_)
                                        var i_57_ = 0
                                        while (i_56_ >= i_57_) {
                                            transformTo!![i_57_] = stream.readUnsignedShort()
                                            if (transformTo!![i_57_] == 65535) transformTo!![i_57_] = -1
                                            i_57_++
                                        }
                                        transformTo!![i_56_ - -1] = i
                                    } else if ((opcode xor -0x1) != -108) {
                                        if ((opcode xor -0x1) == -110) aBoolean852 = false
                                        else if ((opcode xor -0x1) != -112) {
                                            if (opcode != 113) {
                                                if (opcode == 114) {
                                                    aByte851 = (stream.readByte()).toByte()
                                                    aByte854 = (stream.readByte()).toByte()
                                                } else if (opcode == 115) {
                                                    stream.readUnsignedByte()
                                                    stream.readUnsignedByte()
                                                } else if ((opcode xor -0x1) != -120) {
                                                    if (opcode != 121) {
                                                        if ((opcode xor -0x1) != -123) {
                                                            if (opcode == 123) anInt846 = (stream.readUnsignedShort())
                                                            else if (opcode != 125) {
                                                                if (opcode == 127) renderEmote =
                                                                    (stream.readUnsignedShort())
                                                                else if ((opcode xor -0x1) == -129) stream.readUnsignedByte()
                                                                else if (opcode != 134) {
                                                                    if (opcode == 135) {
                                                                        anInt833 = stream.readUnsignedByte()
                                                                        anInt874 = stream.readUnsignedShort()
                                                                    } else if (opcode != 136) {
                                                                        if (opcode != 137) {
                                                                            if (opcode != 138) {
                                                                                if ((opcode xor -0x1) != -140) {
                                                                                    if (opcode == 140) anInt850 = stream
                                                                                        .readUnsignedByte()
                                                                                    else if (opcode == 141) aBoolean849 =
                                                                                        true
                                                                                    else if ((opcode
                                                                                                xor -0x1) != -143
                                                                                    ) {
                                                                                        if (opcode == 143) aBoolean856 =
                                                                                            true
                                                                                        else if ((opcode
                                                                                                    xor -0x1) <= -151
                                                                                            && opcode < 155
                                                                                        ) {
                                                                                            options[opcode - 150] =
                                                                                                stream
                                                                                                    .readString()
                                                                                            if (options[opcode - 150]
                                                                                                    .equals(
                                                                                                        "Hidden",
                                                                                                        ignoreCase = true
                                                                                                    )
                                                                                            ) options[opcode
                                                                                                    + -150] = null
                                                                                        } else if ((opcode
                                                                                                    xor -0x1) == -161
                                                                                        ) {
                                                                                            val i = stream
                                                                                                .readUnsignedByte()
                                                                                            anIntArray885 = IntArray(i)
                                                                                            var i_58_ = 0
                                                                                            while (i > i_58_) {
                                                                                                anIntArray885!![i_58_] =
                                                                                                    stream
                                                                                                        .readUnsignedShort()
                                                                                                i_58_++
                                                                                            }

                                                                                            // all
                                                                                            // added
                                                                                            // after
                                                                                            // here
                                                                                        } else if (opcode == 155) {
                                                                                            val aByte821 = stream
                                                                                                .readByte()
                                                                                            val aByte824 = stream
                                                                                                .readByte()
                                                                                            val aByte843 = stream
                                                                                                .readByte()
                                                                                            val aByte855 = stream
                                                                                                .readByte()
                                                                                        } else if (opcode == 158) {
                                                                                            val aByte833 = 1.toByte()
                                                                                        } else if (opcode == 159) {
                                                                                            val aByte833 = 0.toByte()
                                                                                        } else if (opcode == 162) { // added
                                                                                            // opcode
                                                                                            // aBoolean3190
                                                                                            // =
                                                                                            // true;
                                                                                        } else if (opcode == 163) { // added
                                                                                            // opcode
                                                                                            val anInt864 = stream
                                                                                                .readUnsignedByte()
                                                                                        } else if (opcode == 164) {
                                                                                            val anInt848 = stream
                                                                                                .readUnsignedShort()
                                                                                            val anInt837 = stream
                                                                                                .readUnsignedShort()
                                                                                        } else if (opcode == 165) {
                                                                                            val anInt847 = stream
                                                                                                .readUnsignedByte()
                                                                                        } else if (opcode == 168) {
                                                                                            val anInt828 = stream
                                                                                                .readUnsignedByte()
                                                                                        } else if (opcode >= 170
                                                                                            && opcode < 176
                                                                                        ) {
                                                                                            // if
                                                                                            // (null
                                                                                            // ==
                                                                                            // anIntArray2930)
                                                                                            // {
                                                                                            // anIntArray2930
                                                                                            // =
                                                                                            // new
                                                                                            // int[6];
                                                                                            // Arrays.fill(anIntArray2930,
                                                                                            // -1);
                                                                                            // }
                                                                                            var i_44_ = stream
                                                                                                .readUnsignedShort()
                                                                                                .toShort().toInt()
                                                                                            if (i_44_ == 65535) i_44_ =
                                                                                                -1
                                                                                            // anIntArray2930[opcode
                                                                                            // -
                                                                                            // 170]
                                                                                            // =
                                                                                            // i_44_;
                                                                                        } else if (opcode == 249) {
                                                                                            val i = stream
                                                                                                .readUnsignedByte()
                                                                                            if (params == null) {
                                                                                                params = HashMap(
                                                                                                    i
                                                                                                )
                                                                                            }
                                                                                            var i_60_ = 0
                                                                                            while (i > i_60_) {
                                                                                                val stringInstance =
                                                                                                    stream
                                                                                                        .readUnsignedByte() == 1
                                                                                                val key = stream
                                                                                                    .readMedium()
                                                                                                val value: Any =
                                                                                                    if (stringInstance) stream
                                                                                                        .readString()
                                                                                                    else stream
                                                                                                        .readInt()
                                                                                                params!![key] = value
                                                                                                i_60_++
                                                                                            }
                                                                                        }
                                                                                    } else anInt870 = stream
                                                                                        .readUnsignedShort()
                                                                                } else anInt879 = stream.readSmartInt()
                                                                            } else anInt901 = stream.readSmartInt()
                                                                        } else anInt872 = stream.readUnsignedShort()
                                                                    } else {
                                                                        anInt837 = stream.readUnsignedByte()
                                                                        anInt889 = stream.readUnsignedShort()
                                                                    }
                                                                } else {
                                                                    anInt876 = (stream.readUnsignedShort())
                                                                    if (anInt876 == 65535) anInt876 = -1
                                                                    anInt842 = (stream.readUnsignedShort())
                                                                    if (anInt842 == 65535) anInt842 = -1
                                                                    anInt884 = (stream.readUnsignedShort())
                                                                    if ((anInt884 xor -0x1) == -65536) anInt884 = -1
                                                                    anInt871 = (stream.readUnsignedShort())
                                                                    if ((anInt871 xor -0x1) == -65536) anInt871 = -1
                                                                    anInt875 = (stream.readUnsignedByte())
                                                                }
                                                            } else respawnDirection = (stream.readByte()).toByte()
                                                        } else anInt836 = stream.readSmartInt()
                                                    } else {
                                                        anIntArrayArray840 = (arrayOfNulls(modelIds!!.size))
                                                        val i = (stream.readUnsignedByte())
                                                        var i_62_ = 0
                                                        while (((i_62_ xor -0x1) > (i
                                                                    xor -0x1))
                                                        ) {
                                                            val i_63_ = (stream.readUnsignedByte())
                                                            val `is` =
                                                                ((IntArray(3)).also { anIntArrayArray840!![i_63_] = it })
                                                            `is`[0] = (stream.readByte())
                                                            `is`[1] = (stream.readByte())
                                                            `is`[2] = (stream.readByte())
                                                            i_62_++
                                                        }
                                                    }
                                                } else walkMask = (stream.readByte()).toByte()
                                            } else {
                                                aShort862 = (stream.readUnsignedShort()).toShort()
                                                aShort894 = (stream.readUnsignedShort()).toShort()
                                            }
                                        } else aBoolean857 = false
                                    } else aBoolean841 = false
                                } else anInt853 = stream.readUnsignedShort()
                            } else npcHeight = stream.readUnsignedShort()
                        } else {
                            val i = stream.readUnsignedByte()
                            npcChatHeads = IntArray(i)
                            var i_64_ = 0
                            while ((i_64_ xor -0x1) > (i xor -0x1)) {
                                npcChatHeads!![i_64_] = stream.readSmartInt()
                                i_64_++
                            }
                        }
                    } else {
                        val i = stream.readUnsignedByte()
                        recolorToFind = ShortArray(i)
                        recolorToReplace = ShortArray(i)
                        var i_65_ = 0
                        while ((i xor -0x1) < (i_65_ xor -0x1)) {
                            recolorToReplace!![i_65_] = stream.readUnsignedShort().toShort()
                            recolorToFind!![i_65_] = stream.readUnsignedShort().toShort()
                            i_65_++
                        }
                    }
                } else size = stream.readUnsignedByte()
            } else {
                val i = stream.readUnsignedByte()
                modelIds = IntArray(i)
                for (i_66_ in 0 until i) {
                    modelIds!![i_66_] = stream.readSmartInt()
                    if ((modelIds!![i_66_] xor -0x1) == -65536) modelIds!![i_66_] = -1
                }
            }
        }
    }

    fun encode(): ByteArray {
        val stream = OutputStream()

        var data: Int
        if (this.modelIds != null) {
            stream.writeByte(1)
            stream.writeByte(modelIds!!.size)
            data = 0
            while (data < modelIds!!.size) {
                stream.writeSmartInt(modelIds!![data])
                ++data
            }
        }

        if (this.name != "null") {
            stream.writeByte(2)
            stream.writeString(this.name)
        }

        if (this.size != 0) {
            stream.writeByte(12)
            stream.writeByte(this.size)
        }

        data = 0
        while (data < options.size) {
            if (options[data] != null && options[data] !== "Hidden") {
                stream.writeByte(30 + data)
                stream.writeString(options[data])
            }
            ++data
        }

        if (this.originalModelColors != null && this.modifiedModelColors != null) {
            stream.writeByte(40)
            stream.writeByte(originalModelColors!!.size)
            data = 0
            while (data < originalModelColors!!.size) {
                stream.writeShort(originalModelColors!![data])
                stream.writeShort(modifiedModelColors!![data])
                ++data
            }
        }

        if (this.originalTextureColors != null && this.modifiedTextureColors != null) {
            stream.writeByte(41)
            stream.writeByte(originalTextureColors!!.size)
            data = 0
            while (data < originalTextureColors!!.size) {
                stream.writeShort(originalTextureColors!![data])
                stream.writeShort(modifiedTextureColors!![data])
                ++data
            }
        }

        if (this.unknownArray1 != null) {
            stream.writeByte(42)
            stream.writeByte(unknownArray1!!.size)
            data = 0
            while (data < unknownArray1!!.size) {
                stream.writeByte(unknownArray1!![data].toInt())
                ++data
            }
        }

        if (this.npcChatHeads != null) {
            stream.writeByte(60)
            stream.writeByte(npcChatHeads!!.size)
            data = 0
            while (data < npcChatHeads!!.size) {
                stream.writeSmartInt(npcChatHeads!![data])
                ++data
            }
        }

        if (this.invisibleOnMap) {
            stream.writeByte(93)
        }

        if (this.combatLevel != -1) {
            stream.writeByte(95)
            stream.writeShort(this.combatLevel)
        }

        if (this.npcHeight != 0) {
            stream.writeByte(97)
            stream.writeShort(this.npcHeight)
        }

        if (this.npcWidth != 0) {
            stream.writeByte(98)
            stream.writeShort(this.npcWidth)
        }

        if (this.unknownBoolean1) {
            stream.writeByte(99)
        }

        if (this.unknownInt1 != 0) {
            stream.writeByte(100)
            stream.writeByte(this.unknownInt1)
        }

        if (this.unknownInt2 != 0) {
            stream.writeByte(101)
            stream.writeByte(this.unknownInt2 / 5)
        }

        if (this.headIcon != -1) {
            stream.writeByte(102)
            stream.writeShort(this.headIcon)
        }

        if (walkMask.toInt() != -1) {
            stream.writeByte(119)
            stream.writeByte(walkMask.toInt())
        }

        if (respawnDirection.toInt() != 7) {
            stream.writeByte(125)
            stream.writeByte(respawnDirection.toInt())
        }

        if (this.renderEmote != -1) {
            stream.writeByte(127)
            stream.writeShort(this.renderEmote)
        }

        if (this.params != null) {
            stream.writeByte(249)
            stream.writeByte(params!!.size)
            for (key in params!!.keys) {
                val value = params!![key]
                stream.writeByte(if (value is String) 1 else 0)
                stream.writeMedium(key)
                if (value is String) {
                    stream.writeString(value)
                } else {
                    stream.writeInt((value as Int?)!!)
                }
            }
        }

        stream.writeByte(0)
        val var61 = ByteArray(stream.getOffset())
        stream.setOffset(0)
        stream.getBytes(var61, 0, var61.size)
        return var61
    }

    fun write(cache: Store): Boolean {
        return cache.indexes[18].putFile(getConfigArchive(id, 7), getConfigFile(id, 7), encode())
    }

    override fun toString(): String {
        return id.toString() + " - " + this.name
    }

    public override fun clone(): Cloneable {
        return this
    }
}
