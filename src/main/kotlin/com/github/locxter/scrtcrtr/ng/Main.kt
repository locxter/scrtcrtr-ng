package com.github.locxter.scrtcrtr.ng

import com.formdev.flatlaf.FlatDarkLaf
import com.github.locxter.scrtcrtr.ng.gui.CharacterGridInput
import com.github.locxter.scrtcrtr.ng.lib.StorageController
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.io.File
import javax.swing.*
import javax.swing.border.EmptyBorder

fun main(args: Array<String>) {
    var file: File? = null
    var rowCount = 16
    var columnCount = 32
    val storageController = StorageController(null)
    // Set a pleasing LaF
    try {
        UIManager.setLookAndFeel(FlatDarkLaf())
    } catch (exception: Exception) {
        println("Failed to initialize LaF.")
    }
    // UI components
    val frame = JFrame("scrtcrtr-ng")
    val panel = JPanel()
    val constraints = GridBagConstraints()
    val openButton = JButton("Open")
    val saveButton = JButton("Save")
    val rowCountLabel = JLabel("Rows:")
    val rowCountInput = JSpinner(SpinnerNumberModel(rowCount, 1, 128, 1))
    val columnCountLabel = JLabel("Columns:")
    val columnCountInput = JSpinner(SpinnerNumberModel(columnCount, 1, 128, 1))
    val characterGridInput = CharacterGridInput(rowCount, columnCount)
    val aboutLabel = JLabel("2023 locxter")
    // Add functions to the buttons and inputs
    openButton.addActionListener {
        val fileChooser = JFileChooser()
        val option = fileChooser.showOpenDialog(frame)
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.selectedFile
            storageController.file = file
            val characterGrid = storageController.readCharacterGrid()
            if (characterGrid != null) {
                characterGridInput.writeCharacterGrid(characterGrid)
                rowCount = characterGridInput.rowCount
                columnCount = characterGridInput.columnCount
                rowCountInput.value = rowCount
                columnCountInput.value = columnCount
            }
        }
    }
    saveButton.addActionListener {
        if (file == null) {
            val fileChooser = JFileChooser()
            val option = fileChooser.showSaveDialog(frame)
            if (option == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.selectedFile
                storageController.file = file
            }
        }
        if (file != null) {
            storageController.writeCharacterGrid(characterGridInput.readCharacterGrid())
        }
    }
    rowCountInput.addChangeListener {
        rowCount = rowCountInput.value as Int
        characterGridInput.rowCount = rowCount
    }
    columnCountInput.addChangeListener {
        columnCount = columnCountInput.value as Int
        characterGridInput.columnCount = columnCount
    }
    // Create the main panel
    panel.border = EmptyBorder(5, 5, 5, 5)
    panel.layout = GridBagLayout()
    constraints.insets = Insets(5, 5, 5, 5)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.weightx = 1.0
    constraints.gridx = 0
    constraints.gridy = 0
    panel.add(openButton, constraints)
    constraints.gridx = 1
    constraints.gridy = 0
    panel.add(saveButton, constraints)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.gridx = 2
    constraints.gridy = 0
    panel.add(rowCountLabel, constraints)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.gridx = 3
    constraints.gridy = 0
    panel.add(rowCountInput, constraints)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.gridx = 4
    constraints.gridy = 0
    panel.add(columnCountLabel, constraints)
    constraints.fill = GridBagConstraints.HORIZONTAL
    constraints.gridx = 5
    constraints.gridy = 0
    panel.add(columnCountInput, constraints)
    constraints.fill = GridBagConstraints.BOTH
    constraints.weighty = 1.0
    constraints.gridx = 0
    constraints.gridy = 1
    constraints.gridwidth = 6
    panel.add(characterGridInput, constraints)
    constraints.fill = GridBagConstraints.RELATIVE
    constraints.weighty = 0.0
    constraints.gridx = 0
    constraints.gridy = 2
    panel.add(aboutLabel, constraints)
    // Create the main window
    frame.size = Dimension(640, 640)
    frame.minimumSize = Dimension(480, 480)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(panel)
    frame.isVisible = true
}
