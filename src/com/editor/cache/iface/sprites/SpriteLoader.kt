package com.editor.cache.iface.sprites

import com.alex.filestore.Index
import com.alex.filestore.Store
import java.awt.image.BufferedImage
import java.io.IOException
import java.nio.ByteBuffer

object SpriteLoader {
    var spriteCache: HashMap<Int, SpriteArchive> = HashMap()
    var STORE: Store? = null

    @Throws(IOException::class)
    fun initStore(cachePath: String?) {
        if (STORE == null) {
            STORE = Store(cachePath)
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

        val idx = spriteIndex
        if (idx == null) {
            println("Sprite index is null.")
            return null
        }

        val spriteData = idx.getFile(archive)
        if (spriteData == null) {
            println("No sprite data found for archive $archive")
            return null
        }

        val spriteBuff = ByteBuffer.wrap(spriteData)
        val s = SpriteArchive.decode(spriteBuff)

        if (s == null) {
            println("Failed to decode sprite archive: $archive")
            return null
        }

        spriteCache[archive] = s
        return s
    }

    @JvmStatic
    fun getSprite(archive: Int): BufferedImage? {
        return getSprite(archive, 0)
    }

    fun getSprite(archive: Int, fileIndex: Int): BufferedImage? {
        val arch = getArchive(archive) ?: return null

        return arch.getSprite(fileIndex)
    }

    val spriteIndex: Index?
        get() {
            if (STORE == null) {
                println("Store is not initialized. Please initialize it first.")
                return null
            }

            if (STORE!!.indexes.size < 9) {
                println("Cache does not contain enough indexes.")
                return null
            }

            return STORE!!.indexes[8]
        }
}
