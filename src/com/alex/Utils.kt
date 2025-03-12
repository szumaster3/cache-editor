package com.alex

import com.alex.io.OutputStream
import com.alex.store.Store
import java.math.BigInteger
import java.util.*

/**
 * The type Utils.
 */
object Utils {
    /**
     * Crypt rsa byte [ ].
     *
     * @param data     the data
     * @param exponent the exponent
     * @param modulus  the modulus
     * @return the byte [ ]
     */
    @JvmStatic
    fun cryptRSA(data: ByteArray, exponent: BigInteger?, modulus: BigInteger?): ByteArray {
        return (BigInteger(data)).modPow(exponent, modulus).toByteArray()
    }

    /**
     * Get archive packet data byte [ ].
     *
     * @param indexId   the index id
     * @param archiveId the archive id
     * @param archive   the archive
     * @return the byte [ ]
     */
    @JvmStatic
    fun getArchivePacketData(indexId: Int, archiveId: Int, archive: ByteArray): ByteArray {
        val stream = OutputStream(archive.size + 4)
        stream.writeByte(indexId)
        stream.writeShort(archiveId)
        stream.writeByte(0)
        stream.writeInt(archive.size)
        var offset = 8

        for (var6 in archive.indices) {
            if (offset == 512) {
                stream.writeByte(-1)
                offset = 1
            }

            stream.writeByte(archive[var6].toInt())
            ++offset
        }

        val var61 = ByteArray(stream.offset)
        stream.offset = 0
        stream.getBytes(var61, 0, var61.size)
        return var61
    }

    /**
     * Gets name hash.
     *
     * @param name the name
     * @return the name hash
     */
    @JvmStatic
    fun getNameHash(name: String): Int {
        return name.lowercase(Locale.getDefault()).hashCode()
    }

    /**
     * Gets interface definitions size.
     *
     * @param store the store
     * @return the interface definitions size
     */
    fun getInterfaceDefinitionsSize(store: Store): Int {
        return store.indexes[3].lastArchiveId + 1
    }

    /**
     * Gets interface definitions components size.
     *
     * @param store       the store
     * @param interfaceId the interface id
     * @return the interface definitions components size
     */
    fun getInterfaceDefinitionsComponentsSize(store: Store, interfaceId: Int): Int {
        return store.indexes[3].getLastFileId(interfaceId) + 1
    }

    /**
     * Gets animation definitions size.
     *
     * @param store the store
     * @return the animation definitions size
     */
    fun getAnimationDefinitionsSize(store: Store): Int {
        val lastArchiveId = store.indexes[20].lastArchiveId
        return lastArchiveId * 128 + store.indexes[20].getValidFilesCount(lastArchiveId)
    }

    /**
     * Gets item definitions size.
     *
     * @param store the store
     * @return the item definitions size
     */
    @JvmStatic
    fun getItemDefinitionsSize(store: Store): Int {
        val lastArchiveId = store.indexes[19].lastArchiveId
        return lastArchiveId * 256 + store.indexes[19].getValidFilesCount(lastArchiveId)
    }

    /**
     * Gets npc definitions size.
     *
     * @param store the store
     * @return the npc definitions size
     */
    @JvmStatic
    fun getNPCDefinitionsSize(store: Store): Int {
        val lastArchiveId = store.indexes[18].lastArchiveId
        return lastArchiveId * 128 + store.indexes[18].getValidFilesCount(lastArchiveId)
    }

    /**
     * Gets object definitions size.
     *
     * @param store the store
     * @return the object definitions size
     */
    @JvmStatic
    fun getObjectDefinitionsSize(store: Store): Int {
        val lastArchiveId = store.indexes[16].lastArchiveId
        return lastArchiveId * 256 + store.indexes[16].getValidFilesCount(lastArchiveId)
    }

    /**
     * Gets graphic definitions size.
     *
     * @param store the store
     * @return the graphic definitions size
     */
    fun getGraphicDefinitionsSize(store: Store): Int {
        val lastArchiveId = store.indexes[21].lastArchiveId
        return lastArchiveId * 256 + store.indexes[21].getValidFilesCount(lastArchiveId)
    }
}
