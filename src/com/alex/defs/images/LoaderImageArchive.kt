package com.alex.defs.images

import com.alex.filestore.Cache
import java.awt.Image
import java.awt.Toolkit

/**
 * The type Loader image archive.
 */
class LoaderImageArchive {
    /**
     * Get image data byte [ ].
     *
     * @return the byte [ ]
     */
    lateinit var imageData: ByteArray
        private set

    /**
     * Instantiates a new Loader image archive.
     *
     * @param data the data
     */
    constructor(data: ByteArray) {
        this.imageData = data
    }

    /**
     * Instantiates a new Loader image archive.
     *
     * @param cache     the cache
     * @param archiveId the archive id
     */
    constructor(cache: Cache, archiveId: Int) : this(cache, 32, archiveId, 0)

    private constructor(cache: Cache, idx: Int, archiveId: Int, fileId: Int) {
        this.decodeArchive(cache, idx, archiveId, fileId)
    }

    private fun decodeArchive(
        cache: Cache,
        idx: Int,
        archiveId: Int,
        fileId: Int,
    ) {
        val data = cache.indexes[idx].getFile(archiveId, fileId)
        if (data != null) {
            this.imageData = data
        }
    }

    val image: Image
        /**
         * Gets image.
         *
         * @return the image
         */
        get() = Toolkit.getDefaultToolkit().createImage(this.imageData)
}
