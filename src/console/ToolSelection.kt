package console

import com.editor.cache.iface.InterfaceEditor
import com.editor.cache.item.ItemListExport
import com.editor.cache.item.ItemSelection
import com.editor.cache.item.ModelDumper
import com.editor.cache.item.ModelPack
import com.editor.cache.npc.NPCListExport
import com.editor.cache.npc.NPCSelection
import com.editor.cache.`object`.ObjectSelection
import com.editor.cache.region.RegionEditor
import com.editor.model.ColorPicker
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.io.*
import javax.swing.*
import kotlin.system.exitProcess

class ToolSelection : JFrame() {
    private var cache = ""
    private val loadCacheButton = JButton("Load Cache")
    private val loadLastCacheButton = JButton("Last Location")
    private val selectionBox: JComboBox<String> = JComboBox()
    private val cacheFile = File("cache_location.txt")
    private val toolSelected = "ToolSelection"
    private val startMessage = "Tool started."
    private val failMessage = "Failed to start."

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
        initComponents()
        Main.log("Main", "ToolSelection Started")
    }

    private fun initComponents() {
        val alignmentPanel1 = JPanel(FlowLayout(FlowLayout.CENTER))
        val alignmentPanel2 = JPanel(FlowLayout())
        val alignmentPanel3 = JPanel(FlowLayout())

        this.preferredSize = Dimension(250, 200)
        val selectYourEditorLabel = JLabel("Select your editor: ")
        val submitButton = JButton("Submit")
        val jMenuBar1 = JMenuBar()
        val jMenu1 = JMenu("File")
        val exitButton = JMenuItem("Exit Program")

        val buttonSize = Dimension(100, 30)
        setupButton(loadCacheButton, buttonSize, this::loadCacheButtonHandler)
        setupButton(loadLastCacheButton, buttonSize, this::loadLastCacheButtonHandler)

        alignmentPanel1.add(loadCacheButton)
        alignmentPanel1.add(Box.createHorizontalStrut(2))
        alignmentPanel1.add(loadLastCacheButton)
        alignmentPanel2.add(selectYourEditorLabel)

        selectionBox.model = DefaultComboBoxModel(
            arrayOf(
                "Item Editor",
                "NPC Editor",
                "Object Editor",
                "Transfer Region",
                "Transfer Interface",
                "Export model",
                "Export NPC list",
                "Export Item list",
                "Pack model",
                "Pick a Color"
            )
        )

        submitButton.addActionListener { evt -> submitButtonActionPerformed(evt) }
        alignmentPanel3.add(selectionBox)
        alignmentPanel3.add(submitButton)

        exitButton.addActionListener { evt -> exitButtonActionPerformed(evt) }
        jMenu1.add(exitButton)
        jMenuBar1.add(jMenu1)
        this.jMenuBar = jMenuBar1

        this.contentPane.layout = GridLayout(3, 1, 5, 10)
        this.add(alignmentPanel1)
        this.add(alignmentPanel2)
        this.add(alignmentPanel3)
        this.revalidate()
        this.repaint()
        this.pack()
    }

    private fun setupButton(button: JButton, size: Dimension, action: (ActionEvent) -> Unit) {
        button.apply {
            preferredSize = size
            minimumSize = size
            maximumSize = size
            addActionListener(action)
        }
    }

    private fun submitButtonActionPerformed(evt: ActionEvent) {
        if (cache.isEmpty() && selectionBox.selectedIndex != 9) {
            Main.log(toolSelected, "No Cache Set!")
            return
        }
        when (selectionBox.selectedIndex) {
            0 -> try {
                ItemSelection(cache).isVisible = true;
                Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            1 -> try {
                NPCSelection(cache).isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            2 -> try {
                ObjectSelection(cache).isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }
            3 -> try {
                RegionEditor(cache).isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }
            4 -> {
                InterfaceEditor(cache).isVisible = true; Main.log(toolSelected, startMessage)
            }

            5 -> try {
                ModelDumper(cache).isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            6 -> try {
                NPCListExport(cache); Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            7 -> try {
                ItemListExport(cache); Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            8 -> try {
                SwingUtilities.invokeLater { ModelPack(cache).isVisible = true }; Main.log(
                    toolSelected, startMessage
                )
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            9 -> try {
                SwingUtilities.invokeLater { ColorPicker().isVisible = true }; Main.log(
                    toolSelected, startMessage
                )
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            else -> Main.log(toolSelected, "No Tool Selected!")
        }
    }

    private fun loadCacheButtonHandler(evt: ActionEvent) {
        val fc = JFileChooser().apply { fileSelectionMode = JFileChooser.DIRECTORIES_ONLY }
        if (evt.source === loadCacheButton) {
            val returnVal = fc.showOpenDialog(this)
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                val file = fc.selectedFile
                this.cache = "${file.path}/"
                saveLastCachePath(this.cache)
            }
        }
    }

    private fun loadLastCacheButtonHandler(evt: ActionEvent) {
        if (cacheFile.exists()) {
            val lastCachePath = loadLastCachePath()
            if (lastCachePath.isNotEmpty()) {
                this.cache = lastCachePath
                Main.log("ToolSelection", "Last location: $cache")
            } else {
                JOptionPane.showMessageDialog(this, "No previous cache found.")
            }
        } else {
            JOptionPane.showMessageDialog(this, "No previous cache found.")
        }
    }

    private fun saveLastCachePath(path: String) {
        try {
            BufferedWriter(FileWriter(cacheFile)).use { writer ->
                writer.write(path)
            }
            Main.log("ToolSelection", "Cache path saved.")
        } catch (e: IOException) {
            Main.log("ToolSelection", "Failed to save cache path.")
        }
    }

    private fun loadLastCachePath(): String {
        return try {
            BufferedReader(FileReader(cacheFile)).use { reader -> reader.readText() }
        } catch (e: IOException) {
            Main.log("ToolSelection", "Failed to load cache path.")
            ""
        }
    }

    private fun exitButtonActionPerformed(evt: ActionEvent) {
        val response = JOptionPane.showConfirmDialog(
            null, "Do you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        )
        if (response == JOptionPane.YES_OPTION) {
            exitProcess(0)
        }
    }
}
