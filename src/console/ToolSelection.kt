package console

import com.displee.cache.CacheLibrary
import com.editor.cache.FileManager
import com.editor.cache.extract.IndicesSelection
import com.editor.cache.iface.InterfaceEditor
import com.editor.cache.item.ItemListExport
import com.editor.cache.item.ItemSelection
import com.editor.cache.npc.NPCListExport
import com.editor.cache.npc.NPCSelection
import com.editor.cache.`object`.ObjectListExport
import com.editor.cache.`object`.ObjectSelection
import com.editor.cache.transfer.IndexTransfer
import com.editor.cache.transfer.InterfaceTransfer
import com.editor.cache.transfer.RegionTransfer
import com.editor.misc.ColorPicker
import com.editor.misc.Screenshot
import com.editor.model.ModelExporter
import com.editor.model.ModelPacker
import com.editor.model.view.frame.ModelFrame
import java.awt.*
import java.awt.event.ActionEvent
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.swing.*
import kotlin.system.exitProcess

class ToolSelection : JFrame() {
    private var cache = ""
    private val loadCacheButton = JButton("Load cache")
    private val loadLastCacheButton = JButton("Last Location")
    private val selectionBox: JComboBox<String> = JComboBox()
    private val cacheFile = File("cache_location.txt")
    private val toolSelected = "ToolSelection"
    private val startMessage = "Tool started."
    private val failMessage = "Failed to start."
    private val backupMessage = JLabel().apply { font = font.deriveFont(12) }

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
        val tabbedPane = JTabbedPane()

        val panelTab = JPanel(FlowLayout(FlowLayout.LEFT))

        val selectToolButton = JLabel("Select tool")

        val button0 = JButton("Rebuild cache").apply {
            addActionListener { rebuildCache() }
            preferredSize = Dimension(100, 30)
        }

        val button1 = JButton("Backup cache").apply {
            addActionListener { backup(this) }
            preferredSize = Dimension(100, 30)
        }

        val button2 = JButton("Screenshot Capture").apply {
            addActionListener { placeholderButton3(this) }
            preferredSize = Dimension(205, 30)
        }

        val panelMiddle = JPanel(FlowLayout(FlowLayout.CENTER))
        panelMiddle.add(button0)
        panelMiddle.add(button1)

        val panelBottom = JPanel(FlowLayout(FlowLayout.CENTER))
        panelBottom.add(button2)

        panelTab.add(selectToolButton)
        panelTab.add(panelMiddle)
        panelTab.add(panelBottom)
        panelTab.add(backupMessage)

        val toolsTab = JPanel(FlowLayout())
        val submitButton = JButton("Submit")
        val buttonSize = Dimension(100, 30)
        val submitButtonSize = Dimension(90, 22)

        setupButton(loadCacheButton, buttonSize, this::loadCacheButtonHandler)
        setupButton(loadLastCacheButton, buttonSize, this::loadLastCacheButtonHandler)

        val alignmentPanel1 = JPanel(FlowLayout(FlowLayout.CENTER))
        val alignmentPanel2 = JPanel(FlowLayout())
        val alignmentPanel3 = JPanel(FlowLayout())

        alignmentPanel1.add(loadCacheButton)
        alignmentPanel1.add(Box.createHorizontalStrut(2))
        alignmentPanel1.add(loadLastCacheButton)
        alignmentPanel2.add(selectToolButton)

        selectionBox.model = DefaultComboBoxModel(
            arrayOf(
                "Item Editor",
                "NPC Editor",
                "Object Editor",
                "Interface Editor",
                "Transfer Region",
                "Transfer Interface",
                "Transfer Index",
                "Export model",
                "Export NPC list",
                "Export Item list",
                "Export Object list",
                "Export Indices",
                "Pack model",
                "Pick a Color",
                "File Manager",
                "Model Viewer",
            ),
        )

        setupButton(submitButton, submitButtonSize, this::submitButtonActionPerformed)
        alignmentPanel3.add(selectionBox)
        alignmentPanel3.add(submitButton)

        toolsTab.add(alignmentPanel1)
        toolsTab.add(alignmentPanel2)
        toolsTab.add(alignmentPanel3)

        tabbedPane.addTab("Tools", toolsTab)
        tabbedPane.addTab("Panel", panelTab)

        this.contentPane.add(tabbedPane)

