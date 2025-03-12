package util

import com.alex.store.Store
import java.io.File
import java.io.FileInputStream
import java.io.IOException

/**
 * The type Utils.
 */
object Utils {
    /**
     * Get bytes from file byte [ ].
     *
     * @param file the file
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    @JvmStatic
    @Throws(IOException::class)
    fun getBytesFromFile(file: File): ByteArray {
        FileInputStream(file).use { inputStream ->  // Automatically closes the stream when done
            val length = file.length()

            // Check if file length exceeds Integer.MAX_VALUE
            if (length > Int.MAX_VALUE) {
                throw IOException("File is too large to load into memory")
            }

            val bytes = ByteArray(length.toInt())
            var offset = 0

            // Read the file in chunks
            var numRead: Int
            while (offset < bytes.size) {
                numRead = inputStream.read(bytes, offset, bytes.size - offset)
                if (numRead == -1) {
                    break
                }
                offset += numRead
            }

            // Check if we have read the whole file
            if (offset < bytes.size) {
                throw IOException("Could not completely read file ${file.name}")
            }

            return bytes
        }
    }

    /**
     * Pack custom model int.
     *
     * @param cache the cache
     * @param data  the data
     * @return the int
     */
    @JvmStatic
    fun packCustomModel(cache: Store, data: ByteArray?): Int {
        val archiveId = cache.indexes[7].lastArchiveId + 1
        return if (cache.indexes[7].putFile(archiveId, 0, data)) {
            archiveId
        } else {
            println("Failed packing model $archiveId")
            -1
        }
    }

    /**
     * Pack custom model int.
     *
     * @param cache   the cache
     * @param data    the data
     * @param modelId the model id
     * @return the int
     */
    @JvmStatic
    fun packCustomModel(cache: Store, data: ByteArray?, modelId: Int): Int {
        return if (cache.indexes[7].putFile(modelId, 0, data)) {
            modelId
        } else {
            println("Failed packing model $modelId")
            -1
        }
    }
}
