package com.github.locxter.scrtcrtr.ng.lib

import javax.swing.text.AttributeSet
import javax.swing.text.BadLocationException
import javax.swing.text.PlainDocument


class LengthLimitedDocument() : PlainDocument() {
    private var limit = 1

    constructor(limit: Int) : this() {
        this.limit = limit
    }

    // Method to insert the newly entered string if possible
    @Throws(BadLocationException::class)
    override fun insertString(offset: Int, str: String, attr: AttributeSet?) {
        if (length + str.length <= limit) {
            super.insertString(offset, str, attr)
        }
    }
}
