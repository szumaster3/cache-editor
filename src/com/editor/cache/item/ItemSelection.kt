package com.editor.cache.item

import com.alex.defs.items.ItemDefinitions
import com.alex.defs.items.ItemDefinitions.Companion.getItemDefinition
import com.alex.filestore.Cache
import com.alex.util.Utils.getItemDefinitionsSize
import console.Main.log
import java.awt.EventQueue
import java.io.IOException
import javax.swing.*
import javax.swing.LayoutStyle.ComponentPlacement

class ItemSelection : JFrame {
    private var itemDefinitionListModel: DefaultListModel<ItemDefinitions>? = null
    private var itemList: JList<ItemDefinitions>? = null
    private val fullItemList = mutableListOf<ItemDefinitions>()

    constructor(cache: String?) {
        CACHE = Cache(cache)
        title = "Item Selection"
        isResizable = false
        defaultCloseOperation = 2
        setLocationRelativeTo(null)
        initComponents()
    }

    constructor() {
        initComponents()
    }

    private fun initComponents() {
        val editButton = JButton("Edit")
        val addButton = JButton("Add New")
        val duplicateButton = JButton("Duplicate")
        val deleteButton = JButton("Delete")
        val jMenuBar1 = JMenuBar()
        val jMenu1 = JMenu("File")
        val exitButton = JMenuItem("Close")
        val searchField =
            JTextField().apply {
                toolTipText = "Search by name"
                columns = 20
            }

        itemDefinitionListModel = DefaultListModel()
        itemList =
            JList(itemDefinitionListModel).apply {
                selectionMode = ListSelectionModel.SINGLE_SELECTION
                layoutOrientation = JList.VERTICAL
                visibleRowCount = -1
            }
        val jScrollPane1 = JScrollPane(itemList)

        editButton.addActionListener {
            val definitions = itemList?.selectedValue
            if (definitions != null) {
                ItemEditor(this@ItemSelection, definitions)
            }
        }

        addButton.addActionListener {
            val definitions = ItemDefinitions(CACHE!!, newItemDefinition, false)
            if (definitions.id != -1) {
                ItemEditor(this@ItemSelection, definitions)
            }
        }

        duplicateButton.addActionListener {
            var definitions = itemList?.selectedValue
            if (definitions != null) {
                definitions = definitions.clone() as ItemDefinitions
                definitions.id = newItemDefinition
                if (definitions.id != -1) {
                    ItemEditor(this@ItemSelection, definitions)
                }
            }
        }

        deleteButton.addActionListener {
            val definitions = itemList?.selectedValue
            val frame = JFrame()
            val result =
                JOptionPane.showConfirmDialog(
                    frame,
                    "Do you really want to delete item [${definitions?.id}]?"
                )
            if (result == JOptionPane.YES_OPTION && definitions != null) {
                CACHE!!.indexes[19].removeFile(definitions.archiveId, definitions.fileId)
                fullItemList.remove(definitions)
                removeItemDefinition(definitions)
                log(name, "Item ${definitions.id} removed.")
            }
        }

        exitButton.addActionListener { exitButtonActionPerformed() }
        jMenu1.add(exitButton)
        jMenuBar1.add(jMenu1)
        jMenuBar = jMenuBar1

        searchField.document.addDocumentListener(
            object : javax.swing.event.DocumentListener {
                private fun filter() {
                    val text = searchField.text.lowercase()
                    val filtered =
                        fullItemList.filter { it.name?.lowercase()?.contains(text) == true }
                    SwingUtilities.invokeLater {
                        itemList?.setUI(null)
                        itemDefinitionListModel!!.clear()
                        filtered.forEach { itemDefinitionListModel!!.addElement(it) }
                        itemList?.updateUI()
                    }
                }

                override fun insertUpdate(e: javax.swing.event.DocumentEvent?) = filter()

                override fun removeUpdate(e: javax.swing.event.DocumentEvent?) = filter()

                override fun changedUpdate(e: javax.swing.event.DocumentEvent?) = filter()
            }
        )

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                            layout
                                .createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(
                                    searchField,
                                    GroupLayout.PREFERRED_SIZE,
                                    200,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    jScrollPane1,
                                    GroupLayout.PREFERRED_SIZE,
                                    200,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addGroup(
                                    layout
                                        .createSequentialGroup()
                                        .addComponent(editButton)
                                        .addPreferredGap(
                                            ComponentPlacement.RELATED,
                                            GroupLayout.DEFAULT_SIZE,
                                            Int.MAX_VALUE
                                        )
                                        .addComponent(addButton),
                                )
                                .addGroup(
                                    layout
                                        .createSequentialGroup()
                                        .addComponent(duplicateButton)
                                        .addPreferredGap(
                                            ComponentPlacement.RELATED,
                                            GroupLayout.DEFAULT_SIZE,
                                            Int.MAX_VALUE
                                        )
                                        .addComponent(deleteButton),
                                ),
                        )
                        .addContainerGap()
                )
        )

        layout.setVerticalGroup(
            layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    layout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addComponent(
                            searchField,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(
                            jScrollPane1,
                            GroupLayout.PREFERRED_SIZE,
                            279,
                            GroupLayout.PREFERRED_SIZE
                        )
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(
                            layout
                                .createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(editButton)
                                .addComponent(addButton),
                        )
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(
                            layout
                                .createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(duplicateButton)
                                .addComponent(deleteButton),
                        )
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Int.MAX_VALUE)
                )
        )
        this.pack()

        Thread {
            var id = 0
            val size = getItemDefinitionsSize(CACHE!!)
            while (id < size) {
                val def = getItemDefinition(CACHE!!, id)
                if (def != null) {
                    fullItemList.add(def)
                    SwingUtilities.invokeLater { itemDefinitionListModel!!.addElement(def) }
                }
                id++
            }
            log(name, "List loaded.")
        }
            .start()
    }

    private fun exitButtonActionPerformed() {
        dispose()
    }

    private val newItemDefinition: Int
        get() = getItemDefinitionsSize(CACHE!!)

    private fun removeItemDefinition(definition: ItemDefinitions?) {
        EventQueue.invokeLater { itemDefinitionListModel!!.removeElement(definition) }
    }

    fun updateItemDefinition(definition: ItemDefinitions?) {
        EventQueue.invokeLater {
            val index = itemDefinitionListModel!!.indexOf(definition)
            if (index == -1) {
                itemDefinitionListModel!!.addElement(definition)
                fullItemList.add(definition!!)
            } else {
                itemDefinitionListModel!!.setElementAt(definition, index)
                fullItemList[index] = definition!!
            }
        }
    }

    companion object {
        var CACHE: Cache? = null

        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            CACHE = Cache("cache/", false)
            EventQueue.invokeLater { ItemSelection().isVisible = true }
        }
    }
}
