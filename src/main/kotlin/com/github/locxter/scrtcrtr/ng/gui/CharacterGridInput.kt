package com.github.locxter.scrtcrtr.ng.gui

import com.github.locxter.scrtcrtr.ng.lib.LengthLimitedDocument
import com.github.locxter.scrtcrtr.ng.model.CharacterGrid
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField

class CharacterGridInput() : JScrollPane() {
    private val inputGrid: MutableList<MutableList<JTextField>> = mutableListOf()
    private val panel = JPanel()
    private val constraints = GridBagConstraints()

    // Attributes
    var rowCount = 2
        set(value) {
            // Resize if needed
            if (field != value) {
                if (field < value) {

                    // Add rows when currently too few exist
                    for (i in field until value) {
                        val inputRow = mutableListOf<JTextField>()
                        for (j in 0 until columnCount) {
                            val input = createInput()
                            inputRow.add(input)
                        }
                        inputGrid.add(inputRow)
                    }
                } else {
                    // Delete rows when currently to many exist
                    inputGrid.subList(value, field).clear()
                }
                field = value
                // Rebuild the panel
                assemblePanel()
            }
        }
    var columnCount = 2
        set(value) {
            // Resize if needed
            if (field != value) {
                if (field < value) {
                    // Add columns when currently too few exist
                    for (inputRow in inputGrid) {
                        for (j in field until value) {
                            val input = createInput()
                            inputRow.add(input)
                        }
                    }
                } else {
                    // Delete columns when currently to many exist
                    for (inputRow in inputGrid) {
                        inputRow.subList(value, field).clear()
                    }
                }
                field = value
                // Rebuild the panel
                assemblePanel()
            }
        }

    init {
        // Configure the scroll pane
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED)
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED)
        panel.layout = GridBagLayout()
        // Create the input grid and assemble the panel
        for (i in 0 until rowCount) {
            val inputRow = mutableListOf<JTextField>()
            for (j in 0 until columnCount) {
                val input = createInput()
                inputRow.add(input)
            }
            inputGrid.add(inputRow)
        }
        assemblePanel()
    }

    constructor(rowCount: Int, columnCount: Int) : this() {
        this.rowCount = rowCount
        this.columnCount = columnCount
    }

    // Method to read the input grid
    fun readCharacterGrid(): CharacterGrid {
        val characterGrid = CharacterGrid(rowCount, columnCount)
        for (i in 0 until rowCount) {
            for (j in 0 until columnCount) {
                if (inputGrid[i][j].text.isNotEmpty()) {
                    characterGrid.grid[i][j] = inputGrid[i][j].text[0]
                }
            }
        }
        return characterGrid
    }

    // Method to write a character grid to the input grid
    fun writeCharacterGrid(characterGrid: CharacterGrid) {
        // Resize the input grid to the size of the character grid
        rowCount = characterGrid.rowCount
        columnCount = characterGrid.columnCount
        // Actually write the characters
        for (i in 0 until rowCount) {
            for (j in 0 until columnCount) {
                if (characterGrid.grid[i][j] == ' ') {
                    inputGrid[i][j].text = ""
                } else {
                    inputGrid[i][j].text = characterGrid.grid[i][j].toString()
                }
            }
        }
    }

    // Helper method for creating an input
    private fun createInput(): JTextField {
        val input = JTextField()
        val dimension = Dimension(24, 24)
        input.maximumSize = dimension
        input.minimumSize = dimension
        input.preferredSize = dimension
        input.horizontalAlignment = JTextField.CENTER
        input.document = LengthLimitedDocument(1)
        input.font = Font(Font.MONOSPACED, Font.PLAIN, 14)
        // Change selected input using the arrow keys
        input.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(event: KeyEvent) {
                val key = event.keyCode
                var row = 0
                var column = 0
                for (i in 0 until rowCount) {
                    val inputRow = inputGrid[i]
                    if (inputRow.contains(input)) {
                        column = inputRow.indexOf(input)
                        row = i
                    }
                }
                when (key) {
                    KeyEvent.VK_UP -> {
                        if (row > 0) {
                            inputGrid[row - 1][column].requestFocus()
                        }
                    }

                    KeyEvent.VK_DOWN -> {
                        if (row < rowCount - 1) {
                            inputGrid[row + 1][column].requestFocus()
                        }
                    }

                    KeyEvent.VK_LEFT -> {
                        if (column > 0) {
                            inputGrid[row][column - 1].requestFocus()
                        }
                    }

                    KeyEvent.VK_RIGHT -> {
                        if (column < columnCount - 1) {
                            inputGrid[row][column + 1].requestFocus()
                        }
                    }
                }
            }
        })
        return input
    }

    // Helper method for assembling the panel
    private fun assemblePanel() {
        panel.removeAll()
        for (i in 0 until rowCount) {
            for (j in 0 until columnCount) {
                constraints.gridx = j
                constraints.gridy = i
                panel.add(inputGrid[i][j], constraints)
            }
        }
        setViewportView(panel)
    }
}
