package console

import com.editor.iface.InterfaceEditor
import com.editor.item.ItemSelection
import com.editor.item.ModelDumper
import com.editor.npc.NPCSelection
import com.editor.`object`.ObjectSelection
import com.editor.region.RegionEditor
import java.awt.*
import java.awt.event.ActionEvent
import java.io.IOException
import javax.swing.*
import kotlin.system.exitProcess

class ToolSelection : JFrame() {
    private var cache = ""
    private var loadCacheButton: JButton? = null
    private var selectionBox: JComboBox<String>? = null

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
        val submitButton = JButton("Submit")
        val jMenuBar1 = JMenuBar()
        val jMenu1 = JMenu("File")
        this.loadCacheButton = JButton("Load Cache")
        val exitButton = JMenuItem("Exit Program")
        this.defaultCloseOperation = EXIT_ON_CLOSE

        loadCacheButton!!.preferredSize = Dimension(125, 30)
        loadCacheButton!!.addActionListener { e -> loadCacheButtonHandler(e) }
        alignmentPanel1.add(loadCacheButton, BorderLayout.CENTER)

        alignmentPanel2.add(selectYourEditorLabel)

        selectionBox!!.model = DefaultComboBoxModel(
            arrayOf(
                "Item Editor",
                "NPC Editor",
                "Transfer Region",
                "Transfer Interface",
                "Object Editor",
                "Model Export",
                //"Animation Editor"
            )
        )
        submitButton.addActionListener { evt -> submitButtonActionPerformed(evt) }
        alignmentPanel3.add(selectionBox)
        alignmentPanel3.add(submitButton)

        exitButton.addActionListener { evt -> exitButtonActionPerformed(evt) }
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
            Main.log("ToolSelection", "No Cache Set!")
        } else {
            when (selectionBox!!.selectedIndex) {
                0 -> try {
                    ItemSelection(cache).isVisible = true
                    Main.log("ToolSelection", "ItemSelection Started")
                } catch (e: IOException) {
                    Main.log("ToolSelection", "Failed to start ItemSelection!")
                }
                1 -> try {
                    NPCSelection(cache).isVisible = true
                    Main.log("ToolSelection", "NPCSelection Started")
                } catch (e: Exception) {
                    Main.log("ToolSelection", "Failed to start NPC selection!")
                }
                2 -> try {
                    RegionEditor(cache).isVisible = true
                    Main.log("ToolSelection", "RegionEditor Started")
                } catch (e: Exception) {
                    Main.log("ToolSelection", "Failed to start RegionEditor!")
                }
                3 -> InterfaceEditor(cache).isVisible = true.also {
                    Main.log("ToolSelection", "InterfaceEditor Started")
                }
                4 -> try {
                    ObjectSelection(cache).isVisible = true
                    Main.log("ToolSelection", "ObjectSelection Started")
                } catch (e: Exception) {
                    Main.log("ToolSelection", "Failed to start Object selection!")
                }
                5 -> try {
                    ModelDumper(cache).isVisible = true
                    Main.log("ToolSelection", "ModelDumper Started")
                } catch (e: Exception) {
                    Main.log("ToolSelection", "Failed to start ModelDumper!")
                }
                //6 -> try {
                //    AnimationSelection(cache).isVisible = true
                //    Main.log("ToolSelection", "AnimationSelection Started")
                //} catch (e: Exception) {
                //    Main.log("ToolSelection", "Failed to start AnimationSelection!")
                //}
                else -> Main.log("ToolSelection", "No Tool Selected!")
            }
        }
    }

    private fun loadCacheButtonHandler(evt: ActionEvent) {
        val fc = JFileChooser()
        fc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        if (evt.source === loadCacheButton) {
            val returnVal = fc.showOpenDialog(this)
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                val file = fc.selectedFile
                this.cache = file.path + "/"
            }
        }
    }

    private fun exitButtonActionPerformed(evt: ActionEvent) {
        val response = JOptionPane.showConfirmDialog(
            null, "Do you want to continue?", "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        )
        if (response == JOptionPane.YES_OPTION) {
            exitProcess(0)
        }
    }
}