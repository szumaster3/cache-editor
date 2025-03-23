package com.editor.cache.npc

import com.alex.defs.npcs.NPCDefinitions
import com.alex.filestore.Cache
import com.alex.util.Utils.getNPCDefinitionsSize
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class NPCListExport(
    cache: String,
) {
    private val cache: Cache = Cache(cache)

    init {
        val directory = File("data/export/lists/")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "npc_list.txt")

        try {
            file.createNewFile()

            BufferedWriter(FileWriter(file)).use { writer ->
                writer.appendLine("Id - name")

                for (id in 0 until getNPCDefinitionsSize(this.cache)) {
                    val def = NPCDefinitions.getNPCDefinition(this.cache, id)
                    val npcInfo = "$id - ${def.name}"
                    writer.appendLine(npcInfo)
                    println(npcInfo)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
