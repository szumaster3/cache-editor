package com.editor.cache.item

import com.alex.defs.items.ItemDefinitions
import com.alex.defs.items.ItemDefinitions.Companion.getItemDefinition
import com.alex.filestore.Cache
import com.alex.util.Utils.getItemDefinitionsSize
import com.editor.cache.item.ItemSelection.Companion.CACHE
import console.Main.log
import java.awt.EventQueue
import java.io.IOException
import javax.swing.*
import javax.swing.LayoutStyle.ComponentPlacement

/**
 * A frame for selecting and editing item definitions in the cache.
 * This class allows viewing, adding, editing, duplicating, and deleting items from the cache.
 *
 * @property CACHE The cache object used to load item definitions.
 */
class ItemSelection : JFrame {
    private var itemDefinitionListModel: DefaultListModel<ItemDefinitions>? = null
    private var itemList: JList<ItemDefinitions>? = null

    /**
     * Constructor for initializing the frame with a given cache path.
     *
     * @param cache The path to the cache directory.
     */
    constructor(cache: String?) {
        CACHE = Cache(cache)
        this.isResizable = false
        this.defaultCloseOperation = 1
        this.setLocationRelativeTo(null)
        this.initComponents()
    }

    /**
     * Default constructor for initializing the frame without a cache path.
     */
    constructor() {
        this.initComponents()
    }

    /**
     * Initializes the UI components and sets up actions for buttons.
     */
    private fun initComponents() {
        val editButton = JButton()
        val addButton = JButton()
        val duplicateButton = JButton()
        val deleteButton = JButton()
        val jMenuBar1 = JMenuBar()
        val jMenu1 = JMenu()
        val exitButton = JMenuItem()

        /*
         * Setup item list and button actions.
         */

        this.defaultCloseOperation = 1
        this.itemDefinitionListModel = DefaultListModel<ItemDefinitions>()
        this.itemList = JList(this.itemDefinitionListModel)
        itemList?.selectionMode = 1
        itemList?.setLayoutOrientation(0)
        itemList?.setVisibleRowCount(-1)
        val jScrollPane1 = JScrollPane(this.itemList)
        editButton.text = "Edit"
        editButton.addActionListener {
            val definitions = itemList?.getSelectedValue()
            if (definitions != null) {
                ItemEditor(this@ItemSelection, definitions)
            }
        }
        addButton.text = "Add New"
        addButton.addActionListener {
            val definitions =
                ItemDefinitions(
                    CACHE!!,
                    newItemDefinition,
                    false,
                )
            if (definitions.id != -1) {
                ItemEditor(this@ItemSelection, definitions)
            }
        }
        duplicateButton.text = "Duplicate"
        duplicateButton.addActionListener {
            var definitions = itemList?.getSelectedValue()
            if (definitions != null) {
                definitions = definitions.clone() as ItemDefinitions
                definitions.id = this@ItemSelection.newItemDefinition
                if (definitions.id != -1) {
                    ItemEditor(this@ItemSelection, definitions)
                }
            }
        }
        deleteButton.text = "Delete"
        deleteButton.addActionListener({
            val definitions = itemList?.getSelectedValue()
            val frame = JFrame()
            val result =
                JOptionPane.showConfirmDialog(frame, "Do you really want to delete item [" + definitions!!.id + "]?")
            if (result == 0) {
                CACHE!!.indexes[19].removeFile(definitions.archiveId, definitions.fileId)
                this@ItemSelection.removeItemDefinition(definitions)
                log(name, "Item " + definitions.id + " removed.")
            }
        })

        /*
         * Setup menu bar with "Exit" option.
         */

        jMenu1.text = "File"
        exitButton.text = "Close"
        exitButton.addActionListener { evt -> this@ItemSelection.exitButtonActionPerformed() }
        jMenu1.add(exitButton)
        jMenuBar1.add(jMenu1)
        this.jMenuBar = jMenuBar1

        /*
         * Layout configuration.
         */

        val layout = GroupLayout(this.contentPane)
        this.contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane1, -2, 200, -2)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(editButton)
                                                    .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                                    .addComponent(addButton),
                                            ),
                                    ).addGap(0, 0, 32767),
                            ).addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(duplicateButton)
                                    .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                    .addComponent(deleteButton),
                            ),
                    ).addContainerGap(-1, 32767),
            ),
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, -2, 279, -2)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(editButton)
                            .addComponent(addButton),
                    ).addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(duplicateButton)
                            .addComponent(deleteButton),
                    ).addContainerGap(-1, 32767),
            ),
        )
        this.pack()
        this.showAllItemDefinitions()
    }

    /**
     * Handles the action when the "Exit" button is pressed, disposing the frame.
     */
    private fun exitButtonActionPerformed() {
        this.dispose()
    }

    /**
     * Returns the next available item definition ID.
     *
     * @return The next available item definition ID.
     */
    private val newItemDefinition: Int
        get() = getItemDefinitionsSize(CACHE!!)

    /**
     * Loads and displays all item definitions from the cache.
     */
    private fun showAllItemDefinitions() {
        var id: Int
        if (getItemDefinitionsSize(CACHE!!) > 30000) {
            id = 0
            while (id < getItemDefinitionsSize(CACHE!!) - 22314) {
                this.addItemDefinition(getItemDefinition(CACHE!!, id))
                ++id
            }
        } else {
            id = 0
            while (id < getItemDefinitionsSize(CACHE!!)) {
                this.addItemDefinition(getItemDefinition(CACHE!!, id))
                ++id
            }
        }

        log(name, "List loaded.")
    }

    /**
     * Adds a new item definition to the list model.
     *
     * @param definition The item definition to add.
     */
    private fun addItemDefinition(definition: ItemDefinitions?) {
        EventQueue.invokeLater { itemDefinitionListModel!!.addElement(definition) }
    }

    /**
     * Updates the item definition in the list model, or adds it if it doesn't exist.
     *
     * @param definition The item definition to update.
     */
    fun updateItemDefinition(definition: ItemDefinitions?) {
        EventQueue.invokeLater {
            val index = itemDefinitionListModel!!.indexOf(definition)
            if (index == -1) {
                itemDefinitionListModel!!.addElement(definition)
            } else {
                itemDefinitionListModel!!.setElementAt(definition, index)
            }
        }
    }

    /**
     * Removes an item definition from the list model.
     *
     * @param definition The item definition to remove.
     */
    private fun removeItemDefinition(definition: ItemDefinitions?) {
        EventQueue.invokeLater {
            itemDefinitionListModel!!.removeElement(
                definition,
            )
        }
    }

    companion object {
        var CACHE: Cache? = null

        /**
         * Main method to launch the ItemSelection window.
         *
         * @param args Command-line arguments (not used).
         * @throws IOException If an error occurs during cache loading.
         */
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            CACHE = Cache("cache/", false)
            EventQueue.invokeLater { (ItemSelection()).isVisible = true }
        }
    }
}
