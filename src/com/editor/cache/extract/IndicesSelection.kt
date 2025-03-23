package com.editor.cache.extract

import com.alex.util.Utils
import com.editor.cache.extract.Core.start
import console.Main
import java.awt.event.*
import javax.swing.*

class IndicesSelection : JFrame() {
    private var fileChooser: JFileChooser? = null

    private var btnExtract: JButton? = null
    private var checkBox: JCheckBox? = null
    private var checkBox1: JCheckBox? = null
    private var checkBox2: JCheckBox? = null
    private var checkBox3: JCheckBox? = null
    private var checkBox4: JCheckBox? = null
    private var checkBox5: JCheckBox? = null
    private var checkBox6: JCheckBox? = null
    private var checkBox7: JCheckBox? = null
    private var checkBox8: JCheckBox? = null
    private var checkBox9: JCheckBox? = null
    private var checkBox10: JCheckBox? = null
    private var checkBox11: JCheckBox? = null
    private var checkBox12: JCheckBox? = null
    private var checkBox13: JCheckBox? = null
    private var checkBox14: JCheckBox? = null
    private var checkBox15: JCheckBox? = null
    private var checkBox16: JCheckBox? = null
    private var checkBox17: JCheckBox? = null
    private var checkBox18: JCheckBox? = null
    private var checkBox19: JCheckBox? = null
    private var checkBox20: JCheckBox? = null
    private var checkBox21: JCheckBox? = null
    private var checkBox22: JCheckBox? = null
    private var checkBox23: JCheckBox? = null
    private var checkBox24: JCheckBox? = null
    private var checkBox25: JCheckBox? = null
    private var checkBox26: JCheckBox? = null
    private var checkBox27: JCheckBox? = null
    private var checkBox28: JCheckBox? = null
    private var checkBox255: JCheckBox? = null
    private var lblNewLabel: JLabel? = null

