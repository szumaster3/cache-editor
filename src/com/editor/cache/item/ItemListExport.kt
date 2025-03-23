package com.editor.cache.item

import com.alex.defs.items.ItemDefinitions
import com.alex.filestore.Cache
import com.alex.util.Utils.getItemDefinitionsSize
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ItemListExport(
    cache: String,
) {
    private val cache: Cache = Cache(cache)

    init {
        val directory = File("data/export/lists/")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "item_list.txt")

        try {
            file.createNewFile()

            BufferedWriter(FileWriter(file)).use { writer ->
                writer.appendLine("Id - name")

                for (id in 0 until getItemDefinitionsSize(this.cache)) {
                    val def = ItemDefinitions.getItemDefinition(this.cache, id)
                    val itemInfo = "$id - ${def.name}"
                    writer.appendLine(itemInfo)
                    println(itemInfo)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
