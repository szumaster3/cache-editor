package com.editor.cache.anim

import com.alex.loaders.animations.AnimationDefinitions
import com.alex.store.Store
import java.awt.EventQueue
import javax.swing.*
import javax.swing.LayoutStyle.ComponentPlacement

class AnimationSelection : JFrame {
    private var addButton: JButton? = null
    private var duplicateButton: JButton? = null
    private var editButton: JButton? = null
    private var animationListModel: DefaultListModel<String>? = null
    private var animationList: JList<String>? = null
    private var jMenu1: JMenu? = null
    private var jMenuBar1: JMenuBar? = null
    private var exitButton: JMenuItem? = null
    private var deleteButton: JButton? = null

    constructor(cache: String) {
        STORE = Store(cache)
        this.title = "Animation Selection"
        this.isResizable = false
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.setLocationRelativeTo(null)
        this.initComponents()
    }

    constructor() {
        this.initComponents()
    }

    private fun initComponents() {
        this.editButton = JButton("Edit")
        this.addButton = JButton("Add New")
        this.duplicateButton = JButton("Duplicate")
        this.deleteButton = JButton("Delete")
        this.jMenuBar1 = JMenuBar()
        this.jMenu1 = JMenu("File")
        this.exitButton = JMenuItem("Close")
        this.defaultCloseOperation = 1
        this.animationListModel = DefaultListModel()
        this.animationList = JList(this.animationListModel)
        animationList?.selectionMode = ListSelectionModel.SINGLE_SELECTION
        animationList?.layoutOrientation = JList.HORIZONTAL_WRAP
        animationList?.visibleRowCount = -1
        val jScrollPane1 = JScrollPane(this.animationList)

        editButton?.apply {
            addActionListener {
                val selectedIndex = animationList?.selectedIndex
                if (selectedIndex != -1) {
                    AnimationEditor(STORE!!.toString(), selectedIndex!!).isVisible = true
                }
            }
        }

        addButton?.apply {
            addActionListener {
                val newId = newAnimationID
                AnimationEditor(STORE!!.toString(), newId).isVisible = true
            }
        }

        duplicateButton?.apply {
            addActionListener {
                val selectedIndex = animationList?.selectedIndex
                if (selectedIndex != -1) {
                    val newId = newAnimationID
                    val originalAnimation = AnimationDefinitions.getAnimationDefinitions(selectedIndex!!)

                    if (originalAnimation != null) {
                        val clonedAnimation = originalAnimation.clone()
                        AnimationDefinitions.setAnimationDefinitions(newId, clonedAnimation)

                        addAnimationToList(newId)
                        JOptionPane.showMessageDialog(this@AnimationSelection, "Animation ID $selectedIndex duplicated as ID $newId")
                    } else {
                        JOptionPane.showMessageDialog(this@AnimationSelection, "Error: Failed to duplicate animation.", "Error", JOptionPane.ERROR_MESSAGE)
                    }
                }
            }
        }


        deleteButton?.apply {
            addActionListener {
                val selectedIndex = animationList?.selectedIndex
                if (selectedIndex != -1) {
                    val frame = JFrame()
                    val result = JOptionPane.showConfirmDialog(frame, "Do you really want to delete animation ID $selectedIndex?")
                    if (result == JOptionPane.YES_OPTION) {
                        animationListModel?.remove(selectedIndex!!)
                    }
                }
            }
        }

        jMenu1?.apply {
            exitButton?.addActionListener { dispose() }
            add(exitButton)
        }

        jMenuBar1?.add(jMenu1)
        this.jMenuBar = jMenuBar1

        val layout = GroupLayout(this.contentPane)
        this.contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                        layout.createSequentialGroup().addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, -2, 200, -2).addGroup(
                                    layout.createSequentialGroup().addComponent(this.editButton)
                                        .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                                        .addComponent(this.addButton)
                                )
                        ).addGap(0, 0, 32767)
                    ).addGroup(
                        layout.createSequentialGroup().addComponent(this.duplicateButton)
                            .addPreferredGap(ComponentPlacement.RELATED, -1, 32767)
                            .addComponent(this.deleteButton)
                    )
                ).addContainerGap(-1, 32767)
            )
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -2, 279, -2)
                    .addPreferredGap(ComponentPlacement.RELATED).addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.editButton)
                            .addComponent(this.addButton)
                    ).addPreferredGap(ComponentPlacement.RELATED).addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.duplicateButton)
                            .addComponent(this.deleteButton)
                    ).addContainerGap(-1, 32767)
            )
        )
        this.pack()
        this.loadAnimations()
    }

    private fun loadAnimations() {
        val animationCount = AnimationDefinitions.getAnimationDefinitionsSize(STORE!!)
        for (i in 0 until animationCount) {
            val animation = AnimationDefinitions.getAnimationDefinitions(i)
            if (animation != null) {
                addAnimationToList(i)
            }
        }
    }

    private fun addAnimationToList(id: Int) {
        EventQueue.invokeLater { animationListModel?.addElement("ID: $id - ${AnimationDefinitions.getAnimationDefinitions(id)}") }
    }

    val newAnimationID: Int
        get() = AnimationDefinitions.getAnimationDefinitionsSize(STORE!!)

    companion object {
        @JvmField
        var STORE: Store? = null
    }
}