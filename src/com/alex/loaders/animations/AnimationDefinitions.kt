package com.alex.loaders.animations

import com.alex.io.InputStream
import com.alex.store.Store
import java.io.IOException
import java.io.PrintStream
import java.util.concurrent.ConcurrentHashMap

/**
 * The type Animation definitions.
 */
class AnimationDefinitions {
    /**
     * The Loop cycles.
     */
    var loopCycles: Int = 99

    /**
     * The An int 2137.
     */
    var anInt2137: Int = 0

    /**
     * The An int 2140.
     */
    var anInt2140: Int = -1

    /**
     * The A boolean 2141.
     */
    var aBoolean2141: Boolean = false

    /**
     * The Priority.
     */
    var priority: Int = 5

    /**
     * The Left hand equip.
     */
    var leftHandEquip: Int = -1

    /**
     * The Right hand equip.
     */
    var rightHandEquip: Int = -1

    /**
     * The An int 2145.
     */
    var anInt2145: Int = 0

    /**
     * The Handled sounds.
     */
    lateinit var handledSounds: Array<IntArray?>

    /**
     * The A boolean array 2149.
     */
    var aBooleanArray2149: BooleanArray? = null

    /**
     * The An int array 2151.
     */
    lateinit var anIntArray2151: IntArray

    /**
     * The A boolean 2152.
     */
    var aBoolean2152: Boolean = false

    /**
     * The An int 2155.
     */
    var anInt2155: Int = 2

    /**
     * The A boolean 2158.
     */
    var aBoolean2158: Boolean = false

    /**
     * The A boolean 2159.
     */
    var aBoolean2159: Boolean = false

    /**
     * The An int 2162.
     */
    var anInt2162: Int = -1

    /**
     * The Loop delay.
     */
    var loopDelay: Int = -1

    /**
     * The Sound min delay.
     */
    var soundMinDelay: IntArray? = null

    /**
     * The Sound max delay.
     */
    var soundMaxDelay: IntArray? = null

    /**
     * The An int array 1362.
     */
    var anIntArray1362: IntArray? = null

    /**
     * The Effect 2 sound.
     */
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
        /**
         * Gets emote time.
         *
         * @return the emote time
         */
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
        /**
         * Gets emote game tickets.
         *
         * @return the emote game tickets
         */
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
                anIntArray2151[i_21_] = stream.readUnsignedShort()
                ++i_21_
            }

            i_21_ = 0
            while (index > i_21_) {
                anIntArray2151[i_21_] += stream.readUnsignedShort() shl 16
                ++i_21_
            }
        } else if (opcode == 13) {
            index = stream.readUnsignedShort()
            this.handledSounds = arrayOfNulls(index)

            i_21_ = 0
            while (i_21_ < index) {
                val i_22_ = stream.readUnsignedByte()
                if (i_22_.inv() < -1) {
                    handledSounds[i_21_] = IntArray(i_22_)
                    handledSounds[i_21_]!![0] = stream.read24BitInt()

                    var i_23_ = 1
                    while (i_22_.inv() < i_23_.inv()) {
                        handledSounds[i_21_]!![i_23_] = stream.readUnsignedShort()
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
                this.anIntArray1362 = IntArray(handledSounds.size)

                index = 0
                while (index < handledSounds.size) {
                    anIntArray1362!![index] = 255
                    ++index
                }
            }

            anIntArray1362!![stream.readUnsignedByte()] = stream.readUnsignedByte()
        } else if (opcode == 20) {
            if (this.soundMaxDelay == null || this.soundMinDelay == null) {
                this.soundMaxDelay = IntArray(handledSounds.size)
                this.soundMinDelay = IntArray(handledSounds.size)

                index = 0
                while (index < handledSounds.size) {
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

    /**
     * Method 2394.
     */
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
        private val animDefs: MutableMap<Int, AnimationDefinitions?> = mutableMapOf()

        /**
         * The constant cache.
         */
        var cache: Store? = null

        /**
         * The constant id.
         */
        var id: Int = 0

        /**
         * The Frames.
         */
        lateinit var frames: IntArray

        /**
         * The Delays.
         */
        lateinit var delays: IntArray

        /**
         * The entry point of application.
         *
         * @param args the input arguments
         * @throws IOException the io exception
         */
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
                                while (k < getAnimationDefinitions(i)!!.anIntArray2151.size) {
                                    println("anIntArray2151[$k] = " + getAnimationDefinitions(i)!!.anIntArray2151[k])
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

        /**
         * Gets animation definitions.
         *
         * @param emoteId the emote id
         * @return the animation definitions
         */
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
    }
}