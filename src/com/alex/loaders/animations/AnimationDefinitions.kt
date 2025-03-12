package com.alex.loaders.animations
import com.alex.io.InputStream
import com.alex.io.OutputStream
import com.alex.store.Store
import java.io.IOException
import java.io.PrintStream
import java.util.concurrent.ConcurrentHashMap
class AnimationDefinitions : Cloneable {
    @JvmField val animDefinitions: ConcurrentHashMap<Int, AnimationDefinitions> = ConcurrentHashMap()
    var loopCycles: Int = 99
    var anInt2137: Int = 0
    var anInt2140: Int = -1
    var aBoolean2141: Boolean = false
    var priority: Int = 5
    var leftHandEquip: Int = -1
    var rightHandEquip: Int = -1
    var anInt2145: Int = 0
    var handledSounds: Array<IntArray?>? = null
    var aBooleanArray2149: BooleanArray? = null
    var anIntArray2151: IntArray? = null
    var aBoolean2152: Boolean = false
    var anInt2155: Int = 2
    var aBoolean2158: Boolean = false
    var aBoolean2159: Boolean = false
    var anInt2162: Int = -1
    var loopDelay: Int = -1
    var soundMinDelay: IntArray? = null
    var soundMaxDelay: IntArray? = null
    var anIntArray1362: IntArray? = null
    var effect2Sound: Boolean = false
    private fun readValueLoop(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) {
                return
            }
            this.readValues(stream, opcode)
        }
    }
    val emoteTime: Int
        get() {
            if (delays == null) {
                return 0
            } else {
                var ms = 0
                val `arr$` = delays
                val `len$` = `arr$`.size
                for (`i$` in 0 until `len$`) {
                    val i = `arr$`[`i$`]
                    ms += i
                }
                return ms * 30
            }
        }
    val emoteGameTickets: Int
        get() = this.emoteTime / 1000
    private fun readValues(stream: InputStream, opcode: Int) {
        var index: Int
        var i_21_: Int
        if (opcode == 1) {
            index = stream.readUnsignedShort()
            delays = IntArray(index)
            i_21_ = 0
            while (index.inv() < i_21_.inv()) {
                delays[i_21_] = stream.readUnsignedShort()
                ++i_21_
            }
            frames = IntArray(index)
            i_21_ = 0
            while (i_21_.inv() > index.inv()) {
                frames[i_21_] = stream.readUnsignedShort()
                ++i_21_
            }
            i_21_ = 0
            while (i_21_ < index) {
                frames[i_21_] += stream.readUnsignedShort() shl 16
                ++i_21_
            }
        } else if (opcode == 2) {
            this.loopDelay = stream.readUnsignedShort()
        } else if (opcode == 3) {
            this.aBooleanArray2149 = BooleanArray(256)
            index = stream.readUnsignedByte()
            i_21_ = 0
            while (i_21_ < index) {
                aBooleanArray2149!![stream.readUnsignedByte()] = true
                ++i_21_
            }
        } else if (opcode == 4) {
            this.aBoolean2152 = true
        } else if (opcode == 5) {
            this.priority = stream.readUnsignedByte()
        } else if (opcode == 6) {
            this.rightHandEquip = stream.readUnsignedShort()
        } else if (opcode == 7) {
            this.leftHandEquip = stream.readUnsignedShort()
        } else if (opcode == 8) {
            this.loopCycles = stream.readUnsignedByte()
        } else if (opcode == 9) {
            this.anInt2140 = stream.readUnsignedByte()
        } else if (opcode == 10) {
            this.anInt2162 = stream.readUnsignedByte()
        } else if (opcode == 11) {
            this.anInt2155 = stream.readUnsignedByte()
        } else if (opcode == 12) {
            index = stream.readUnsignedByte()
            this.anIntArray2151 = IntArray(index)
            i_21_ = 0
            while (i_21_.inv() > index.inv()) {
                anIntArray2151!![i_21_] = stream.readUnsignedShort()
                ++i_21_
            }
            i_21_ = 0
            while (index > i_21_) {
                anIntArray2151!![i_21_] += stream.readUnsignedShort() shl 16
                ++i_21_
            }
        } else if (opcode == 13) {
            index = stream.readUnsignedShort()
            this.handledSounds = arrayOfNulls(index)
            i_21_ = 0
            while (i_21_ < index) {
                val i_22_ = stream.readUnsignedByte()
                if (i_22_.inv() < -1) {
                    handledSounds!![i_21_] = IntArray(i_22_)
                    handledSounds!![i_21_]!![0] = stream.read24BitInt()
                    var i_23_ = 1
                    while (i_22_.inv() < i_23_.inv()) {
                        handledSounds!![i_21_]!![i_23_] = stream.readUnsignedShort()
                        ++i_23_
                    }
                }
                ++i_21_
            }
        } else if (opcode == 14) {
            this.aBoolean2141 = true
        } else if (opcode == 15) {
            this.aBoolean2159 = true
        } else if (opcode == 16) {
            this.aBoolean2158 = true
        } else if (opcode == 17) {
            this.anInt2145 = stream.readUnsignedByte()
        } else if (opcode == 18) {
            this.effect2Sound = true
        } else if (opcode == 19) {
            if (this.anIntArray1362 == null) {
                this.anIntArray1362 = IntArray(handledSounds!!.size)
                index = 0
                while (index < handledSounds!!.size) {
                    anIntArray1362!![index] = 255
                    ++index
                }
            }
            anIntArray1362!![stream.readUnsignedByte()] = stream.readUnsignedByte()
        } else if (opcode == 20) {
            if (this.soundMaxDelay == null || this.soundMinDelay == null) {
                this.soundMaxDelay = IntArray(handledSounds!!.size)
                this.soundMinDelay = IntArray(handledSounds!!.size)
                index = 0
                while (index < handledSounds!!.size) {
                    soundMaxDelay!![index] = 256
                    soundMinDelay!![index] = 256
                    ++index
                }
            }
            index = stream.readUnsignedByte()
            soundMaxDelay!![index] = stream.readUnsignedShort()
            soundMinDelay!![index] = stream.readUnsignedShort()
        }
    }
    fun method2394() {
        if (this.anInt2140 == -1) {
            if (this.aBooleanArray2149 == null) {
                this.anInt2140 = 0
            } else {
                this.anInt2140 = 2
            }
        }
        if (this.anInt2162 == -1) {
            if (this.aBooleanArray2149 == null) {
                this.anInt2162 = 0
            } else {
                this.anInt2162 = 2
            }
        }
    }
    companion object {
        private val animDefs: HashMap<Int, AnimationDefinitions?> = HashMap()
        var cache: Store? = null
        var id: Int = 0
        lateinit var frames: IntArray
        lateinit var delays: IntArray
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            cache = Store("cache/")
            label55@ for (i in 0..0) {
                println("Emote ID: $i")
                var k = 0
                while (true) {
                    getAnimationDefinitions(i)
                    var var10000: PrintStream
                    var var10001: StringBuilder
                    if (k >= delays.size) {
                        k = 0
                        while (true) {
                            getAnimationDefinitions(i)
                            if (k >= frames.size) {
                                println("loopDelay = " + getAnimationDefinitions(i)!!.loopDelay)
                                println("leftHandEquip = " + getAnimationDefinitions(i)!!.leftHandEquip)
                                println("priority = " + getAnimationDefinitions(i)!!.priority)
                                println("rightHandEquip = " + getAnimationDefinitions(i)!!.rightHandEquip)
                                println("loopCycles = " + getAnimationDefinitions(i)!!.loopCycles)
                                println("anInt2140 = " + getAnimationDefinitions(i)!!.anInt2140)
                                println("anInt2162 = " + getAnimationDefinitions(i)!!.anInt2162)
                                println("anInt2155 = " + getAnimationDefinitions(i)!!.anInt2155)
                                println("anInt2145 = " + getAnimationDefinitions(i)!!.anInt2145)
                                k = 0
                                while (k < getAnimationDefinitions(i)!!.anIntArray2151!!.size) {
                                    println("anIntArray2151[$k] = " + getAnimationDefinitions(i)!!.anIntArray2151!![k])
                                    ++k
                                }
                                k = 0
                                while (k < getAnimationDefinitions(i)!!.aBooleanArray2149!!.size) {
                                    println("aBooleanArray2149[$k] = " + getAnimationDefinitions(i)!!.aBooleanArray2149!![k])
                                    ++k
                                }
                                println("aBoolean2152 = " + getAnimationDefinitions(i)!!.aBoolean2152)
                                k = 0
                                while (k < getAnimationDefinitions(i)!!.anIntArray1362!!.size) {
                                    println("anIntArray1362[$k] = " + getAnimationDefinitions(i)!!.anIntArray1362!![k])
                                    ++k
                                }
                                continue@label55
                            }
                            var10000 = System.out
                            var10001 = (StringBuilder()).append("frames[").append(k).append("] = ")
                            getAnimationDefinitions(i)
                            var10000.println(var10001.append(frames[k]))
                            ++k
                        }
                    }
                    var10000 = System.out
                    var10001 = (StringBuilder()).append("delays[").append(k).append("] = ")
                    getAnimationDefinitions(i)
                    var10000.println(var10001.append(delays[k]))
                    ++k
                }
            }
        }
        fun getAnimationDefinitions(emoteId: Int): AnimationDefinitions? {
            return try {
                animDefs[emoteId]?.let {
                    return it
                }
                val data = cache?.indexes?.get(20)?.getFile(emoteId ushr 7, emoteId and 127)
                val newAnimationDef = AnimationDefinitions()
                data?.let {
                    newAnimationDef.readValueLoop(InputStream(it))
                }
                newAnimationDef.method2394()
                animDefs[emoteId] = newAnimationDef
                id = emoteId
                return newAnimationDef
            } catch (e: Exception) {
                return null
            }
        }

        @JvmStatic
        fun getAnimationDefinitionsSize(store: Store): Int {
            val lastArchiveId = store.indexes[20].lastArchiveId
            return lastArchiveId * 128 + store.indexes[20].getValidFilesCount(lastArchiveId)
        }

        fun setAnimationDefinitions(emoteId: Int, animationDefinitions: AnimationDefinitions?) {
            if (animationDefinitions != null) {
                animDefs[emoteId] = animationDefinitions
            } else {
                animDefs.remove(emoteId)
            }
        }
    }

    fun encode(): ByteArray {
        val stream = OutputStream()

        stream.writeByte(1)
        stream.writeShort(frames.size)
        for (delay in delays) stream.writeShort(delay)
        for (frame in frames) stream.writeShort(frame and 0xFFFF)
        for (frame in frames) stream.writeShort(frame shr 16)

        if (loopDelay != -1) {
            stream.writeByte(2)
            stream.writeShort(loopDelay)
        }

        if (priority != 5) {
            stream.writeByte(5)
            stream.writeByte(priority)
        }

        if (rightHandEquip != -1) {
            stream.writeByte(6)
            stream.writeShort(rightHandEquip)
        }

        if (leftHandEquip != -1) {
            stream.writeByte(7)
            stream.writeShort(leftHandEquip)
        }

        if (loopCycles != 99) {
            stream.writeByte(8)
            stream.writeByte(loopCycles)
        }

        if (anInt2140 != -1) {
            stream.writeByte(9)
            stream.writeByte(anInt2140)
        }

        if (anInt2162 != -1) {
            stream.writeByte(10)
            stream.writeByte(anInt2162)
        }

        if (anInt2155 != 2) {
            stream.writeByte(11)
            stream.writeByte(anInt2155)
        }

        if (anIntArray2151 != null) {
            stream.writeByte(12)
            stream.writeByte(anIntArray2151!!.size)
            for (id in anIntArray2151!!) stream.writeShort(id)
            for (id in anIntArray2151!!) stream.writeShort(id shr 16)
        }

        if (aBoolean2141) {
            stream.writeByte(14)
        }

        if (aBoolean2159) {
            stream.writeByte(15)
        }

        if (aBoolean2158) {
            stream.writeByte(16)
        }

        if (anInt2145 != 0) {
            stream.writeByte(17)
            stream.writeByte(anInt2145)
        }

        if (effect2Sound) {
            stream.writeByte(18)
        }

        stream.writeByte(0)

        val var6 = ByteArray(stream.offset)
        stream.offset = 0
        stream.getBytes(var6, 0, var6.size)
        return var6
    }
    public override fun clone(): AnimationDefinitions {
        val cloned = super.clone() as AnimationDefinitions

        cloned.loopCycles = this.loopCycles
        cloned.anInt2137 = this.anInt2137
        cloned.anInt2140 = this.anInt2140
        cloned.aBoolean2141 = this.aBoolean2141
        cloned.priority = this.priority
        cloned.leftHandEquip = this.leftHandEquip
        cloned.rightHandEquip = this.rightHandEquip
        cloned.anInt2145 = this.anInt2145
        cloned.aBoolean2152 = this.aBoolean2152
        cloned.anInt2155 = this.anInt2155
        cloned.aBoolean2158 = this.aBoolean2158
        cloned.aBoolean2159 = this.aBoolean2159
        cloned.anInt2162 = this.anInt2162
        cloned.loopDelay = this.loopDelay
        cloned.effect2Sound = this.effect2Sound

        cloned.anIntArray2151 = this.anIntArray2151!!.clone()
        cloned.anIntArray1362 = this.anIntArray1362?.clone()
        cloned.aBooleanArray2149 = this.aBooleanArray2149?.clone()
        cloned.soundMinDelay = this.soundMinDelay?.clone()
        cloned.soundMaxDelay = this.soundMaxDelay?.clone()

        cloned.handledSounds = this.handledSounds?.map { it?.clone() }?.toTypedArray()

        //cloned.animDefinitions.putAll(this.animDefinitions.mapValues { it.value.clone() })

        return cloned
    }



    override fun toString(): String {
        return id.toString() + " - " + this.animDefinitions.keys()
    }
}