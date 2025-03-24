package com.editor.cache

import com.displee.cache.CacheLibrary
import com.displee.cache.index.Index
import com.displee.cache.index.archive.Archive
import core.cache.CacheIndex
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.*
import javax.swing.tree.*

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

    private val infoLabel = JLabel("<html>Name: <br>Archives: <br>Revision: <br>CRC:</html>")
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
        }

        add(mainPanel)
        setSize(280, 550)
        defaultCloseOperation = 1
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun createTreePanel(): JScrollPane {
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION
        populateTree(rootNode)
        val treeModel = DefaultTreeModel(rootNode)
        tree.model = treeModel
        tree.expandRow(0)

        tree.addTreeSelectionListener { event ->
            val selectedNode = event.path.lastPathComponent as DefaultMutableTreeNode
            val nodeData = selectedNode.userObject.toString()

            when {
                nodeData.startsWith("Index") -> {
                    selectedIndex = nodeData.substringAfter("Index ").toInt()
                    currentIndex = library.index(selectedIndex!!)
                }
                nodeData.startsWith("Archive") -> {
                    selectedArchive = nodeData.substringAfter("Archive ").toInt()
                    currentIndex?.let { currentArchive = it.archive(selectedArchive!!) }
                }
                nodeData.startsWith("File") -> {
                    selectedFile = nodeData.substringAfter("File ").toInt()
                    currentFile = currentArchive?.file(selectedFile!!)?.data
                }
            }

            detailsPanel()
            updateActionInfoLabel()
        }

        return JScrollPane(tree)
    }

    private fun updateActionInfoLabel() {
        actionResultPanel.removeAll()
        actionResultPanel.add(createActionResultLabel(), BorderLayout.PAGE_END)
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
        return panel
    }

    private fun populateTree(rootNode: DefaultMutableTreeNode) {
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

        val selectedDetails =
            when {
                selectedArchive != null -> {
                    val filesCount = currentArchive?.files?.size ?: "Unknown"
                    "Archive $selectedArchive"
                }
                selectedIndex != null -> {
                    "Index $indexName"
                }
                else -> "None"
            }

        infoLabel.text =
            "<html>Name: $indexName <br>Archives: $archivesCount <br>Revision: $revision <br>CRC: $crc</html>"
    }

    private fun createAddArchiveButton(): JButton =
        JButton("Add archive").apply {
            addActionListener { addArchive() }
            size = Dimension(40, 25)
        }

    private fun createRemoveArchiveButton(): JButton =
        JButton("Remove archive").apply {
            addActionListener { removeArchive() }
            size = Dimension(40, 25)
        }

    private fun createAddFileButton(): JButton =
        JButton("Add file").apply {
            addActionListener { addFile() }
            size = Dimension(40, 25)
        }

    private fun createRemoveFileButton(): JButton =
        JButton("Remove file").apply {
            addActionListener { removeFile() }
            size = Dimension(40, 25)
        }

    private fun createEditFileButton(): JButton =
        JButton("Edit").apply {
            addActionListener { editFile() }
            size = Dimension(40, 25)
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
        }
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

        (tree.model as DefaultTreeModel).nodeChanged(indexNode)
    }

    private fun removeArchive() {
        val index = library.index(selectedIndex!!)
        val archive = index.archive(selectedArchive!!)
        archive?.let {
            index.remove(selectedArchive!!)
            index.update()

            val indexNode = findNode(rootNode, "Index ${selectedIndex!!}")
            val archiveNode = findNode(indexNode!!, "Archive ${selectedArchive!!}")
            archiveNode?.let { (tree.model as DefaultTreeModel).removeNodeFromParent(archiveNode) }
        }
        library.reload()
        refreshTree()
        updateActionInfoLabel()
    }

    private fun addFile() {}

    private fun removeFile() {
        val index = library.index(selectedIndex!!)

        library.remove(selectedIndex!!, selectedArchive!!, selectedFile!!)
        index.update()

        val archiveNode = findNode(rootNode, "Archive ${selectedArchive!!}")
        val fileNode = findNode(archiveNode!!, "File ${selectedFile!!}")
        fileNode?.let { (tree.model as DefaultTreeModel).removeNodeFromParent(fileNode) }

        refreshTree()
        library.reload()
        updateActionInfoLabel()
    }

    private fun updateIndex() {
        selectedIndex?.let { indexId ->
            val index = library.index(indexId)
            if (index.update()) {} else {}
        }
    }

    private fun saveFile() {}
}
