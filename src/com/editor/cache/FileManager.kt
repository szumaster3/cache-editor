package com.editor.cache

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.displee.cache.index.archive.Archive
import core.cache.CacheIndex
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.io.File
import java.nio.file.Files
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreePath
import javax.swing.tree.TreeSelectionModel

class FileManager(
    private val library: CacheLibrary,
) : JFrame() {

    private var selectedIndex: Int? = null
    private var selectedArchive: Int? = null
    private var selectedFile: Int? = null

    private var currentIndex: Index? = null
    private var currentArchive: Archive? = null
    private var currentFile: ByteArray? = null

    private val rootNode = DefaultMutableTreeNode("Root")
    private val treeModel = DefaultTreeModel(rootNode)
    private val tree = JTree(treeModel)

    private val infoLabel = JLabel("<html>Name: <br>Archives: <br>Revision: <br>CRC:<br>Compression:</html>")
    private val actionResultPanel = JPanel(BorderLayout())

    init {
        title = "File Manager"
        layout = BorderLayout()

        val mainPanel = JPanel(BorderLayout())
        val treePanel = createTreePanel()

        val actionPanel = JPanel(GridLayout(3, 3, 5, 5))

        val centerPanel = JPanel(BorderLayout())
        centerPanel.add(treePanel, BorderLayout.NORTH)
        centerPanel.add(informationPanel(), BorderLayout.CENTER)

        mainPanel.add(centerPanel, BorderLayout.CENTER)
        mainPanel.add(actionPanel, BorderLayout.SOUTH)

        actionPanel.apply {
            border = BorderFactory.createEmptyBorder(10, 0, 10, 0)
            add(createAddArchiveButton())
            add(createRemoveArchiveButton())
            add(createAddFileButton())
            add(createRemoveFileButton())
            add(createEditFileButton())
            add(createExportIndexButton())
            add(createRenameArchiveButton())
        }

        add(mainPanel)
        setSize(320, 600)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun createTreePanel(): JScrollPane {
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        populateTree(rootNode)
        tree.model = DefaultTreeModel(rootNode)
        tree.expandRow(0)

        tree.addTreeSelectionListener { event ->
            val selectedNode = event.path.lastPathComponent as DefaultMutableTreeNode
            val nodeData = selectedNode.userObject.toString()

            when {
                nodeData.startsWith("Index") -> {
                    selectedIndex = nodeData.substringAfter("Index ").toInt()
                    selectedArchive = null
                    selectedFile = null
                    currentIndex = library.index(selectedIndex!!)
                    currentArchive = null
                    currentFile = null
                }

                nodeData.startsWith("Archive") -> {
                    selectedArchive = nodeData.substringAfter("Archive ").toInt()
                    selectedFile = null
                    currentIndex?.let { currentArchive = it.archive(selectedArchive!!) }
                    currentFile = null
                }

                nodeData.startsWith("File") -> {
                    selectedFile = nodeData.substringAfter("File ").toInt()
                    currentFile = currentArchive?.file(selectedFile!!)?.data
                }
                else -> {
                    selectedIndex = null
                    selectedArchive = null
                    selectedFile = null
                    currentIndex = null
                    currentArchive = null
                    currentFile = null
                }
            }

            detailsPanel()
            updateActionInfoLabel("Selected: $nodeData")
        }

        return JScrollPane(tree)
    }

    private fun updateActionInfoLabel(text: String) {
        actionResultPanel.removeAll()
        val label = createActionResultLabel()
        label.text = text
        actionResultPanel.add(label, BorderLayout.PAGE_END)
        actionResultPanel.revalidate()
        actionResultPanel.repaint()
    }

    private fun createActionResultLabel(): JLabel {
        val label = JLabel("No action performed.")
        label.alignmentX = JLabel.CENTER_ALIGNMENT
        label.alignmentY = JLabel.CENTER_ALIGNMENT

        label.border = BorderFactory.createLineBorder(java.awt.Color.BLACK, 1)
        label.background = java.awt.Color.BLACK
        label.foreground = java.awt.Color.WHITE
        label.isOpaque = true
        return label
    }

    private fun informationPanel(): JPanel {
        val panel = JPanel(BorderLayout())
        panel.border = BorderFactory.createEmptyBorder(5, 7, 5, 0)
        panel.add(infoLabel, BorderLayout.CENTER)
        panel.add(actionResultPanel, BorderLayout.SOUTH)
        return panel
    }

    private fun populateTree(rootNode: DefaultMutableTreeNode) {
        rootNode.removeAllChildren()
        val indices = library.indices()

        indices.forEach { index ->
            val indexNode = DefaultMutableTreeNode("Index ${index.id}")
            rootNode.add(indexNode)

            val archives = index.archives()
            archives.forEach { archive ->
                val archiveNode = DefaultMutableTreeNode("Archive ${archive.id}")
                indexNode.add(archiveNode)

                archive.files.forEach { (fileId, _) ->
                    val fileNode = DefaultMutableTreeNode("File $fileId")
                    archiveNode.add(fileNode)
                }
            }
        }
        (tree.model as DefaultTreeModel).reload()
    }

    private fun detailsPanel() {
        val cacheIndexName = CacheIndex.values().find { it.id == selectedIndex }
        val indexName =
            cacheIndexName?.name?.lowercase()?.split('_')?.joinToString(" ") {
                it.replaceFirstChar { char -> char.uppercase() }
            } ?: "None"
        val archivesCount = currentIndex?.archives()?.size ?: "None"
        val revision = currentIndex?.revision ?: "None"
        val crc = currentIndex?.crc ?: "None"
        val compression = currentIndex?.compressionType ?: "None"

        infoLabel.text =
            "<html>Name: $indexName <br>Archives: $archivesCount <br>Revision: $revision <br>CRC: $crc<br>Compression: $compression</html>"
    }

    private fun createAddArchiveButton(): JButton =
        JButton("Add archive").apply {
            addActionListener { addArchive() }
            size = Dimension(100, 25)
        }

    private fun createRemoveArchiveButton(): JButton =
        JButton("Remove archive").apply {
            addActionListener { removeArchive() }
            size = Dimension(100, 25)
        }

    private fun createAddFileButton(): JButton =
        JButton("Add file").apply {
            addActionListener { addFile() }
            size = Dimension(100, 25)
        }

    private fun createRemoveFileButton(): JButton =
        JButton("Remove file").apply {
            addActionListener { removeFile() }
            size = Dimension(100, 25)
        }

    private fun createEditFileButton(): JButton =
        JButton("Edit").apply {
            addActionListener { editFile() }
            size = Dimension(100, 25)
        }

    private fun createExportIndexButton(): JButton =
        JButton("Export index").apply {
            addActionListener { exportIndex() }
            size = Dimension(100, 25)
        }

    private fun createRenameArchiveButton(): JButton =
        JButton("Rename archive").apply {
            addActionListener { renameArchive() }
            size = Dimension(120, 25)
        }

    private fun findNode(
        parent: DefaultMutableTreeNode,
        nodeName: String
    ): DefaultMutableTreeNode? {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i) as DefaultMutableTreeNode
            if (child.userObject == nodeName) {
                return child
            }
        }
        return null
    }

    private fun refreshTree() {
        val expandedPaths = getExpandedPaths()
        val selectedPath = tree.selectionPath

        if (selectedIndex != null) {
            val indexNode = findNode(rootNode, "Index ${selectedIndex!!}")
            if (indexNode != null) {
                updateIndexNode(indexNode)
            }
        }

        restoreExpandedPaths(expandedPaths)

        if (selectedPath != null) {
            tree.selectionPath = selectedPath
        }

        tree.revalidate()
        tree.repaint()
    }

    private fun updateIndexNode(indexNode: DefaultMutableTreeNode) {
        indexNode.removeAllChildren()

        val index = library.index(selectedIndex!!)
        val archives = index.archives()
        archives.forEach { archive ->
            val archiveNode = DefaultMutableTreeNode("Archive ${archive.id}")
            indexNode.add(archiveNode)

            archive.files.forEach { (fileId, _) ->
                val fileNode = DefaultMutableTreeNode("File $fileId")
                archiveNode.add(fileNode)
            }
        }

        (tree.model as DefaultTreeModel).nodeStructureChanged(indexNode)
    }

    private fun getExpandedPaths(): List<TreePath> {
        val expandedPaths = mutableListOf<TreePath>()
        val numRows = tree.rowCount
        for (i in 0 until numRows) {
            val path = tree.getPathForRow(i)
            if (tree.isExpanded(path)) {
                expandedPaths.add(path)
            }
        }
        return expandedPaths
    }

    private fun restoreExpandedPaths(expandedPaths: List<TreePath>) {
        expandedPaths.forEach { path -> tree.expandPath(path) }
    }

    private fun editFile() {
        selectedIndex?.let { indexId ->
            selectedArchive?.let { archiveId ->
                selectedFile?.let { fileId ->
                    val archive = library.index(indexId).archive(archiveId)
                    val fileData = archive?.file(fileId)

                    fileData?.let {
                        val hexEditorPanel = it.data?.let { it1 -> createHexEditorPanel(it1) }
                        val hexEditorFrame = JFrame("Hex Editor")
                        hexEditorFrame.layout = BorderLayout()
                        hexEditorFrame.add(hexEditorPanel, BorderLayout.CENTER)
                        hexEditorFrame.setSize(666, 666)
                        hexEditorFrame.isVisible = true
                    }
                }
            }
        }
    }

    private fun createHexEditorPanel(fileData: ByteArray): JScrollPane {
        val hexEditorArea = JTextArea()
        hexEditorArea.isEditable = true
        hexEditorArea.text = byteArrayToHex(fileData)

        val scrollPane = JScrollPane(hexEditorArea)

        return scrollPane
    }

    private fun byteArrayToHex(byteArray: ByteArray): String = buildString {
        for (byte in byteArray) {
            append(String.format("%02X ", byte))
        }
    }

    private fun addArchive() {
        selectedIndex?.let {
            val newArchive = library.index(it).add()
            if (newArchive != null) {
                updateActionInfoLabel("Added archive ${newArchive.id} to index $it.")
                refreshTree()
            }
        }
    }

    private fun removeArchive() {
        if (selectedIndex == null || selectedArchive == null) {
            updateActionInfoLabel("Select an archive to remove.")
            return
        }
        val index = library.index(selectedIndex!!)
        val archive = index.archive(selectedArchive!!)
        archive?.let {
            index.remove(selectedArchive!!)
            val updated = index.update()
            updateActionInfoLabel(if (updated) "Removed archive $selectedArchive." else "Failed to remove archive.")
            refreshTree()
        }
    }

    private fun addFile() {
        if (selectedIndex == null || selectedArchive == null) {
            updateActionInfoLabel("Select an archive to add file to.")
            return
        }
        val fc = JFileChooser()
        val ret = fc.showOpenDialog(this)
        if (ret == JFileChooser.APPROVE_OPTION) {
            val file = fc.selectedFile
            val data = Files.readAllBytes(file.toPath())
            val archive = library.index(selectedIndex!!).archive(selectedArchive!!)
            if (archive != null) {
                archive.add(data)
                val updated = library.index(selectedIndex!!).update()
                updateActionInfoLabel(if (updated) "Added file ${file.name} to archive $selectedArchive." else "Failed to add file.")
                refreshTree()
            }
        }
    }

    private fun removeFile() {
        if (selectedIndex == null || selectedArchive == null || selectedFile == null) {
            updateActionInfoLabel("Select a file to remove.")
            return
        }
        library.remove(selectedIndex!!, selectedArchive!!, selectedFile!!)
        library.index(selectedIndex!!).update()
        updateActionInfoLabel("Removed file $selectedFile from archive $selectedArchive.")
        refreshTree()
    }

    private fun exportIndex() {
        if (selectedIndex == null) {
            updateActionInfoLabel("Select an index to export.")
            return
        }
        val index = library.index(selectedIndex!!)
        val fc = JFileChooser()
        fc.selectedFile = java.io.File("index_${selectedIndex}.dat")
        val ret = fc.showSaveDialog(this)
        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                val file = fc.selectedFile
                val data = index.write()
                file.writeBytes(data)
                updateActionInfoLabel("Exported index $selectedIndex to ${file.absolutePath}")
            } catch (ex: Exception) {
                updateActionInfoLabel("Failed to export index: ${ex.message}")
            }
        }
    }

    private fun renameArchive() {
        if (selectedIndex == null || selectedArchive == null) {
            updateActionInfoLabel("Select an archive to rename.")
            return
        }
        val newIdStr = JOptionPane.showInputDialog(this, "Enter new archive ID:")
        val newId = newIdStr?.toIntOrNull()
        if (newId == null) {
            updateActionInfoLabel("Invalid archive ID.")
            return
        }

        val index = library.index(selectedIndex!!)
        val archive = index.archive(selectedArchive!!)
        if (archive == null) {
            updateActionInfoLabel("Archive not found.")
            return
        }

        val newArchive = index.add(newId)
        archive.files.forEach { (fileId, file) ->
            val data = file.data
            if (data != null) {
                newArchive.add(fileId, data)
            } else {
                println("Warning: File $fileId data is null, skipping")
            }
        }
        index.remove(selectedArchive!!)
        val updated = index.update()
        if (updated) {
            selectedArchive = newId
            updateActionInfoLabel("Renamed archive to $newId")
            refreshTree()
        } else {
            updateActionInfoLabel("Failed to rename archive")
        }
    }
}
