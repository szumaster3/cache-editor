package com.editor.cache.item

import com.alex.Utils.getItemDefinitionsSize
import com.alex.loaders.items.ItemDefinitions
import com.alex.store.Store
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ItemListExport(cache: String) {

    private val store: Store = Store(cache)

    init {
        val directory = File("data/lists")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "item_list.txt")

        try {
            file.createNewFile()

            BufferedWriter(FileWriter(file)).use { writer ->
                writer.appendLine()

                for (id in 0 until getItemDefinitionsSize(store)) {
                    val def = ItemDefinitions.getItemDefinition(store, id)
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
