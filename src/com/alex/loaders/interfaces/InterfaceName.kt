package com.alex.loaders.interfaces

import com.alex.store.Store
import com.alex.Utils
import java.io.IOException

/**
 * The type Interface name.
 */
object InterfaceName {
    /**
     * The constant VALID_CHARS.
     */
    val VALID_CHARS: CharArray = charArrayOf(
        'a',
        'b',
        'c',
        'd',
        'e',
        'f',
        'g',
        'h',
        'i',
        'j',
        'k',
        'l',
        'm',
        'n',
        'o',
        'p',
        'q',
        'r',
        's',
        't',
        'u',
        'v',
        'w',
        'x',
        'y',
        'z'
    )

    /**
     * Print all combinations 4 letters.
     */
    fun printAllCombinations4Letters() {
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val rscache = Store("cache/", false)
        val hash = rscache.indexes[3].table.archives[884]?.nameHash
        val `arr$` = VALID_CHARS
        val `len$` = `arr$`.size

        for (`i$` in 0 until `len$`) {
            val l1 = `arr$`[`i$`]
            println(l1)
            val `arr$1` = VALID_CHARS
            val `len$1` = `arr$1`.size

            for (`i$1` in 0 until `len$1`) {
                val l2 = `arr$1`[`i$1`]
                val `arr$2` = VALID_CHARS
                val `len$2` = `arr$2`.size

                for (`i$2` in 0 until `len$2`) {
                    val l3 = `arr$2`[`i$2`]
                    val `arr$3` = VALID_CHARS
                    val `len$3` = `arr$3`.size

                    for (`i$3` in 0 until `len$3`) {
                        val l4 = `arr$3`[`i$3`]
                        val `arr$4` = VALID_CHARS
                        val `len$4` = `arr$4`.size

                        for (`i$4` in 0 until `len$4`) {
                            val l5 = `arr$4`[`i$4`]
                            val `arr$5` = VALID_CHARS
                            val `len$5` = `arr$5`.size

                            for (`i$5` in 0 until `len$5`) {
                                val l6 = `arr$5`[`i$5`]
                                val name = String(charArrayOf(l1, l2, l3, l4, l5, l6))
                                if (Utils.getNameHash(name) == hash) {
                                    println(name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
