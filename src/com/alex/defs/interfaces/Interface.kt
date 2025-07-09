package com.alex.defs.interfaces

import com.alex.filestore.Cache
import com.alex.io.InputStream
import com.alex.util.Utils
import java.awt.Component
import java.awt.Image
import java.awt.image.FilteredImageSource
import java.awt.image.ReplicateScaleFilter
import java.io.IOException
import javax.swing.JComponent

/**
 * The type Interface.
 */
class Interface
@JvmOverloads
constructor(
    /**
     * The Id.
     */
    var id: Int,
    /**
     * The cache.
     */
    var cache: Cache,
    load: Boolean = true,
) {
    /**
     * The Components.
     */
    lateinit var components: Array<IComponent?>

    /**
     * The Jcomponents.
     */
    lateinit var jcomponents: Array<JComponent>

    /**
     * Instantiates a new Interface.
     *
     * @param id    the id
     * @param cache the cache
     * @param load  the load
     */

    /**
     * Instantiates a new Interface.
     *
     * @param id    the id
     * @param cache the cache
     */
    init {
        if (load) {
            this.getComponents()
        }
    }

    /**
     * Draw.
     *
     * @param parent the parent
     */
    fun draw(parent: JComponent?) {
    }

    /**
     * Resize image image.
     *
     * @param image  the image
     * @param width  the width
     * @param height the height
     * @param c      the c
     * @return the image
     */
    fun resizeImage(
        image: Image,
        width: Int,
        height: Int,
        c: Component,
    ): Image {
        val replicate = ReplicateScaleFilter(width, height)
        val prod = FilteredImageSource(image.source, replicate)
        return c.createImage(prod)
    }

    /**
     * Gets components.
     */
    fun getComponents() {
        if (Utils.getInterfaceDefinitionsSize(this.cache) <= this.id) {
            throw RuntimeException("Invalid interface id.")
        } else {
            this.components =
                arrayOfNulls(
                    Utils.getInterfaceDefinitionsComponentsSize(this.cache, this.id),
                )

            for (componentId in components.indices) {
                // components[componentId] = IComponent()
                components[componentId]!!.hash = this.id shl 16 or componentId
                val data =
                    cache.indexes[3].getFile(this.id, componentId)
                        ?: throw RuntimeException("Interface " + this.id + ", component " + componentId + " data is null.")

                println("Interface: " + this.id + " - ComponentId: " + componentId)
                if (data[0].toInt() != -1) {
                    components[componentId]!!.decodeNoscriptsFormat(InputStream(data))
                } else {
                    components[componentId]!!.decodeScriptsFormat(InputStream(data))
                }
            }
        }
    }

    companion object {
        /**
         * The entry point of application.
         *
         * @param args the input arguments
         * @throws IOException the io exception
         */
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val rscache = Cache("cache/")

            for (i in 0 until Utils.getInterfaceDefinitionsSize(rscache)) {
                try {
                    Interface(i, rscache)
                } catch (var4: Throwable) {
                }
            }
        }
    }
}
