package com.editor.cache.anim

import com.alex.loaders.animations.AnimationDefinitions
import com.alex.store.Store
import java.awt.*
import javax.swing.*

class AnimationEditor(private val cache: String, private val animationId: Int) : JFrame() {
    private val fields = mutableListOf<JTextField>()
    private var selectedAnimation: AnimationDefinitions? = null

    init {
        title = "Animation Editor - ID: $animationId"
        setSize(600, 700)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setLocationRelativeTo(null)

        val store = Store(cache)
        selectedAnimation = AnimationDefinitions.getAnimationDefinitions(animationId)

        val editPanel = JPanel(GridLayout(19, 2, 5, 5))
        val labels = arrayOf(
            "Loop Cycles", "Priority", "Left Hand Equip", "Right Hand Equip", "AnInt2137",
            "AnInt2140", "AnInt2145", "AnInt2155", "AnInt2162", "Loop Delay",
            "ABoolean2141", "Handled Sounds", "ABooleanArray2149", "AnIntArray2151",
            "ABoolean2152", "Sound Min Delay", "Sound Max Delay", "AnIntArray1362", "Effect2Sound"
        )

        labels.forEach {
            editPanel.add(JLabel("$it:"))
            val field = JTextField()
            fields.add(field)
            editPanel.add(field)
        }

        selectedAnimation?.let { loadAnimation(it) }

        val saveButton = JButton("Save Changes").apply {
            addActionListener { saveChanges() }
        }

        layout = BorderLayout()
        add(editPanel, BorderLayout.CENTER)
        add(saveButton, BorderLayout.SOUTH)
    }

    private fun loadAnimation(animation: AnimationDefinitions) {
        val values = arrayOf(
            animation.loopCycles.toString(), animation.priority.toString(),
            animation.leftHandEquip.toString(), animation.rightHandEquip.toString(),
            animation.anInt2137.toString(), animation.anInt2140.toString(),
            animation.anInt2145.toString(), animation.anInt2155.toString(),
            animation.anInt2162.toString(), animation.loopDelay.toString(),
            animation.aBoolean2141.toString(),
            animation.handledSounds?.joinToString(";") { it!!.joinToString(",") } ?: "",
            animation.aBooleanArray2149?.joinToString(",") ?: "",
            animation.anIntArray2151?.joinToString(",") ?: "",
            animation.aBoolean2152.toString(),
            animation.soundMinDelay?.joinToString(",") ?: "",
            animation.soundMaxDelay?.joinToString(",") ?: "",
            animation.anIntArray1362?.joinToString(",") ?: "",
            animation.effect2Sound.toString()
        )

        values.forEachIndexed { index, value -> fields[index].text = value }
    }

    private fun saveChanges() {
        selectedAnimation?.let {
            try {
                it.loopCycles = fields[0].text.toInt()
                it.priority = fields[1].text.toInt()
                it.leftHandEquip = fields[2].text.toInt()
                it.rightHandEquip = fields[3].text.toInt()
                it.anInt2137 = fields[4].text.toInt()
                it.anInt2140 = fields[5].text.toInt()
                it.anInt2145 = fields[6].text.toInt()
                it.anInt2155 = fields[7].text.toInt()
                it.anInt2162 = fields[8].text.toInt()
                it.loopDelay = fields[9].text.toInt()
                it.aBoolean2141 = fields[10].text.toBooleanStrictOrNull() ?: false

                it.handledSounds = fields[11].text.takeIf { it.isNotBlank() }?.split(";")
                    ?.map { group -> group.split(",").mapNotNull { it.toIntOrNull() }.toIntArray() }
                    ?.toTypedArray()

                it.aBooleanArray2149 = fields[12].text.takeIf { it.isNotBlank() }?.split(",")
                    ?.mapNotNull { it.toBooleanStrictOrNull() }?.toBooleanArray()

                it.anIntArray2151 = fields[13].text.takeIf { it.isNotBlank() }?.split(",")
                    ?.mapNotNull { it.toIntOrNull() }?.toIntArray()

                it.aBoolean2152 = fields[14].text.toBooleanStrictOrNull() ?: false

                it.soundMinDelay = fields[15].text.takeIf { it.isNotBlank() }?.split(",")
                    ?.mapNotNull { it.toIntOrNull() }?.toIntArray()

                it.soundMaxDelay = fields[16].text.takeIf { it.isNotBlank() }?.split(",")
                    ?.mapNotNull { it.toIntOrNull() }?.toIntArray()

                it.anIntArray1362 = fields[17].text.takeIf { it.isNotBlank() }?.split(",")
                    ?.mapNotNull { it.toIntOrNull() }?.toIntArray()

                it.effect2Sound = fields[18].text.toBooleanStrictOrNull() ?: false

                saveAnimation(it)

                JOptionPane.showMessageDialog(this, "Changes saved successfully!")
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(this, "Invalid input! ${e.message}", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }

    private fun saveAnimation(animation: AnimationDefinitions) {
        val store = Store(cache)
        val encodedData = AnimationEncoder.encode(animation)

        AnimationDefinitions.setAnimationDefinitions(animationId, animation)

        store.indexes[20].putFile(animationId ushr 7, animationId and 127, encodedData)
        store.indexes[20].rewriteTable()
    }
}
