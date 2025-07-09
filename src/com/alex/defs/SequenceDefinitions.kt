package com.alex.defs

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.io.OutputStream
import com.alex.util.Utils
import console.Main
import java.util.concurrent.ConcurrentHashMap

class SequenceDefinitions {
    var id: Int = 0
    var maxLoops: Int = 99
    var animationSpeed: Int = 0
    var frameIds: IntArray? = null
    var resetWhenWalk: Int = -1
    var isAnimationLocked: Boolean = false
    var forcedPriority: Int = 5
    var leftHandItem: Int = -1
    var rightHandItem: Int = -1
    var animationEffect: Int = 0
    var sounds: Array<IntArray>? = null
    var animationFlowControl: BooleanArray? = null
    var baseIds: IntArray? = null
    var isEffectApplied: Boolean = false
    var frameLengths: IntArray? = null
    var delayType: Int = 2
    var isEffectApplied2: Boolean = false
    var isDefaultPriority: Boolean = false
    var priority: Int = -1
    var frameStep: Int = -1
    var soundMinDelay: IntArray? = null
    var soundMaxDelay: IntArray? = null
    var soundVolume: IntArray? = null
    var hasSoundEffect: Boolean = false
    private var originalFrames: IntArray? = null

    companion object {
        private val sequenceDefinitionsCache = ConcurrentHashMap<Int, SequenceDefinitions>()

        fun getAnimationDefinitions(
            cache: Cache,
            emoteId: Int,
        ): SequenceDefinitions? =
            try {
                sequenceDefinitionsCache[emoteId] ?: run {
                    val data = cache.indexes[20].getFile(emoteId shr 7, emoteId and 0x7f)
                    val definition =
                        SequenceDefinitions().apply {
                            id = emoteId
                            data?.let { decode(InputStream(it)) }
                            method2394()
                        }
                    sequenceDefinitionsCache[emoteId] = definition
                    definition
                }
            } catch (e: Throwable) {
                null
            }
    }