        this.preferredSize = Dimension(250, 200)
        this.pack()
    }

    private fun setupButton(
        button: JButton,
        size: Dimension,
        action: (ActionEvent) -> Unit,
    ) {
        button.apply {
            preferredSize = size
            minimumSize = size
            maximumSize = size
            addActionListener(action)
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        }
    }


    private fun submitButtonActionPerformed(evt: ActionEvent) {
        val toolID = selectionBox.selectedIndex
        if (cache.isEmpty() && toolID != 10 && toolID != 6 && toolID != 13) {
            Main.log(toolSelected, "No cache Set!")
            return
        }
        when (selectionBox.selectedIndex) {
            0 -> try {
                ItemSelection(cache).isVisible = true; Main.log(toolSelected, startMessage)
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
                SwingUtilities.invokeLater { InterfaceEditor(cache).isVisible = true }; Main.log(
                    toolSelected, startMessage
                )
            } catch (e: Exception) {
                Main.log(toolSelected, failMessage)
            }

            4 -> try {
                RegionTransfer(cache).isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            5 -> {
                InterfaceTransfer(cache).isVisible = true; Main.log(toolSelected, startMessage)
            }

            6 -> try {
                IndexTransfer().isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            7 -> try {
                ModelExporter(cache).isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            8 -> try {
                NPCListExport(cache); Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            9 -> try {
                ItemListExport(cache); Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            10 -> try {
                ObjectListExport(cache); Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            11 -> try {
                IndicesSelection().isVisible = true; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            12 -> try {
                SwingUtilities.invokeLater { ModelPacker(cache).isVisible = true }; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            13 -> try {
                SwingUtilities.invokeLater { ColorPicker().isVisible = true }; Main.log(toolSelected, startMessage)
            } catch (e: IOException) {
                Main.log(toolSelected, failMessage)
            }

            14 -> try {
                val lib = CacheLibrary.create(cache); FileManager(lib).isVisible = true; Main.log(
                    toolSelected, startMessage
                )
            } catch (e: Exception) {
                Main.log(toolSelected, failMessage)
            }

            15 -> try {
                SwingUtilities.invokeLater { ModelFrame(cache).isVisible = true }; Main.log(toolSelected, startMessage)
            } catch (e: Exception) {
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
            BufferedWriter(FileWriter(cacheFile)).use { writer -> writer.write(path) }
            Main.log("ToolSelection", "cache path saved.")
        } catch (e: IOException) {
            Main.log("ToolSelection", "Failed to write cache path.")
        }
    }

    private fun loadLastCachePath(): String = try {
        BufferedReader(FileReader(cacheFile)).use { reader -> reader.readText() }
    } catch (e: IOException) {
        Main.log("ToolSelection", "Failed to load cache path.")
        ""
    }

    private fun exitButtonActionPerformed(evt: ActionEvent) {
        val response = JOptionPane.showConfirmDialog(
            null,
            "Do you want to continue?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
        )
        if (response == JOptionPane.YES_OPTION) {
            exitProcess(0)
        }
    }

    private fun rebuildCache() {
        val response = JOptionPane.showConfirmDialog(
            null,
            "Are you sure?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
        )
        if (response == JOptionPane.YES_OPTION) {
            try {
                val cache = CacheLibrary.create(cache)
                cache.rebuild(File("data/cache_rebuild/"))
                JOptionPane.showMessageDialog(
                    null,
                    "Cache rebuild completed successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                )
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(
                    null,
                    "An error occurred during the cache rebuild.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun placeholderButton2(button: JButton) {
        val currentDateTime = LocalDateTime.now()

        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val date = currentDateTime.format(dateFormatter)
        val time = currentDateTime.format(timeFormatter)

        button.text = "$date | $time"
    }

    private fun placeholderButton3(button: JButton) {
        EventQueue.invokeLater { Screenshot() }
    }

    private fun backup(button: JButton) {
        if (cache.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cache loaded.", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }
        val backupDir = File("data/backups/").apply {
            if (!exists()) {
                mkdirs()
            }
        }

        val zipFile = File(backupDir, "cache_backup_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))}.zip")

        try {
            ZipOutputStream(FileOutputStream(zipFile)).use { zos ->
                val dir = File(cache)
                zipDirectory(dir, zos, dir.name)
            }
            backupMessage.text = "                          Backup done"
            backupMessage.foreground = Color.darkGray
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(this, "Error while creating backup: ${e.message}", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    private fun zipDirectory(directory: File, zos: ZipOutputStream, parentDirectory: String) {
        if (!directory.exists()) return

        if (directory.isDirectory) {
            val dirEntry = ZipEntry("$parentDirectory/")
            zos.putNextEntry(dirEntry)
            zos.closeEntry()

            directory.listFiles()?.forEach { file ->
                zipDirectory(file, zos, "$parentDirectory/${file.name}")
            }
        } else {
            FileInputStream(directory).use { fis ->
                val entry = ZipEntry(parentDirectory)
                zos.putNextEntry(entry)
                fis.copyTo(zos)
                zos.closeEntry()
            }
        }
    }
}
