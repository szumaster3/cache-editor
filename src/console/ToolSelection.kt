package console

import com.editor.iface.InterfaceEditor
import com.editor.item.ItemSelection
import com.editor.npc.NPCSelection
import com.editor.`object`.ObjectSelection
import com.editor.region.RegionEditor
import java.awt.*
import java.awt.event.ActionEvent
import java.io.IOException
import javax.swing.*
import kotlin.system.exitProcess

/**
 * The type Tool selection.
 */
class ToolSelection : JFrame() {
    private var cache = ""
    private var loadCacheButton: JButton? = null
    private var selectionBox: JComboBox<String>? = null

    /**
     * Instantiates a new Tool selection.
     */
    init {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        this.title = "Tool Selection"
        this.isResizable = false
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocationRelativeTo(null)
        this.initComponents()
        Main.log("Main", "ToolSelection Started")
    }

    private fun initComponents() {
        val alignmentPanel1 = JPanel(FlowLayout())
        val alignmentPanel2 = JPanel(FlowLayout())
        val alignmentPanel3 = JPanel(FlowLayout())

        this.preferredSize = Dimension(250, 200)
        val selectYourEditorLabel = JLabel("Select your editor:")
        this.selectionBox = JComboBox()
        val submitButton = JButton()
        val jMenuBar1 = JMenuBar()
        val jMenu1 = JMenu()
        this.loadCacheButton = JButton()
        val exitButton = JMenuItem()
        this.defaultCloseOperation = 3

        loadCacheButton!!.text = "Load Cache"
        loadCacheButton!!.addActionListener { e ->
            loadCacheButtonHandler(
                e
            )
        }
        loadCacheButton!!.preferredSize = Dimension(125, 30)
        alignmentPanel1.add(loadCacheButton, BorderLayout.CENTER)

        alignmentPanel2.add(selectYourEditorLabel)

        selectionBox!!.model = DefaultComboBoxModel(
            arrayOf(
                "Item Editor",
                "NPCs Editor",
                "Region Editor",
                "Interface Editor",
                "Object Editor"
            )
        )
        submitButton.text = "Submit"
        submitButton.addActionListener { evt: ActionEvent ->
            this@ToolSelection.submitButtonActionPerformed(
                evt
            )
        }

        alignmentPanel3.add(this.selectionBox)
        alignmentPanel3.add(submitButton)

        jMenu1.text = "File"
        exitButton.text = "Exit Program"
        exitButton.addActionListener { evt: ActionEvent ->
            this@ToolSelection.exitButtonActionPerformed(
                evt
            )
        }
        jMenu1.add(exitButton)
        jMenuBar1.add(jMenu1)
        this.jMenuBar = jMenuBar1
        val layout = GridLayout(3, 1, 5, 10)
        this.contentPane.layout = layout
        this.add(alignmentPanel1)
        this.add(alignmentPanel2)
        this.add(alignmentPanel3)
        this.pack()
    }

    private fun submitButtonActionPerformed(evt: ActionEvent) {
        if (cache.isEmpty()) {
            try {
                Main.log("ToolSelection", "No Cache Set!")
            } catch (e: ArrayIndexOutOfBoundsException) {
                Main.log("ToolSelection", "No Cache Set!")
            }
        } else if (selectionBox!!.selectedIndex == 0) {
            try {
                (ItemSelection(this.cache)).isVisible = true
                Main.log("ToolSelection", "ItemSelection Started")
            } catch (var4: IOException) {
                Main.log("ToolSelection", "No Cache Set!")
            }
        } else if (selectionBox!!.selectedIndex == 1) {
            try {
                (NPCSelection(this.cache)).isVisible = true
                Main.log("ToolSelection", "NPCSelection Started")
            } catch (e: Exception) {
                Main.log("ToolSelection", "Failed to start NPC selection!")
            }
        } else if (selectionBox!!.selectedIndex == 2) {
            try {
                RegionEditor.setCacheForRegionEditor(this.cache)
                RegionEditor().isVisible = true
                Main.log("ToolSelection", "RegionEditor Started")
            } catch (e: Exception) {
                Main.log("ToolSelection", "Failed to start RegionEditor!")
            }
        } else if (selectionBox!!.selectedIndex == 3) {
            InterfaceEditor(this.cache).isVisible = true
            Main.log("ToolSelection", "InterfaceEditor Started")
        } else if (selectionBox!!.selectedIndex == 4) {
            try {
                (ObjectSelection(this.cache)).isVisible = true
                Main.log("ToolSelection", "ObjectSelection Started")
            } catch (e: Exception) {
                Main.log("ToolSelection", "Failed to start Object selection!")
            }
        } else {
            Main.log("ToolSelection", "No Tool Selected!")
        }
    }

    private fun loadCacheButtonHandler(evt: ActionEvent) {
        val fc = JFileChooser()
        fc.fileSelectionMode = 1
        if (evt.source === this.loadCacheButton) {
            val returnVal = fc.showOpenDialog(this)
            if (returnVal == 0) {
                val file = fc.selectedFile
                this.cache = file.path + "/"
            }
        }
    }

    private fun exitButtonActionPerformed(evt: ActionEvent) {
        JDialog.setDefaultLookAndFeelDecorated(true)
        val response = JOptionPane.showConfirmDialog(
            null,
            "Do you want to continue?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        )
        if (response == 0) {
            exitProcess(0)
        }
    }

}
