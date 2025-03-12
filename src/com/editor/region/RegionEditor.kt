package com.editor.region

import com.displee.cache.CacheLibrary
import java.awt.*
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.swing.*

class RegionEditor(private val cacheTo: String) : JFrame() {
    private val regionID = JTextField(10)
    private val symmetricKeyField = JTextField(20)
    private val sourceCache = JTextField(20).apply { isEditable = false }
    private val regionOutput = JTextField(20).apply { isEditable = false }
    private val regionInput = JTextField(10)
    private val editButton = JButton("Transfer Region")
    private val selectSourceCache = JButton("Select Source Cache")
    private val backupButton = JButton("Backup Cache")

    init {
        title = "Region Editor"
        layout = GridBagLayout()
        setSize(350, 420)
        defaultCloseOperation = 1
        setLocationRelativeTo(null)
        val gbc = GridBagConstraints().apply { insets = Insets(5, 5, 5, 5); fill = GridBagConstraints.HORIZONTAL }

        add(JLabel("Region ID:"), gbc.apply { gridx = 0; gridy = 0 })
        add(regionID, gbc.apply { gridx = 1 })

        add(JLabel("Symmetric Key:"), gbc.apply { gridx = 0; gridy = 1 })
        add(symmetricKeyField, gbc.apply { gridx = 1 })

        add(JLabel("Region Output:"), gbc.apply { gridx = 0; gridy = 2 })
        add(regionOutput, gbc.apply { gridx = 1 })

        add(JLabel("Enter Region (lX_Y):"), gbc.apply { gridx = 0; gridy = 3 })
        add(regionInput, gbc.apply { gridx = 1 })

        add(createCalculateRegionButton(), gbc.apply { gridx = 1; gridy = 4 })
        add(createCalculateRegionIdButton(), gbc.apply { gridx = 1; gridy = 5 })

        add(JLabel("Cache From Path:"), gbc.apply { gridx = 0; gridy = 6 })
        add(sourceCache, gbc.apply { gridx = 1 })

        selectSourceCache.addActionListener { selectSourceCachePath() }
        add(selectSourceCache, gbc.apply { gridx = 1; gridy = 7 })

        editButton.addActionListener { transferRegion() }
        add(editButton, gbc.apply { gridx = 1; gridy = 8 })

        backupButton.addActionListener { backupCache() }
        add(backupButton, gbc.apply { gridx = 1; gridy = 9 })

        isVisible = true
    }

    private fun transferRegion() {
        val regionId = regionID.text.trim().toIntOrNull()
        val xtea = symmetricKeyField.text.trim().split(",").mapNotNull { it.toIntOrNull() }.toIntArray()

        if (regionId == null || sourceCache.text.isEmpty()) {
            showError("Invalid Region ID or Cache Path!")
            return
        }

        val cacheFrom = CacheLibrary.create(sourceCache.text)
        val cacheDest = CacheLibrary.create(cacheTo)
        val x = (regionId shr 8) and 0xFF
        val y = regionId and 0xFF
        val regionFileName = "l${x}_${y}"

        val regionData = cacheFrom.data(5, regionFileName, xtea)
        val indexTo = cacheDest.index(5)

        if (regionData != null) {
            cacheDest.put(5, regionFileName, regionData, xtea)
            indexTo.update()
            JOptionPane.showMessageDialog(this, "Region $regionId transferred successfully.", "Success", JOptionPane.INFORMATION_MESSAGE)
        } else {
            showError("Region $regionId not found in source cache.")
        }
    }

    private fun backupCache() {
        val cachePath = if (sourceCache.text.isNotEmpty()) sourceCache.text else cacheTo
        val cacheDirectory = File(cachePath)
        if (!cacheDirectory.exists() || !cacheDirectory.isDirectory) {
            showError("Invalid source cache path.")
            return
        }
        val zipFilePath = "$cachePath/cache_backup.zip"
        try {
            FileOutputStream(zipFilePath).use { fos ->
                ZipOutputStream(fos).use { zos ->
                    zipDirectory(cacheDirectory, cacheDirectory, zos)
                }
            }
            JOptionPane.showMessageDialog(this, "Backup created: $zipFilePath", "Success", JOptionPane.INFORMATION_MESSAGE)
        } catch (e: IOException) {
            showError("An error occurred while backing up the cache.")
        }
    }

    private fun zipDirectory(folder: File, baseFolder: File, zos: ZipOutputStream) {
        folder.listFiles()?.forEach { file ->
            if (file.name.endsWith(".zip")) return@forEach
            val entryName = file.relativeTo(baseFolder).path.replace("\\", "/")
            if (file.isDirectory) {
                zipDirectory(file, baseFolder, zos)
            } else {
                zos.putNextEntry(ZipEntry(entryName))
                file.inputStream().use { it.copyTo(zos) }
                zos.closeEntry()
            }
        }
    }

    private fun createCalculateRegionButton() = JButton("Calculate Region").apply {
        addActionListener {
            val id = regionID.text.trim().toIntOrNull()
            if (id != null) {
                val x = (id shr 8) and 0xFF
                val y = id and 0xFF
                regionOutput.text = "l${x}_${y}"
            } else {
                showError("Invalid Region ID!")
            }
        }
    }

    private fun createCalculateRegionIdButton() = JButton("Calculate Region ID").apply {
        addActionListener {
            val text = regionInput.text.trim()
            if (text.startsWith("l") && "_" in text) {
                val parts = text.substring(1).split("_")
                if (parts.size == 2) {
                    val x = parts[0].toInt()
                    val y = parts[1].toInt()
                    regionID.text = ((x shl 8) or y).toString()
                } else {
                    showError("Invalid Region Format (lX_Y)!")
                }
            } else {
                showError("Invalid Region Format (lX_Y)!")
            }
        }
    }

    private fun selectSourceCachePath() {
        val fileChooser = JFileChooser().apply { fileSelectionMode = JFileChooser.DIRECTORIES_ONLY }
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            sourceCache.text = fileChooser.selectedFile.absolutePath
        }
    }

    private fun showError(message: String) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE)
    }
}
