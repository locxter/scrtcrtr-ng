package com.github.locxter.scrtcrtr.ng.model

class CharacterGrid() {
    var rowCount: Int = 0
        set(value) {
            // Resize if needed
            if (value != field && value >= 0) {
                if (field < value) {
                    // Add rows when currently too few exist
                    for (i in field until value) {
                        val row = mutableListOf<Char>()
                        for (j in 0 until columnCount) {
                            row.add(' ')
                        }
                        grid.add(row)
                    }
                } else {
                    // Delete rows when currently to many exist
                    grid.subList(value, field).clear()
                }
                field = value
            }
        }
    var columnCount: Int = 0
        set(value) {
            // Resize if needed
            if (value != field && value >= 0) {
                if (field < value) {
                    // Add columns when currently too few exist
                    for (row in grid) {
                        for (j in field until value) {
                            row.add(' ')
                        }
                    }
                } else {
                    // Delete columns when currently to many exist
                    for (row in grid) {
                        row.subList(value, field).clear()
                    }
                }
                field = value
            }
        }
    val grid: MutableList<MutableList<Char>> = mutableListOf()

    constructor(rowCount: Int, columnCount: Int) : this() {
        this.rowCount = rowCount
        this.columnCount = columnCount
    }
}
