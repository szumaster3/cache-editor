package com.editor.cache.iface.sprites

import com.alex.filestore.Cache
import com.alex.filestore.Index
import com.editor.cache.iface.util.PropertyValues
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.nio.ByteBuffer

object SpriteLoader {
    var spriteCache: HashMap<Int, SpriteArchive> = HashMap()
    var Cache: Cache? = null

    @Throws(Exception::class)
    fun initStore(cachePath: String) {
        if (Cache == null) {
            Cache = Cache(cachePath)
        }
    }

    val numSprites: Int
        get() {
            val spriteIndex = spriteIndex
            return spriteIndex?.lastArchiveId ?: 0
        }

    fun getArchive(archive: Int): SpriteArchive? {
        if (spriteCache.containsKey(archive)) {
            return spriteCache[archive]
        }

        val idx = spriteIndex ?: run {
            println("Sprite index is null.")
            return null
        }

        val spriteData = idx.getFile(archive) ?: run {
            println("No sprite data found for archive $archive")
            return null
        }

        val spriteBuff = ByteBuffer.wrap(spriteData)
        val s = SpriteArchive.decode(spriteBuff) ?: run {
            println("Failed to decode sprite archive: $archive")
            return null
        }

        spriteCache[archive] = s
        return s
    }

    private fun getSpriteFromDump(archive: Int, frame: Int): BufferedImage? {
        val file = File(PropertyValues.dump_path, "${archive}_$frame.png")
        return if (file.exists()) {
            try {
                ImageIO.read(file)
            } catch (e: Exception) {
                println("Failed to load dumped sprite: ${file.absolutePath}")
                null
            }
        } else null
    }

    fun getSprite(archive: Int, fileIndex: Int = 0): BufferedImage? {
        val fromDump = getSpriteFromDump(archive, fileIndex)
        if (fromDump != null) {
            return fromDump
        }
        val arch = getArchive(archive) ?: return null
        return arch.getSprite(fileIndex)
    }

    val spriteIndex: Index?
        get() {
            if (Cache == null) {
                println("Cache is not initialized. Please initialize it first.")
                return null
            }
            if (Cache!!.indexes.size < 9) {
                println("Cache does not contain enough indexes.")
                return null
            }
            return Cache!!.indexes[8]
        }
}
