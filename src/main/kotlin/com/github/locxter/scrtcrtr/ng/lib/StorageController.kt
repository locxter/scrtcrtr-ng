package com.github.locxter.scrtcrtr.ng.lib

import com.github.locxter.scrtcrtr.ng.model.CharacterGrid
import java.io.*
import javax.swing.JOptionPane


class StorageController() {
    var file: File? = null

    constructor(file: File?) : this() {
        this.file = file
    }

    // Method to read a character grid from file
    fun readCharacterGrid(): CharacterGrid? {
        return try {
            val bufferedReader = BufferedReader(FileReader(file!!))
            val characterGrid = CharacterGrid()
            var line: String
            // Read the file character by character for each line
            while (bufferedReader.readLine().also { line = it ?: "" } != null) {
                characterGrid.rowCount++
                if (line.length > characterGrid.columnCount) {
                    characterGrid.columnCount = line.length
                }
                for (i in line.indices) {
                    characterGrid.grid.last()[i] = line[i]
                }
            }
            bufferedReader.close()
            characterGrid
        } catch (exception: Exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(
                null, "Reading file failed", "Error",
                JOptionPane.ERROR_MESSAGE
            )
            println(exception)
            null
        }
    }

    // Method to write a character grid to file
    fun writeCharacterGrid(characterGrid: CharacterGrid) {
        try {
            val bufferedWriter = BufferedWriter(FileWriter(file!!))
            // Write the file character by character for each line
            for (row in characterGrid.grid) {
                for (char in row) {
                    bufferedWriter.write(char.toString())
                }
                bufferedWriter.newLine()
            }
            bufferedWriter.close()
        } catch (exception: Exception) {
            // Display an error if something does not work as expected
            JOptionPane.showMessageDialog(
                null, "Writing file failed", "Error",
                JOptionPane.ERROR_MESSAGE
            )
        }
    }
}
