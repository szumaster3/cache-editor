package com.editor.region

import com.displee.cache.CacheLibrary
import java.awt.*
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.swing.*

class RegionEditor : JFrame() {
    private val regionID: JTextField
    private val symmetricKeyField: JTextField
    private val sourceCache: JTextField
    private val regionOutput: JTextField
    private val regionInput: JTextField
    private val editButton: JButton
    private val selectSourceCache: JButton
    private val backupButton: JButton

    init {
        title = "Region Editor"
        layout = GridBagLayout()
        setSize(400, 500)
        defaultCloseOperation = 1
        setLocationRelativeTo(null)

        val gbc = GridBagConstraints()
        gbc.insets = Insets(10, 10, 10, 10)
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.anchor = GridBagConstraints.WEST

        // Region ID
        val regionIdLabel = JLabel("Region ID:")
        gbc.gridx = 0
        gbc.gridy = 0
        add(regionIdLabel, gbc)

        regionID = JTextField(10)
        gbc.gridx = 1
        gbc.gridy = 0
        add(regionID, gbc)

        // Symmetric Key
        val symmetricKeyLabel = JLabel("Symmetric Key:")
        gbc.gridx = 0
        gbc.gridy = 1
        add(symmetricKeyLabel, gbc)

        symmetricKeyField = JTextField(20)
        gbc.gridx = 1
        gbc.gridy = 1
        add(symmetricKeyField, gbc)

        // Region Output
        val regionOutputLabel = JLabel("Region Output:")
        gbc.gridx = 0
        gbc.gridy = 2
        add(regionOutputLabel, gbc)

        regionOutput = JTextField(20)
        regionOutput.isEditable = false
        gbc.gridx = 1
        gbc.gridy = 2
        add(regionOutput, gbc)

        // Region Input
        val regionInputLabel = JLabel("Enter Region (lX_Y):")
        gbc.gridx = 0
        gbc.gridy = 3
        add(regionInputLabel, gbc)

        regionInput = JTextField(10)
        gbc.gridx = 1
        gbc.gridy = 3
        add(regionInput, gbc)

        // Calculate Region Button
        val calculateRegionButton = JButton("Calculate Region")
        gbc.gridx = 1
        gbc.gridy = 4
        add(calculateRegionButton, gbc)

        // Calculate Region ID Button
        val calculateRegionIdButton = JButton("Calculate Region ID")
        gbc.gridx = 1
        gbc.gridy = 5
        add(calculateRegionIdButton, gbc)

        // Source Cache Path
        val cacheFromLabel = JLabel("Cache From Path:")
        gbc.gridx = 0
        gbc.gridy = 6
        add(cacheFromLabel, gbc)

        sourceCache = JTextField(20)
        sourceCache.isEditable = false
        gbc.gridx = 1
        gbc.gridy = 6
        add(sourceCache, gbc)

        // Select Source Cache Button
        selectSourceCache = JButton("Select Source Cache")
        selectSourceCache.addActionListener {
            val fileChooser = JFileChooser()
            fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            val result = fileChooser.showOpenDialog(this)
            if (result == JFileChooser.APPROVE_OPTION) {
                val selectedDirectory = fileChooser.selectedFile
                sourceCache.text = selectedDirectory.absolutePath
            }
        }
        gbc.gridx = 1
        gbc.gridy = 7
        add(selectSourceCache, gbc)

        // Transfer Region Button
        editButton = JButton("Transfer Region")
        editButton.addActionListener {
            val regionIdText = regionID.text.trim()
            val xteaInput = symmetricKeyField.text.trim()
            try {
                val regionId = regionIdText.toInt()
                editRegion(regionId, xteaInput)
            } catch (ex: NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Invalid Region ID!", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
        gbc.gridx = 1
        gbc.gridy = 8
        add(editButton, gbc)

        // Backup Cache Button
        backupButton = JButton("Backup Cache")
        backupButton.addActionListener { backupCache() }
        gbc.gridx = 1
        gbc.gridy = 9
        add(backupButton, gbc)

        // Set window visibility
        isVisible = true
    }

    private fun backupCache() {
        val cacheDirectory = File(cacheToPath)
        if (!cacheDirectory.exists() || !cacheDirectory.isDirectory) {
            JOptionPane.showMessageDialog(this, "Invalid source cache path.", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }

        val zipFilePath = "$cacheToPath/cache_backup.zip"
        try {
            FileOutputStream(zipFilePath).use { fos ->
                ZipOutputStream(fos).use { zos ->
                    zipDirectory(cacheDirectory, cacheDirectory.name, zos)
                    JOptionPane.showMessageDialog(
                        this, "Backup created at: $zipFilePath", "Success", JOptionPane.INFORMATION_MESSAGE
                    )
                }
            }
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(this, "Error while backing up the cache.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    @Throws(IOException::class)
    private fun zipDirectory(folder: File, parentFolderName: String, zos: ZipOutputStream) {
        for (file in folder.listFiles()) {
            if (file.isDirectory) {
                zipDirectory(file, "$parentFolderName/${file.name}", zos)
            } else {
                zos.putNextEntry(ZipEntry("$parentFolderName/${file.name}"))
                FileInputStream(file).use { fis ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (fis.read(buffer).also { length = it } >= 0) {
                        zos.write(buffer, 0, length)
                    }
                }
                zos.closeEntry()
            }
        }
    }

    fun generateMapIndex() {
        if (cacheToPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please load the source cache!", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }

        val cacheFrom = CacheLibrary.create(cacheToPath)
        val index = cacheFrom.index(5)

        val mapIndexData = StringBuilder()
        for (regionX in 0..255) {
            for (regionY in 0..255) {
                val landscapeId = MapIndexLoader.getLandscapeId(regionX, regionY)
                val objectId = MapIndexLoader.getObjectId(regionX, regionY)

                mapIndexData.append("Region (l$regionX|$regionY) Landscape ID: $landscapeId, Object ID: $objectId\n")
            }
        }

        try {
            BufferedWriter(FileWriter("map_index.txt")).use { writer ->
                writer.write(mapIndexData.toString())
                JOptionPane.showMessageDialog(
                    this, "Map index saved to map_index.txt.", "Success", JOptionPane.INFORMATION_MESSAGE
                )
            }
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(this, "Error saving map_index.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    companion object {
        private var cacheToPath = ""

        fun setCacheForRegionEditor(cachePath: String) {
            cacheToPath = cachePath
        }

        fun editRegion(regionId: Int, symmetricKeyInput: String) {
            if (cacheToPath.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please load the source cache!", "Error", JOptionPane.ERROR_MESSAGE)
                return
            }

            val cacheFrom = CacheLibrary.create(cacheToPath)
            val x = (regionId shr 8) and 0xFF
            val y = regionId and 0xFF

            val symmetricKey = symmetricKeyInput.split(",").map { it.trim().toInt() }.toIntArray()

            val regionData = cacheFrom.data(5, "l$x" + "_" + "$y", symmetricKey)
            val cacheTo = CacheLibrary.create(cacheToPath)

            if (regionData != null) {
                cacheTo.put(5, "l$x" + "_" + "$y", regionData, symmetricKey)
                cacheTo.index(5).update()
                JOptionPane.showMessageDialog(
                    null, "Region $regionId added.", "Success", JOptionPane.INFORMATION_MESSAGE
                )
            } else {
                JOptionPane.showMessageDialog(
                    null, "Region $regionId not found in source cache.", "Error", JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }
}
