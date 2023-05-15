package com.github.locxter.scrtcrtr.ng

import com.formdev.flatlaf.FlatDarkLaf
import com.github.locxter.scrtcrtr.ng.gui.Interface
import javax.swing.UIManager

fun main(args: Array<String>) {
    // Set a pleasing LaF
    try {
        UIManager.setLookAndFeel(FlatDarkLaf())
    } catch (exception: Exception) {
        println("Failed to initialize LaF.")
    }
    Interface()
}
