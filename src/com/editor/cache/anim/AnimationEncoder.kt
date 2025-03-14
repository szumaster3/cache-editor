package com.editor.cache.anim

import com.alex.loaders.animations.AnimationDefinitions
import java.nio.ByteBuffer

object AnimationEncoder {
    fun encode(animation: AnimationDefinitions): ByteArray {
        val buffer = ByteBuffer.allocate(1024)

        buffer.putShort(animation.loopCycles.toShort())
        buffer.put(animation.priority.toByte())
        buffer.putShort(animation.leftHandEquip.toShort())
        buffer.putShort(animation.rightHandEquip.toShort())
        buffer.putShort(animation.anInt2137.toShort())
        buffer.putShort(animation.anInt2140.toShort())
        buffer.putShort(animation.anInt2145.toShort())
        buffer.putShort(animation.anInt2155.toShort())
        buffer.putShort(animation.anInt2162.toShort())
        buffer.putShort(animation.loopDelay.toShort())
        buffer.put(if (animation.aBoolean2141) 1 else 0)

        animation.handledSounds?.let {
            buffer.put(it.size.toByte())
            it.forEach { soundArray ->
                buffer.put(soundArray!!.size.toByte())
                soundArray.forEach { buffer.putShort(it.toShort()) }
            }
        } ?: buffer.put(0)


        animation.aBooleanArray2149?.let {
            buffer.put(it.size.toByte())
            it.forEach { buffer.put(if (it) 1 else 0) }
        } ?: buffer.put(0)

        animation.anIntArray2151.let {
            buffer.put(it?.size!!.toByte())
            it.forEach { buffer.putShort(it.toShort()) }
        }

        buffer.put(if (animation.aBoolean2152) 1 else 0)

        animation.soundMinDelay?.let {
            buffer.put(it.size.toByte())
            it.forEach { buffer.putShort(it.toShort()) }
        } ?: buffer.put(0)

        animation.soundMaxDelay?.let {
            buffer.put(it.size.toByte())
            it.forEach { buffer.putShort(it.toShort()) }
        } ?: buffer.put(0)

        animation.anIntArray1362?.let {
            buffer.put(it.size.toByte())
            it.forEach { buffer.putShort(it.toShort()) }
        } ?: buffer.put(0)

        buffer.put(if (animation.effect2Sound) 1 else 0)

        return buffer.array().copyOf(buffer.position())
    }
}
