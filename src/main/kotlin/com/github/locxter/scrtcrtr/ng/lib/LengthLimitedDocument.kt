package com.github.locxter.scrtcrtr.ng.lib

import javax.swing.text.AttributeSet
import javax.swing.text.BadLocationException
import javax.swing.text.PlainDocument
import kotlin.math.max


class LengthLimitedDocument(limit: Int = 1) : PlainDocument() {
    val limit: Int

    init {
        this.limit = max(limit, 1)
    }

    // Method to insert the newly entered string if possible
    @Throws(BadLocationException::class)
    override fun insertString(offset: Int, str: String, attr: AttributeSet?) {
        if (length + str.length <= limit) {
            super.insertString(offset, str, attr)
        }
    }
}
