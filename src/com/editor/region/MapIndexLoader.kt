package com.editor.region

import com.displee.cache.index.archive.Archive
import java.nio.Buffer

/**
 * The type Map index loader.
 */
abstract class MapIndexLoader {
    /**
     * Init.
     *
     * @param archive the archive
     */
    abstract fun init(archive: Archive?)

    /**
     * Init.
     *
     * @param buffer the buffer
     */
    abstract fun init(buffer: Buffer?)

    /**
     * Gets file id.
     *
     * @param hash the hash
     * @param type the type
     * @return the file id
     */
    abstract fun getFileId(hash: Int, type: MapType?): Int

    /**
     * Landscape present boolean.
     *
     * @param id the id
     * @return the boolean
     */
    abstract fun landscapePresent(id: Int): Boolean

    /**
     * Object present boolean.
     *
     * @param id the id
     * @return the boolean
     */
    abstract fun objectPresent(id: Int): Boolean

    /**
     * Set.
     *
     * @param regionX     the region x
     * @param regionY     the region y
     * @param landscapeId the landscape id
     * @param objectsId   the objects id
     */
    abstract fun set(regionX: Int, regionY: Int, landscapeId: Int, objectsId: Int)

    /**
     * Encode byte [ ].
     *
     * @return the byte [ ]
     */
    abstract fun encode(): ByteArray?

    /**
     * Gets file name.
     *
     * @param hash the hash
     * @param type the type
     * @return the file name
     */
    fun getFileName(hash: Int, type: MapType?): String {
        return lookup(hash, type).toString()
    }

    companion object {
        /**
         * The constant instance.
         */
        var instance: MapIndexLoader? = null

        /**
         * Lookup int.
         *
         * @param hash the hash
         * @param type the type
         * @return the int
         */
        fun lookup(hash: Int, type: MapType?): Int {
            return instance!!.getFileId(hash, type)
        }

        /**
         * Resolve int.
         *
         * @param regionX the region x
         * @param regionY the region y
         * @param type    the type
         * @return the int
         */
        fun resolve(regionX: Int, regionY: Int, type: MapType?): Int {
            val code = (regionX shl 8) + regionY
            return lookup(code, type)
        }

        /**
         * Gets landscape id.
         *
         * @param regionX the region x
         * @param regionY the region y
         * @return the landscape id
         */
        fun getLandscapeId(regionX: Int, regionY: Int): Int {
            return resolve(regionX, regionY, MapType.LANDSCAPE)
        }

        /**
         * Gets object id.
         *
         * @param regionX the region x
         * @param regionY the region y
         * @return the object id
         */
        fun getObjectId(regionX: Int, regionY: Int): Int {
            return resolve(regionX, regionY, MapType.OBJECT)
        }

        /**
         * Sets region data.
         *
         * @param regionX     the region x
         * @param regionY     the region y
         * @param landscapeId the landscape id
         * @param objectsId   the objects id
         */
        fun setRegionData(regionX: Int, regionY: Int, landscapeId: Int, objectsId: Int) {
            instance!!.set(regionX, regionY, landscapeId, objectsId)
        }

        /**
         * Gets name.
         *
         * @param regionX the region x
         * @param regionY the region y
         * @param type    the type
         * @return the name
         */
        fun getName(regionX: Int, regionY: Int, type: MapType?): String {
            val code = (regionX shl 8) + regionY
            return instance!!.getFileName(code, type)
        }
    }
}
