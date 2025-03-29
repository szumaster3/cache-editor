package com.editor.misc

import console.Main
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import javax.swing.*
import kotlin.math.min

class Screenshot : JFrame() {
    private var startX = 0
    private var startY = 0
    private var endX = 0
    private var endY = 0
    private var selecting = false
    private var screenshotDir = "screenshots"
    private var screenshotCaptured = false
    private var hotkey: Int = KeyEvent.VK_F1
    private var selectionConfirmed = false

    init {
        defaultCloseOperation = 1
        isUndecorated = true
        isAlwaysOnTop = true
        extendedState = MAXIMIZED_BOTH
        background = Color(0, 0, 0, 0)

        val screenPanel = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                super.paintComponent(g)
                val g2 = g as Graphics2D
                if (selecting) {
                    g2.color = Color(0, 0, 0, 150)
                    g2.fillRect(0, 0, width, height)
                    val x = min(startX, endX)
                    val y = min(startY, endY)
                    val width = kotlin.math.abs(endX - startX)
                    val height = kotlin.math.abs(endY - startY)
                    g2.clearRect(x, y, width, height)
                    g2.color = Color.RED
                    g2.stroke = BasicStroke(2f)
                    g2.drawRect(x, y, width, height)
                }
            }
        }

        screenPanel.background = Color(0, 0, 0, 0)
        screenPanel.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                if (selecting) {
                    startX = e.x
                    startY = e.y
                }
            }

            override fun mouseReleased(e: MouseEvent) {
                if (selecting) {
                    selecting = false
                    endX = e.x
                    endY = e.y
                    val confirmSave = JOptionPane.showConfirmDialog(
                        this@Screenshot, "Do you want to save this area?", "Save Screenshot", JOptionPane.YES_NO_OPTION
                    )
                    if (confirmSave == JOptionPane.YES_OPTION) {
                        selectionConfirmed = true
                        screenshotCaptured = false
                        Main.log(name, "Screenshot area selected.")
                    }
                    repaint()
                }
            }
        })

        screenPanel.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                if (selecting) {
                    endX = e.x
                    endY = e.y
                    repaint()
                }
            }
        })

        contentPane = screenPanel
        isVisible = true

        val buttonPanel = JPanel()
        buttonPanel.layout = FlowLayout()

        val buttonStyle = { button: JButton ->
            button.preferredSize = Dimension(120, 23)
            button.border = BorderFactory.createLineBorder(Color.BLACK)
        }

        val locationButton = JButton("Save Location")
        locationButton.addActionListener { selectSaveLocation() }
        buttonStyle(locationButton)

        val hotkeyButton = JButton("Hotkeys")
        hotkeyButton.addActionListener { changeHotkey() }
        buttonStyle(hotkeyButton)

        val selectAreaButton = JButton("Screen Area")
        selectAreaButton.addActionListener { enableSelectionMode() }
        buttonStyle(selectAreaButton)

        val resetButton = JButton("Reset Area")
        resetButton.addActionListener { resetSelectionArea() }
        buttonStyle(resetButton)

        val closeButton = JButton("Close")
        closeButton.addActionListener { dispose() }
        buttonStyle(closeButton)

        buttonPanel.add(locationButton)
        buttonPanel.add(hotkeyButton)
        buttonPanel.add(selectAreaButton)
        buttonPanel.add(resetButton)
        buttonPanel.add(closeButton)

        buttonPanel.background = Color(48, 94, 140, 180)
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2))
        buttonPanel.isOpaque = true
        add(buttonPanel, BorderLayout.SOUTH)

        enableHotkeys()
    }

    private fun enableHotkeys() {
        Toolkit.getDefaultToolkit().addAWTEventListener(
            { event ->
                if (event is KeyEvent && event.keyCode == hotkey) {
                    if (selectionConfirmed && !screenshotCaptured) {
                        captureScreenshot()
                        screenshotCaptured = true
                    } else if (selectionConfirmed && screenshotCaptured) {
                        screenshotCaptured = false
                    } else if (!selectionConfirmed) {
                        Main.log(name, "Please confirm the selection area before capturing!")
                    }
                }
            }, AWTEvent.KEY_EVENT_MASK
        )
    }

    private fun captureScreenshot() {
        if (!selectionConfirmed) return
        if (screenshotCaptured) return

        val x = min(startX, endX)
        val y = min(startY, endY)
        val width = kotlin.math.abs(endX - startX)
        val height = kotlin.math.abs(endY - startY)

        if (width > 0 && height > 0) {
            val robot = Robot()
            val screenCapture: BufferedImage = robot.createScreenCapture(Rectangle(x, y, width, height))
            val file = getNextScreenshotFile()
            ImageIO.write(screenCapture, "png", file)
            Main.log(name, "Screenshot saved: [${file.absolutePath}]")
            screenshotCaptured = true
            resetForNextScreenshot()
        }
    }

    private fun resetForNextScreenshot() {
        screenshotCaptured = false
    }

    private fun resetSelectionArea() {
        startX = 0
        startY = 0
        endX = 0
        endY = 0
        selecting = false
        selectionConfirmed = false
        repaint()
        Main.log(name, "Selection area reset.")
    }

    private fun getNextScreenshotFile(): File {
        val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val dir = File(screenshotDir).apply { if (!exists()) mkdirs() }

        var counter = 1
        var file: File
        do {
            val filename = "screenshot_${date}_${String.format("%03d", counter)}.png"
            file = File(dir, filename)
            counter++
        } while (file.exists())

        return file
    }

    private fun selectSaveLocation() {
        val fileChooser = JFileChooser()
        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            screenshotDir = fileChooser.selectedFile.absolutePath
            Main.log(name, "Selected screenshot directory: [$screenshotDir]")
        }
    }

    private fun enableSelectionMode() {
        selecting = true
        repaint()
    }

    private fun changeHotkey() {
        val hotkeyOptions = arrayOf("F1", "F2", "F3", "F4", "F5")
        val selectedOption = JOptionPane.showOptionDialog(
            this,
            "Choose a hotkey for screenshot capture:",
            "Change Hotkey",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            hotkeyOptions,
            hotkeyOptions[0]
        )

        if (selectedOption != -1) {
            hotkey = when (selectedOption) {
                0 -> KeyEvent.VK_F1
                1 -> KeyEvent.VK_F2
                2 -> KeyEvent.VK_F3
                3 -> KeyEvent.VK_F4
                4 -> KeyEvent.VK_F5
                else -> KeyEvent.VK_F1
            }
            Main.log(name, "Hotkey changed to: [${hotkeyOptions[selectedOption]}]")
        }
    }
}
