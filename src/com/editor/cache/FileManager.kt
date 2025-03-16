package com.editor.cache

import com.displee.cache.CacheLibrary
import java.awt.*
import javax.swing.*
import javax.swing.text.DefaultCaret
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeSelectionModel

class FileManager(private val library: CacheLibrary) : JFrame() {
    private var selectedIndex: Int? = null
    private var selectedArchive: Int? = null
    private var selectedFile: Int? = null

    init {
        title = "File Manager"
        layout = BorderLayout()

        val mainPanel = JPanel(BorderLayout())
        val treePanel = createTreePanel()
        val gbc = GridBagConstraints().apply { insets = Insets(5, 5, 5, 5); fill = GridBagConstraints.HORIZONTAL }

        val actionPanel = JPanel(GridLayout(4, 5, 10, 10))

        actionPanel.apply {
            add(createAddArchiveButton())
            add(createRemoveArchiveButton())
            add(createAddFileButton())
            add(createRemoveFileButton())
            add(createUpdateIndexButton())
            add(createEditFileButton())
            add(createSaveFileButton())
        }

        mainPanel.add(treePanel, BorderLayout.CENTER)
        mainPanel.add(actionPanel, BorderLayout.SOUTH)

        add(mainPanel)
        setSize(280, 490)
        defaultCloseOperation = 1
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun createTreePanel(): JScrollPane {
        val rootNode = DefaultMutableTreeNode("Root")
        val tree = JTree(rootNode)
        tree.selectionModel.selectionMode = TreeSelectionModel.SINGLE_TREE_SELECTION

        populateTree(rootNode)

        val treeModel = DefaultTreeModel(rootNode)
        tree.model = treeModel
        tree.expandRow(0)

        tree.addTreeSelectionListener { event ->
            val selectedNode = event.path.lastPathComponent as DefaultMutableTreeNode
            val nodeData = selectedNode.userObject.toString()

            if (nodeData.startsWith("Index")) {
                selectedIndex = nodeData.substringAfter("Index ").toInt()
            } else if (nodeData.startsWith("Archive")) {
                selectedArchive = nodeData.substringAfter("Archive ").toInt()
            } else if (nodeData.startsWith("File")) {
                selectedFile = nodeData.substringAfter("File ").toInt()
                // editFile()
            }
        }

        return JScrollPane(tree)
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
    }

    private fun createAddArchiveButton(): JButton {
        return JButton("Add archive").apply {
            addActionListener { addArchive() }
            size = Dimension(40, 25)
        }
    }

    private fun createRemoveArchiveButton(): JButton {
        return JButton("Remove archive").apply {
            addActionListener { removeArchive() }
            size = Dimension(40, 25)
        }
    }

    private fun createAddFileButton(): JButton {
        return JButton("Add file to archive").apply {
            addActionListener { addFileToArchive() }
            size = Dimension(40, 25)
        }
    }

    private fun createRemoveFileButton(): JButton {
        return JButton("Remove file from archive").apply {
            addActionListener { removeFileFromArchive() }
            size = Dimension(40, 25)
        }
    }

    private fun createUpdateIndexButton(): JButton {
        return JButton("Update Index").apply {
            addActionListener { updateIndex() }
            size = Dimension(40, 25)
        }
    }

    private fun createEditFileButton(): JButton {
        return JButton("Edit file").apply {
            addActionListener { editFile() }
            size = Dimension(40, 25)
        }
    }

    private fun createSaveFileButton(): JButton {
        return JButton("Save file").apply {
            addActionListener { saveFile() }
            size = Dimension(40, 25)
        }
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

        val caret = DefaultCaret()
        caret.updatePolicy = DefaultCaret.ALWAYS_UPDATE
        hexEditorArea.caret = caret

        return scrollPane
    }

    private fun byteArrayToHex(byteArray: ByteArray): String {
        return buildString {
            for (byte in byteArray) {
                append(String.format("%02X ", byte))
            }
        }
    }

    private fun addArchive() {
        selectedIndex?.let {
            val newArchive = library.index(it).add()
        }
    }

    private fun removeArchive() {
        selectedIndex?.let { indexId ->
            selectedArchive?.let { archiveId ->
                val archiveToRemove = library.index(indexId).archive(archiveId)
                archiveToRemove?.let {
                    library.index(indexId).remove(archiveToRemove.id)
                }
            }
        }
    }

    private fun addFileToArchive() {
        selectedIndex?.let { indexId ->
            selectedArchive?.let { archiveId ->
                val archive = library.index(indexId).archive(archiveId)
                archive?.let {
                    val fileData = byteArrayOf()
                    archive.add(fileData)
                }
            }
        }
    }

    private fun removeFileFromArchive() {
        selectedIndex?.let { indexId ->
            selectedArchive?.let { archiveId ->
                val archive = library.index(indexId).archive(archiveId)
                archive?.let {
                    if (archive.files.isNotEmpty()) {
                        val fileToRemove = archive.files.firstKey()
                        archive.remove(fileToRemove)
                    }
                }
            }
        }
    }

    private fun updateIndex() {
        selectedIndex?.let { indexId ->
            val index = library.index(indexId)
            if (index.update()) {
                JOptionPane.showMessageDialog(this, "Index updated.")
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update index.")
            }
        }
    }

    private fun saveFile() {
        JOptionPane.showMessageDialog(this, "WIP.")
    }

}
