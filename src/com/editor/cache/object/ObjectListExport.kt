package com.editor.cache.`object`

import com.alex.defs.objects.ObjectDefinitions
import com.alex.filestore.Cache
import com.alex.util.Utils.getObjectDefinitionsSize
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class ObjectListExport(
    cache: String,
) {
    private val cache: Cache = Cache(cache)

    init {
        val directory = File("data/export/lists/")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "object_list.txt")

        try {
            file.createNewFile()

            BufferedWriter(FileWriter(file)).use { writer ->
                writer.appendLine("Id - name")

                for (id in 0 until getObjectDefinitionsSize(this.cache)) {
                    val def = ObjectDefinitions.getObjectDefinition(this.cache, id)
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
