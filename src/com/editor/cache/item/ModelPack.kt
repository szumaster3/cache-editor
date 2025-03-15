package com.editor.cache.item

import com.alex.store.Store
import console.Main
import com.editor.util.Utils
import javax.swing.*
import javax.swing.GroupLayout.*
import java.io.File
import java.io.IOException

class ModelPack(cache: String? = null) : JFrame() {

    private var store: Store? = null
    private val modelDirField: JTextField = JTextField()
    private val sameId: JCheckBox = JCheckBox("Keep Same ID").apply {
        toolTipText = "Keeps same ID as named"
    }
    private val submitButton: JButton = JButton("Submit").apply {
        addActionListener { submitActionPerformed() }
    }
    private val modelDirItem: JMenuItem = JMenuItem("Select model directory").apply {
        addActionListener { modelDirActionPerformed() }
    }
    private val exitItem: JMenuItem = JMenuItem("Exit").apply {
        addActionListener { exitActionPerformed() }
    }
    private val menuBar: JMenuBar = JMenuBar().apply {
        val fileMenu = JMenu("File").apply {
            add(modelDirItem)
            add(exitItem)
        }
        add(fileMenu)
    }

    init {
        if (cache != null) {
            try {
                store = Store(cache)
            } catch (e: Exception) {
                Main.log("ModelPack", "Cannot find cache directory")
            }
        }
        initComponents()
        setupFrame()
    }

    private fun initComponents() {
        val label1 = JLabel("Multiple Model Packer")
        val label2 = JLabel("Models Directory")
        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createParallelGroup(Alignment.LEADING)
                    .addGroup(createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label1)
                        .addContainerGap()
                    )
                    .addGroup(createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(modelDirField, GroupLayout.DEFAULT_SIZE, 150, Int.MAX_VALUE)
                        .addContainerGap()
                    )
                    .addGroup(createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sameId)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitButton)
                        .addContainerGap()
                    )
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(createParallelGroup(Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(modelDirField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    )
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(sameId)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(submitButton)
                    .addContainerGap(10, Int.MAX_VALUE)
            )
        }
    }

    private fun setupFrame() {
        defaultCloseOperation = 1
        title = "ModelPack"
        isResizable = false
        jMenuBar = menuBar
        setLocationRelativeTo(null)
        pack()
        isVisible = true
    }

    private fun submitActionPerformed() {
        val directory = modelDirField.text
        val keepID = sameId.isSelected
        val directoryFile = File(directory)
        val files = directoryFile.listFiles() ?: return

        files.filter { it.isFile }.forEach { file ->
            val fileName = file.name
            val modelId = if (keepID) fileName.replace(".dat", "") else ""
            try {
                store?.let {
                    val packedModel = Utils.packCustomModel(it, Utils.getBytesFromFile(file), modelId.toIntOrNull() ?: 0)
                    Main.log("ModelPack", "The model ID of $fileName is: $packedModel")
                } ?: run {
                    Main.log("ModelPack", "Store is not initialized.")
                }
            } catch (e: IOException) {
                Main.log("ModelPack", "There was an error packing the model.")
            }
        }
    }

    private fun modelDirActionPerformed() {
        val fileChooser = JFileChooser().apply { fileSelectionMode = JFileChooser.DIRECTORIES_ONLY }
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            modelDirField.text = fileChooser.selectedFile.absolutePath + "/"
        }
    }

    private fun exitActionPerformed() {
        dispose()
    }

}
