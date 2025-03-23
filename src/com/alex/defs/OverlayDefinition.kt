package com.alex.defs

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.io.OutputStream
import java.util.concurrent.ConcurrentHashMap

class OverlayDefinition {
    companion object {
        private val cachedDefinition = ConcurrentHashMap<Int, OverlayDefinition>()

        fun getOverlayDefinition(
            cache: Cache,
            id: Int,
        ): OverlayDefinition =
            cachedDefinition[id] ?: run {
                val data = cache.getIndexes()[2].getFile(4, id)
                val definition =
                    OverlayDefinition().apply {
                        this.id = id
                        data?.let { decode(InputStream(it)) }
                        processIdAndOpcode()
                    }
                cachedDefinition[id] = definition
                definition
            }
    }

    var id: Int = -1
    var rgbColor: Int = -1
    var textureId: Int = -1
    var hideUnderlay: Boolean = true
    var blendColor: Int = -1
    var additionalBlend: Int = -1
    var blendMultiplier: Int = -1
    var isNonSelectable: Boolean = true
    var textureOverride: Int = -1
    var isRecolored: Boolean = false
    var recolorRgb: Int = -1
    var recolorMultiplier: Int = -1
    var isHidden: Boolean = false
    var additionalTextureId: Int = -1

    init {
    }

    private fun decode(stream: InputStream) {
        while (true) {
            val opcode = stream.readUnsignedByte()
            if (opcode == 0) break
            decode(stream, opcode)
        }
    }

    private fun decode(
        stream: InputStream,
        opcode: Int,
    ) {
        when (opcode) {
            1 -> rgbColor = stream.readMedium()
            2 -> textureId = stream.readUnsignedByte()
            3 -> {
                textureId = stream.readUnsignedShort()
                if (textureId == 65565) textureId = -1
            }

            5 -> hideUnderlay = true
            7 -> blendColor = stream.readMedium()
            8 -> {}
            9 -> blendMultiplier = stream.readUnsignedShort() shl 2
            10 -> isNonSelectable = false
            11 -> textureOverride = stream.readUnsignedByte()
            12 -> isRecolored = true
            13 -> recolorRgb = stream.readMedium()
            14 -> recolorMultiplier = stream.readUnsignedByte() shl 2
            16 -> additionalBlend = stream.readUnsignedByte()
            20 -> additionalTextureId = stream.readUnsignedShort()
            21 -> isHidden = true
            22 -> additionalTextureId = stream.readUnsignedShort()
        }
    }

    private fun processIdAndOpcode() {
        textureOverride = (textureOverride shl 8) or id
    }

    fun write(cache: Cache) {
        cache.indexes[2].putFile(4, id, encode())
    }

    private fun encode(): ByteArray {
        val stream = OutputStream()

        if (rgbColor != -1) {
            stream.writeByte(1)
            stream.writeMedium(rgbColor)
        }

        if (textureId != -1) {
            stream.writeByte(3)
            stream.writeShort(textureId)
        }

        if (hideUnderlay) stream.writeByte(5)

        if (blendColor != -1) {
            stream.writeByte(7)
            stream.writeMedium(blendColor)
        }

        if (blendMultiplier != -1) {
            stream.writeByte(9)
            stream.writeShort(blendMultiplier shr 2)
        }

        if (!isNonSelectable) stream.writeByte(10)

        if (textureOverride != -1) {
            stream.writeByte(11)
            stream.writeByte(textureOverride)
        }

        if (isRecolored) stream.writeByte(12)

        if (recolorRgb != -1) {
            stream.writeByte(13)
            stream.writeMedium(recolorRgb)
        }

        if (recolorMultiplier != -1) {
            stream.writeByte(14)
            stream.writeByte(recolorMultiplier shr 2)
        }

        if (additionalBlend != -1) {
            stream.writeByte(16)
            stream.writeByte(additionalBlend)
        }

        if (additionalTextureId != -1) {
            stream.writeByte(20)
            stream.writeShort(additionalTextureId)
        }

        if (isHidden) {
            stream.writeByte(21)
            stream.writeByte(1)
        }

        if (additionalTextureId != -1) {
            stream.writeByte(22)
            stream.writeShort(additionalTextureId)
        }

        stream.writeByte(0)
        return ByteArray(stream.offset).also { payload ->
            stream.setOffset(0)
            stream.getBytes(payload, 0, payload.size)
        }
    }
}
