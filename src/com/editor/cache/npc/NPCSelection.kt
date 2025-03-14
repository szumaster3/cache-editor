package com.editor.cache.npc

import com.alex.Utils.getNPCDefinitionsSize
import com.alex.loaders.npcs.NPCDefinitions
import com.alex.store.Store
import com.editor.cache.item.ItemSelection
import console.Main.log
import java.awt.EventQueue
import java.io.IOException
import javax.swing.*
import javax.swing.LayoutStyle.ComponentPlacement

class NPCSelection : JFrame {
    private var addButton: JButton? = null
    private var duplicateButton: JButton? = null
    private var editButton: JButton? = null
    private var npcListModel: DefaultListModel<NPCDefinitions>? = null
    private var npcList: JList<NPCDefinitions?>? = null
    private var jMenu1: JMenu? = null
    private var jMenuBar1: JMenuBar? = null
    private var exitButton: JMenuItem? = null
    private var deleteButton: JButton? = null

    constructor(cache: String?) {
        STORE = Store(cache)
        this.title = "NPC Selection"
        this.isResizable = false
        this.defaultCloseOperation = 1
        this.setLocationRelativeTo(null)
        this.initComponents()
    }

    constructor() {
        this.initComponents()
    }

    private fun initComponents() {
        this.editButton = JButton()
        this.addButton = JButton()
        this.duplicateButton = JButton()
        this.deleteButton = JButton()
        this.jMenuBar1 = JMenuBar()
        this.jMenu1 = JMenu()
        this.exitButton = JMenuItem()
        this.defaultCloseOperation = 1
        this.npcListModel = DefaultListModel<NPCDefinitions>()
        this.npcList = JList<NPCDefinitions?>(this.npcListModel)
        npcList?.selectionMode = ListSelectionModel.SINGLE_SELECTION
        npcList?.layoutOrientation = JList.HORIZONTAL_WRAP
        npcList?.visibleRowCount = -1
        val jScrollPane1 = JScrollPane(this.npcList)

        editButton?.apply {
            text = "Edit"
            addActionListener {
                val defs = npcList?.selectedValue
                if (defs != null) {
                    NPCEditor(this@NPCSelection, defs)
                }
            }
        }

        addButton?.apply {
            text = "Add New"
            addActionListener {
                val npc = NPCDefinitions(ItemSelection.STORE, newNPCID, false)
                if (npc.id != -1) {
                    NPCEditor(this@NPCSelection, npc).isVisible = true
                }
            }
        }

        duplicateButton?.apply {
            text = "Duplicate"
            addActionListener {
                val npc = npcList?.selectedValue
                if (npc != null) {
                    val clonedNpc = npc.clone() as NPCDefinitions
                    clonedNpc.id = this@NPCSelection.newNPCID
                    if (clonedNpc.id != -1) {
                        NPCEditor(this@NPCSelection, clonedNpc).isVisible = true
                    }
                }
            }
        }

        deleteButton?.apply {
            text = "Delete"
            addActionListener {
                val defs = npcList?.selectedValue
                if (defs != null) {
                    val frame = JFrame()
                    val result = JOptionPane.showConfirmDialog(frame, "Do you really want to delete item ${defs.id}")
                    if (result == JOptionPane.YES_OPTION) {
                        STORE?.indexes?.get(18)?.removeFile(defs.archiveId, defs.fileId)
                        this@NPCSelection.removeNPCDefs(defs)
                        log("NPCSelection", "NPC ID: ${defs.id} removed.")
                    }
                }
            }
        }

        jMenu1?.apply {
            text = "File"
            exitButton?.apply {
                text = "Close"
                addActionListener { this@NPCSelection.exitButtonActionPerformed() }
            }
            add(exitButton)
        }

        jMenuBar1?.add(jMenu1)
        this.jMenuBar = jMenuBar1

        val layout = GroupLayout(this.contentPane)
        this.contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                        layout.createSequentialGroup().addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, -2, 200, -2).addGroup(
                                    layout.createSequentialGroup().addComponent(this.editButton)
                                        .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                        .addComponent(this.addButton)
                                )
                        ).addGap(0, 0, 32767)
                    ).addGroup(
                        layout.createSequentialGroup().addComponent(this.duplicateButton)
                            .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                            .addComponent(this.deleteButton)
                    )
                ).addContainerGap(-1, 32767)
            )
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -2, 279, -2)
                    .addPreferredGap(ComponentPlacement.RELATED).addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.editButton)
                            .addComponent(this.addButton)
                    ).addPreferredGap(ComponentPlacement.RELATED).addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.duplicateButton)
                            .addComponent(this.deleteButton)
                    ).addContainerGap(-1, 32767)
            )
        )

        this.pack()
        this.addAllNPCs()
    }

    private fun exitButtonActionPerformed() {
        this.dispose()
    }

    val newNPCID: Int
        get() = getNPCDefinitionsSize(STORE!!)

    fun addAllNPCs() {
        val npcCount = getNPCDefinitionsSize(STORE!!)
        println("NPC Count: $npcCount")
        var id = 0
        while (id < npcCount) {
            val npc = NPCDefinitions.getNPCDefinition(STORE, id)
            if (npc != null) {
                this.addNPCDefs(npc)
            } else {
                println("Error: NPC not loaded for ID $id")
            }
            ++id
        }
        log("NPCSelection", "All NPCs Loaded")
    }

    fun addNPCDefs(npc: NPCDefinitions?) {
        EventQueue.invokeLater { npcListModel?.addElement(npc) }
    }

    fun updateNPCDefs(npc: NPCDefinitions?) {
        EventQueue.invokeLater {
            val index = npcListModel?.indexOf(npc)
            if (index == -1) {
                npcListModel?.addElement(npc)
            } else {
                if (index != null) {
                    npcListModel?.setElementAt(npc, index)
                }
            }
        }
    }

    fun removeNPCDefs(npc: NPCDefinitions?) {
        EventQueue.invokeLater { npcListModel?.removeElement(npc) }
    }

    companion object {
        @JvmField
        var STORE: Store? = null

        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            STORE = Store("cache/", false)
            EventQueue.invokeLater { NPCSelection().isVisible = true }
        }
    }
}