    fun decode(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) break
            read(stream, opcode)
        }
    }

    fun decodeMinecraft(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) break
            Main.log(this.javaClass.name, "Opcode : $opcode")
            readMinecraft(stream, opcode)
        }
    }

    private fun readMinecraft(
        stream: InputStream,
        opcode: Int,
    ) {
        var count: Int
        when (opcode) {
            1 -> {
                count = stream.readUnsignedShort()
                frameLengths = IntArray(count)
                for (index in 0 until count) frameLengths!![index] = stream.readUnsignedShort()
                frameIds = IntArray(count)
                originalFrames = IntArray(count)
                for (index in 0 until count) {
                    frameIds!![index] = stream.readUnsignedShort()
                    originalFrames!![index] = frameIds!![index]
                }
                for (index in 0 until count) {
                    frameIds!![index] += stream.readUnsignedShort() shl 16
                }
            }

            2 -> frameStep = stream.readUnsignedShort()
            3 -> {
                animationFlowControl = BooleanArray(256)
                count = stream.readUnsignedByte()
                for (index in 0 until count) {
                    animationFlowControl!![stream.readUnsignedByte()] = true
                }
            }

            4 -> Unit
            5 -> forcedPriority = stream.readUnsignedByte()
            6 -> leftHandItem = -1
            7 -> rightHandItem = -1
            8 -> maxLoops = stream.readUnsignedByte()
            9 -> resetWhenWalk = stream.readUnsignedByte()
            10 -> priority = stream.readUnsignedByte()
            11 -> delayType = stream.readUnsignedByte()
            12 -> {
                count = stream.readUnsignedByte()
                baseIds = IntArray(count)
                for (index in 0 until count) baseIds!![index] = stream.readUnsignedShort()
                for (index in 0 until count) baseIds!![index] += stream.readUnsignedShort() shl 16
            }

            13 -> {
                count = stream.readUnsignedByte()
                sounds = Array(count) { IntArray(1) }
                for (sound in 0 until count) {
                    sounds!![sound][0] = stream.readMedium()
                }
            }

            else -> Main.log(this.javaClass.name, "Missing Minecraft Animation opcode: [$opcode], Animation: [$id]")
        }
    }

    fun write(cache: Cache): Boolean =
        cache.indexes[20].putFile(Utils.getConfigArchive(id, 8), Utils.getConfigFile(id, 8), encode())

    private fun encode(): ByteArray {
        val stream = OutputStream()

        var count: Int

        stream.writeByte(1)
        count = frameLengths?.size ?: 0
        stream.writeShort(count)
        frameLengths?.forEach { stream.writeShort(it) }
        frameIds?.forEach { stream.writeShort(it) }
        frameIds?.forEach { stream.writeShort(it - (it shr 16)) }

        stream.writeByte(2)
        stream.writeShort(frameStep)

        stream.writeByte(3)
        count = animationFlowControl?.count { it } ?: 0
        stream.writeByte(count)

        if (isEffectApplied) stream.writeByte(4)

        stream.writeByte(5)
        stream.writeByte(forcedPriority)

        if (rightHandItem != -1) {
            stream.writeShort(6)
            stream.writeShort(rightHandItem)
        }

        if (leftHandItem != -1) {
            stream.writeByte(7)
            stream.writeShort(leftHandItem)
        }

        stream.writeByte(8)
        stream.writeByte(maxLoops)

        if (resetWhenWalk != -1) {
            stream.writeByte(9)
            stream.writeByte(resetWhenWalk)
        }

        stream.writeByte(10)
        stream.writeByte(priority)

        stream.writeByte(11)
        stream.writeByte(delayType)

        baseIds?.let {
            stream.writeByte(12)
            count = it.size
            stream.writeByte(count)
            it.forEach { baseId -> stream.writeShort(baseId) }
            it.forEach { baseId -> stream.writeShort(baseId - (baseId shr 16)) }
        }

        sounds?.let {
            stream.writeByte(13)
            stream.writeShort(it.size)
            it.forEach { sound ->
                val soundSize = sound.size
                stream.writeByte(soundSize)
                if (soundSize > -1) {
                    stream.writeMedium(sound[0])
                    for (s in 1 until soundSize) {
                        stream.writeShort(sound[s])
                    }
                }
            }
        }

        stream.writeByte(0)
        val offsetStream = ByteArray(stream.offset)
        stream.setOffset(0)
        stream.getBytes(offsetStream, 0, offsetStream.size)

        return offsetStream
    }

    private fun read(
        stream: InputStream,
        opcode: Int,
    ) {
        var count: Int
        when (opcode) {
            1 -> {
                count = stream.readUnsignedShort()
                frameLengths = IntArray(count)
                for (index in 0 until count) frameLengths!![index] = stream.readUnsignedShort()
                frameIds = IntArray(count)
                originalFrames = IntArray(count)
                for (index in 0 until count) {
                    frameIds!![index] = stream.readUnsignedShort()
                    originalFrames!![index] = frameIds!![index]
                }
                for (index in 0 until count) {
                    frameIds!![index] += stream.readUnsignedShort() shl 16
                }
            }

            2 -> frameStep = stream.readUnsignedShort()
            3 -> {
                animationFlowControl = BooleanArray(256)
                count = stream.readUnsignedByte()
                for (index in 0 until count) {
                    animationFlowControl!![stream.readUnsignedByte()] = true
                }
            }

            4 -> isEffectApplied = true
            5 -> forcedPriority = stream.readUnsignedByte()
            6 -> rightHandItem = stream.readUnsignedShort()
            7 -> leftHandItem = stream.readUnsignedShort()
            8 -> maxLoops = stream.readUnsignedByte()
            9 -> resetWhenWalk = stream.readUnsignedByte()
            10 -> priority = stream.readUnsignedByte()
            11 -> delayType = stream.readUnsignedByte()
            12 -> {
                count = stream.readUnsignedByte()
                baseIds = IntArray(count)
                for (index in 0 until count) baseIds!![index] = stream.readUnsignedShort()
                for (index in 0 until count) baseIds!![index] += stream.readUnsignedShort() shl 16
            }

            13 -> {
                count = stream.readUnsignedShort()
                sounds = Array(count) { IntArray(1) }
                for (index in 0 until count) {
                    val soundSize = stream.readUnsignedByte()
                    if (soundSize > -1) {
                        sounds!![index] = IntArray(soundSize)
                        sounds!![index][0] = stream.readMedium()
                        for (s in 1 until soundSize) {
                            sounds!![index][s] = stream.readUnsignedShort()
                        }
                    }
                }
            }

            14 -> isAnimationLocked = true
            15 -> isDefaultPriority = true
            16 -> isEffectApplied2 = true
            17 -> animationEffect = stream.readUnsignedByte()
            18 -> hasSoundEffect = true
            19 -> {
                if (soundVolume == null) {
                    soundVolume = IntArray(sounds?.size ?: 0) { 255 }
                }
                soundVolume!![stream.readUnsignedByte()] = stream.readUnsignedByte()
            }

            20 -> {
                if (soundMaxDelay == null || soundMinDelay == null) {
                    soundMaxDelay = IntArray(sounds?.size ?: 0) { 256 }
                    soundMinDelay = IntArray(sounds?.size ?: 0) { 256 }
                }
                count = stream.readUnsignedByte()
                soundMaxDelay!![count] = stream.readUnsignedShort()
                soundMinDelay!![count] = stream.readUnsignedShort()
            }
        }
    }

    private fun method2394() {
        if (resetWhenWalk == -1) {
            resetWhenWalk = if (animationFlowControl == null) 0 else 2
        }

        if (priority == -1) {
            priority = if (animationFlowControl == null) 0 else 2
        }
    }
}