    private inner class CheckBoxListener : ItemListener {
        override fun itemStateChanged(e: ItemEvent) {
            if (e.source === checkBox) {
                if (checkBox!!.isSelected) {
                    Utils.toExtract[0] = !Utils.toExtract[0]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[0] = !Utils.toExtract[0]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox1) {
                if (checkBox1!!.isSelected) {
                    Utils.toExtract[1] = !Utils.toExtract[1]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[1] = !Utils.toExtract[1]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox2) {
                if (checkBox2!!.isSelected) {
                    Utils.toExtract[2] = !Utils.toExtract[2]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[2] = !Utils.toExtract[2]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox3) {
                if (checkBox3!!.isSelected) {
                    Utils.toExtract[3] = !Utils.toExtract[3]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[3] = !Utils.toExtract[3]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox4) {
                if (checkBox4!!.isSelected) {
                    Utils.toExtract[4] = !Utils.toExtract[4]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[4] = !Utils.toExtract[4]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox5) {
                if (checkBox5!!.isSelected) {
                    Utils.toExtract[5] = !Utils.toExtract[5]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[5] = !Utils.toExtract[5]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox6) {
                if (checkBox6!!.isSelected) {
                    Utils.toExtract[6] = !Utils.toExtract[6]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[6] = !Utils.toExtract[6]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox7) {
                if (checkBox7!!.isSelected) {
                    Utils.toExtract[7] = !Utils.toExtract[7]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[7] = !Utils.toExtract[7]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox8) {
                if (checkBox8!!.isSelected) {
                    Utils.toExtract[8] = !Utils.toExtract[8]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[8] = !Utils.toExtract[8]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox9) {
                if (checkBox9!!.isSelected) {
                    Utils.toExtract[9] = !Utils.toExtract[9]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[9] = !Utils.toExtract[9]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox10) {
                if (checkBox10!!.isSelected) {
                    Utils.toExtract[10] = !Utils.toExtract[10]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[10] = !Utils.toExtract[10]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox11) {
                if (checkBox11!!.isSelected) {
                    Utils.toExtract[11] = !Utils.toExtract[11]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[11] = !Utils.toExtract[11]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox12) {
                if (checkBox12!!.isSelected) {
                    Utils.toExtract[12] = !Utils.toExtract[12]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[12] = !Utils.toExtract[12]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox13) {
                if (checkBox13!!.isSelected) {
                    Utils.toExtract[13] = !Utils.toExtract[13]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[13] = !Utils.toExtract[13]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox14) {
                if (checkBox14!!.isSelected) {
                    Utils.toExtract[14] = !Utils.toExtract[14]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[14] = !Utils.toExtract[14]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox15) {
                if (checkBox15!!.isSelected) {
                    Utils.toExtract[15] = !Utils.toExtract[15]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[15] = !Utils.toExtract[15]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox16) {
                if (checkBox16!!.isSelected) {
                    Utils.toExtract[16] = !Utils.toExtract[16]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[16] = !Utils.toExtract[16]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox17) {
                if (checkBox17!!.isSelected) {
                    Utils.toExtract[17] = !Utils.toExtract[17]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[17] = !Utils.toExtract[17]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox18) {
                if (checkBox18!!.isSelected) {
                    Utils.toExtract[18] = !Utils.toExtract[18]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[18] = !Utils.toExtract[18]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox19) {
                if (checkBox19!!.isSelected) {
                    Utils.toExtract[19] = !Utils.toExtract[19]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[19] = !Utils.toExtract[19]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox20) {
                if (checkBox20!!.isSelected) {
                    Utils.toExtract[20] = !Utils.toExtract[20]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[20] = !Utils.toExtract[20]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox21) {
                if (checkBox21!!.isSelected) {
                    Utils.toExtract[21] = !Utils.toExtract[21]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[21] = !Utils.toExtract[21]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox22) {
                if (checkBox22!!.isSelected) {
                    Utils.toExtract[22] = !Utils.toExtract[22]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[22] = !Utils.toExtract[22]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox23) {
                if (checkBox23!!.isSelected) {
                    Utils.toExtract[23] = !Utils.toExtract[23]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[23] = !Utils.toExtract[23]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox24) {
                if (checkBox24!!.isSelected) {
                    Utils.toExtract[24] = !Utils.toExtract[24]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[24] = !Utils.toExtract[24]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox25) {
                if (checkBox25!!.isSelected) {
                    Utils.toExtract[25] = !Utils.toExtract[25]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[25] = !Utils.toExtract[25]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox26) {
                if (checkBox26!!.isSelected) {
                    Utils.toExtract[26] = !Utils.toExtract[26]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[26] = !Utils.toExtract[26]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox27) {
                if (checkBox27!!.isSelected) {
                    Utils.toExtract[27] = !Utils.toExtract[27]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[27] = !Utils.toExtract[27]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox28) {
                if (checkBox28!!.isSelected) {
                    Utils.toExtract[28] = !Utils.toExtract[28]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[28] = !Utils.toExtract[28]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            } else if (e.source === checkBox255) {
                if (checkBox255!!.isSelected) {
                    Utils.toExtract[255] = !Utils.toExtract[255]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                } else {
                    Utils.toExtract[255] = !Utils.toExtract[255]
                    Main.log(this.javaClass.name, Utils.toExtract.contentToString())
                }
            }
        }
    }

    init {
        setSize(350, 370)
        initComponents()
        setLocationRelativeTo(null)
        defaultCloseOperation = 1
        isResizable = false
    }

    private fun selectInput(e: MouseEvent) {
        fileChooser = JFileChooser()
        fileChooser!!.fileSelectionMode = 1
        val response = fileChooser!!.showOpenDialog(this)
        if (response == 0) {
            val selectedDir = fileChooser!!.selectedFile
            if (selectedDir.isDirectory) {
                Utils.inputFolder = selectedDir.absolutePath
                Main.log(this.javaClass.name, "Input directory set as: " + Utils.inputFolder)
            }
            if (Utils.inputFolder !== "" && Utils.outputFolder !== "" && Utils.format !== "") {
                btnExtract!!.isEnabled =
                    true
            }
        }
    }

    private fun selectOutput(e: MouseEvent) {
        fileChooser = JFileChooser()
        fileChooser!!.fileSelectionMode = 1
        val response = fileChooser!!.showOpenDialog(this)
        if (response == 0) {
            val selectedDir = fileChooser!!.selectedFile
            if (selectedDir.isDirectory) {
                Utils.outputFolder = selectedDir.absolutePath
                Main.log(this.javaClass.name, "Output directory set as: " + Utils.outputFolder)
            }
            if (Utils.inputFolder !== "" && Utils.outputFolder !== "" && Utils.format !== "") {
                btnExtract!!.isEnabled =
                    true
            }
        }
    }

    private fun extract(e: MouseEvent) {
        start()
        lblNewLabel!!.text = "Done"
    }

    private fun initComponents() {
        btnExtract = JButton()
        checkBox = JCheckBox("0")
        checkBox1 = JCheckBox("1")
        checkBox2 = JCheckBox("2")
        checkBox3 = JCheckBox("3")
        checkBox4 = JCheckBox("4")
        checkBox5 = JCheckBox("5")
        checkBox6 = JCheckBox("6")
        checkBox7 = JCheckBox("7")
        checkBox8 = JCheckBox("8")
        checkBox9 = JCheckBox("9")
        checkBox10 = JCheckBox("10")
        checkBox11 = JCheckBox("11")
        checkBox12 = JCheckBox("12")
        checkBox13 = JCheckBox("13")
        checkBox14 = JCheckBox("14")
        checkBox15 = JCheckBox("15")
        checkBox16 = JCheckBox("16")
        checkBox17 = JCheckBox("17")
        checkBox18 = JCheckBox("18")
        checkBox19 = JCheckBox("19")
        checkBox20 = JCheckBox("20")
        checkBox21 = JCheckBox("21")
        checkBox22 = JCheckBox("22")
        checkBox23 = JCheckBox("23")
        checkBox24 = JCheckBox("24")
        checkBox25 = JCheckBox("25")
        checkBox26 = JCheckBox("26")
        checkBox27 = JCheckBox("27")
        checkBox28 = JCheckBox("28")
        checkBox255 = JCheckBox("255")

        title = "Cache Exporter Poesy700"
        isResizable = false
        val contentPane = contentPane
        getContentPane().layout = null

        val btnNewButton = JButton("Select input folder")
        btnNewButton.setBounds(20, 11, 127, 23)
        getContentPane().add(btnNewButton)
        btnNewButton.addMouseListener(
            object : MouseAdapter() {
                override fun mouseReleased(e: MouseEvent) {
                    selectInput(e)
                }
            },
        )

        val button = JButton("Select output folder")
        button.setBounds(184, 11, 127, 23)
        getContentPane().add(button)
        button.addMouseListener(
            object : MouseAdapter() {
                override fun mouseReleased(e: MouseEvent) {
                    selectOutput(e)
                }
            },
        )

        val format = arrayOf<String?>("", "Old", "New")
        val comboBox: JComboBox<*> = JComboBox<Any?>(format)
        comboBox.toolTipText = "Choose format"
        comboBox.setBounds(184, 59, 127, 20)
        getContentPane().add(comboBox)
        val cbActionListener =
            ActionListener {
                val s = comboBox.selectedItem as String
                when (s) {
                    "Old" -> {
                        Utils.format = "old"
                        Main.log(this.javaClass.name, "cache format set to: " + Utils.format)
                        if (Utils.inputFolder !== "" && Utils.outputFolder !== "" && Utils.format !== "") {
                            btnExtract!!.isEnabled = true
                            checkBox!!.isEnabled = true
                            checkBox1!!.isEnabled = true
                            checkBox2!!.isEnabled = true
                            checkBox3!!.isEnabled = true
                            checkBox4!!.isEnabled = true
                            checkBox5!!.isEnabled = false
                            checkBox6!!.isEnabled = false
                            checkBox7!!.isEnabled = false
                            checkBox8!!.isEnabled = false
                            checkBox9!!.isEnabled = false
                            checkBox10!!.isEnabled = false
                            checkBox11!!.isEnabled = false
                            checkBox12!!.isEnabled = false
                            checkBox13!!.isEnabled = false
                            checkBox14!!.isEnabled = false
                            checkBox15!!.isEnabled = false
                            checkBox16!!.isEnabled = false
                            checkBox17!!.isEnabled = false
                            checkBox18!!.isEnabled = false
                        }
                    }

                    "New" -> {
                        Utils.format = "new"
                        Main.log(this.javaClass.name, "cache format set to: " + Utils.format)
                        if (Utils.inputFolder !== "" && Utils.outputFolder !== "" && Utils.format !== "") {
                            btnExtract!!.isEnabled = true
                            checkBox!!.isEnabled = true
                            checkBox1!!.isEnabled = true
                            checkBox2!!.isEnabled = true
                            checkBox3!!.isEnabled = true
                            checkBox4!!.isEnabled = true
                            checkBox5!!.isEnabled = true
                            checkBox6!!.isEnabled = true
                            checkBox7!!.isEnabled = true
                            checkBox8!!.isEnabled = true
                            checkBox9!!.isEnabled = true
                            checkBox10!!.isEnabled = true
                            checkBox11!!.isEnabled = true
                            checkBox12!!.isEnabled = true
                            checkBox13!!.isEnabled = true
                            checkBox14!!.isEnabled = true
                            checkBox15!!.isEnabled = true
                            checkBox16!!.isEnabled = true
                            checkBox17!!.isEnabled = true
                            checkBox18!!.isEnabled = true
                            checkBox19!!.isEnabled = true
                            checkBox20!!.isEnabled = true
                            checkBox21!!.isEnabled = true
                            checkBox22!!.isEnabled = true
                            checkBox23!!.isEnabled = true
                            checkBox24!!.isEnabled = true
                            checkBox25!!.isEnabled = true
                            checkBox26!!.isEnabled = true
                            checkBox27!!.isEnabled = true
                            checkBox28!!.isEnabled = true
                            checkBox255!!.isEnabled = true
                        }
                    }

                    else -> {
                        Main.log(this.javaClass.name, "Default")
                        Utils.format = ""
                        btnExtract!!.isEnabled = false
                        checkBox!!.isEnabled = false
                        btnExtract!!.isEnabled = true
                        checkBox!!.isEnabled = true
                        checkBox1!!.isEnabled = true
                        checkBox2!!.isEnabled = true
                        checkBox3!!.isEnabled = true
                        checkBox4!!.isEnabled = true
                        checkBox5!!.isEnabled = true
                        checkBox6!!.isEnabled = true
                        checkBox7!!.isEnabled = true
                        checkBox8!!.isEnabled = true
                        checkBox9!!.isEnabled = true
                        checkBox10!!.isEnabled = true
                        checkBox11!!.isEnabled = true
                        checkBox12!!.isEnabled = true
                        checkBox13!!.isEnabled = true
                        checkBox14!!.isEnabled = true
                        checkBox15!!.isEnabled = true
                        checkBox16!!.isEnabled = true
                        checkBox17!!.isEnabled = true
                        checkBox18!!.isEnabled = true
                        checkBox19!!.isEnabled = true
                        checkBox20!!.isEnabled = true
                        checkBox21!!.isEnabled = true
                        checkBox22!!.isEnabled = true
                        checkBox23!!.isEnabled = true
                        checkBox24!!.isEnabled = true
                        checkBox25!!.isEnabled = true
                        checkBox26!!.isEnabled = true
                        checkBox27!!.isEnabled = true
                        checkBox28!!.isEnabled = true
                        checkBox255!!.isEnabled = true
                    }
                }
            }

        comboBox.addActionListener(cbActionListener)

        val lblCacheFormat = JLabel("Cache Format")
        lblCacheFormat.setBounds(22, 63, 74, 14)
        getContentPane().add(lblCacheFormat)


        btnExtract!!.text = "Extract"
        btnExtract!!.isEnabled = false
        btnExtract!!.setBounds(125, 265, 89, 23)
        btnExtract!!.addMouseListener(
            object : MouseAdapter() {
                override fun mouseReleased(e: MouseEvent) {
                    lblNewLabel!!.text = "Extracting..."
                    extract(e)
                }
            },
        )
        getContentPane().add(btnExtract)

        checkBox!!.isEnabled = false
        checkBox!!.setBounds(27, 126, 31, 23)
        checkBox!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox)

        checkBox1!!.isEnabled = false
        checkBox1!!.setBounds(27, 152, 31, 23)
        checkBox1!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox1)

        checkBox2!!.isEnabled = false
        checkBox2!!.setBounds(27, 177, 31, 23)
        checkBox2!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox2)

        checkBox3!!.isEnabled = false
        checkBox3!!.setBounds(27, 203, 31, 23)
        checkBox3!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox3)

        checkBox4!!.isEnabled = false
        checkBox4!!.setBounds(62, 126, 31, 23)
        checkBox4!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox4)

        checkBox5!!.isEnabled = false
        checkBox5!!.setBounds(62, 152, 31, 23)
        checkBox5!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox5)

        checkBox6!!.isEnabled = false
        checkBox6!!.setBounds(62, 177, 31, 23)
        checkBox6!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox6)

        checkBox7!!.isEnabled = false
        checkBox7!!.setBounds(62, 203, 31, 23)
        checkBox7!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox7)

        checkBox8!!.isEnabled = false
        checkBox8!!.setBounds(95, 126, 31, 23)
        checkBox8!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox8)

        checkBox9!!.isEnabled = false
        checkBox9!!.setBounds(95, 152, 31, 23)
        checkBox9!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox9)

        checkBox10!!.isEnabled = false
        checkBox10!!.setBounds(95, 177, 37, 23)
        checkBox10!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox10)

        checkBox11!!.isEnabled = false
        checkBox11!!.setBounds(95, 203, 37, 23)
        checkBox11!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox11)

        checkBox12!!.isEnabled = false
        checkBox12!!.setBounds(136, 126, 37, 23)
        checkBox12!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox12)

        checkBox13!!.isEnabled = false
        checkBox13!!.setBounds(136, 152, 37, 23)
        checkBox13!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox13)

        checkBox14!!.isEnabled = false
        checkBox14!!.setBounds(136, 177, 37, 23)
        checkBox14!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox14)

        checkBox15!!.isEnabled = false
        checkBox15!!.setBounds(136, 203, 37, 23)
        checkBox15!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox15)

        checkBox16!!.isEnabled = false
        checkBox16!!.setBounds(184, 126, 37, 23)
        checkBox16!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox16)

        checkBox17!!.isEnabled = false
        checkBox17!!.setBounds(184, 152, 43, 23)
        checkBox17!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox17)

        checkBox18!!.isEnabled = false
        checkBox18!!.setBounds(184, 177, 43, 23)
        checkBox18!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox18)

        checkBox19!!.isEnabled = false
        checkBox19!!.setBounds(184, 203, 43, 23)
        checkBox19!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox19)

        checkBox20!!.isEnabled = false
        checkBox20!!.setBounds(232, 126, 43, 23)
        checkBox20!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox20)

        checkBox21!!.isEnabled = false
        checkBox21!!.setBounds(232, 152, 43, 23)
        checkBox21!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox21)

        checkBox22!!.isEnabled = false
        checkBox22!!.setBounds(232, 177, 43, 23)
        checkBox22!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox22)

        checkBox23!!.isEnabled = false
        checkBox23!!.setBounds(232, 203, 43, 23)
        checkBox23!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox23)

        checkBox24!!.isEnabled = false
        checkBox24!!.setBounds(280, 126, 43, 23)
        checkBox24!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox24)

        checkBox25!!.isEnabled = false
        checkBox25!!.setBounds(280, 152, 43, 23)
        checkBox25!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox25)

        checkBox26!!.isEnabled = false
        checkBox26!!.setBounds(280, 177, 43, 23)
        checkBox26!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox26)

        checkBox27!!.isEnabled = false
        checkBox27!!.setBounds(280, 203, 43, 23)
        checkBox27!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox27)

        checkBox28!!.isEnabled = false
        checkBox28!!.setBounds(27, 229, 43, 23)
        checkBox28!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox28)

        checkBox255!!.isEnabled = false
        checkBox255!!.setBounds(27, 255, 43, 23)
        checkBox255!!.addItemListener(CheckBoxListener())
        getContentPane().add(checkBox255)

        val lblIndexes = JLabel("Indices")
        lblIndexes.setBounds(22, 105, 46, 14)
        getContentPane().add(lblIndexes)

        lblNewLabel = JLabel()
        lblNewLabel!!.setBounds(148, 240, 46, 14)
        getContentPane().add(lblNewLabel)
    }
}
