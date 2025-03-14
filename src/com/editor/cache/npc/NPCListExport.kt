package com.editor.cache.npc

import com.alex.Utils.getNPCDefinitionsSize
import com.alex.loaders.npcs.NPCDefinitions
import com.alex.store.Store
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class NPCListExport(cache: String) {

    private val store: Store = Store(cache)

    init {
        val directory = File("data/lists")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "npc_list.txt")

        try {
            file.createNewFile()

            BufferedWriter(FileWriter(file)).use { writer ->
                writer.appendLine()

                for (id in 0 until getNPCDefinitionsSize(store)) {
                    val def = NPCDefinitions.getNPCDefinition(store, id)
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
