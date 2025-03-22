package com.editor.cache.iface.util

import java.io.*
import java.util.*

/**
 * This object handles the loading and storing of various property values, such as cache path, dump path, and sprite path.
 * It loads the configuration from a `config.properties` file and provides utility methods to set the paths.
 *
 * @author paolo, Ceikry
 */
object PropertyValues {
    /**
     *  The path to the cache directory, loaded from the `config.properties` file.
     */
    var cache_path: String? = null

    /**
     *  The path to the directory where dumped sprites are stored. Default value is `data/export/sprites/`.
     */
    var dump_path: String = "data/export/sprites/"

    /**
     *  The path to the sprite directory, loaded from the `config.properties` file.
     */
    var sprite_path: String? = null

    /**
     *  Input stream for reading property values from the `config.properties` file.
     */
    var input: InputStream? = null

    /**
     * Loads the configuration values from the `config.properties` file.
     * This function will load the cache path, dump path, and sprite path from the properties file,
     * and assign them to the respective variables. If the file cannot be found or read, an error is logged.
     *
     * @throws IOException if there is an error reading the properties file.
     */
    @JvmStatic
    @Throws(IOException::class)
    fun loadValues() {
        try {
            val prop = Properties()
            val propFileName = "config.properties"

            input = FileInputStream("config.properties")

            if (input != null) {
                prop.load(input)
            } else {
                throw FileNotFoundException("property file '$propFileName' not found in the classpath")
            }

            println(prop.getProperty("cache_path"))
            cache_path = prop.getProperty("cache_path")
            dump_path = prop.getProperty("dump_path")
            sprite_path = prop.getProperty("sprite_path")
        } catch (e: Exception) {
            println("Exception: $e")
        } finally {
            input?.close()
        }
    }

    /**
     * Sets the cache path and automatically adjusts the dump path and sprite path based on the cache path.
     *
     * If the provided path does not end with a file separator, it will be added.
     *
     * @param path The new cache path to set.
     */
    @JvmStatic
    fun setCachePath(path: String) {
        var path = path
        if (!path.endsWith(File.separator)) path += File.separator
        cache_path = path
        dump_path = cache_path + "IFToolDumps" + File.separator
        sprite_path = cache_path
    }
}
