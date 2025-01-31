package com.erdemserhat.harmonyhaven.markdowntext

import android.content.Context
import android.text.Spannable
import android.text.Selection
import android.text.style.ClickableSpan
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView(context: Context) : AppCompatTextView(context) {

    private var isTextSelectable: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        try {
            // Handle clickable spans
            if (!isTextSelectable) {
                if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_DOWN) {
                    val link = getClickableSpans(event)

                    if (link.isNotEmpty()) {
                        if (event.action == MotionEvent.ACTION_UP) {
                            link[0].onClick(this)
                        }
                        return true
                    }
                }
            }

            // Allow default text selection behavior
            return super.onTouchEvent(event)
        } catch (e: Exception) {
            Log.e("CustomTextView", "Error in onTouchEvent: ${e.message}")
            return false
        }
    }

    private fun getClickableSpans(event: MotionEvent): Array<ClickableSpan> {
        val x = event.x.toInt()
        val y = event.y.toInt()

        val layout = layout ?: return emptyArray()

        if (y < 0 || y >= layout.height) return emptyArray()

        val line = layout.getLineForVertical(y)
        val off = layout.getOffsetForHorizontal(line, x.toFloat())

        if (off < 0) return emptyArray()

        val spannable = text as? Spannable ?: return emptyArray()
        return spannable.getSpans(off, off, ClickableSpan::class.java)
    }

    private fun clearSelection() {
        try {
            val spannable = text as? Spannable ?: return
            val selectionStart = Selection.getSelectionStart(spannable)
            val selectionEnd = Selection.getSelectionEnd(spannable)

            // Log the current selection state for debugging
            Log.d("CustomTextView", "Selection Start: $selectionStart, Selection End: $selectionEnd")

            // Only remove selection if the indices are valid
            if (selectionStart >= 0 && selectionEnd >= 0) {
                Selection.removeSelection(spannable)
            } else {
                // If the selection is invalid, force-clear it
                Selection.setSelection(spannable, 0, 0)
            }
        } catch (e: Exception) {
            Log.e("CustomTextView", "Error in clearSelection: ${e.message}")
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        try {
            super.setText(text, type)

            // Clear selection whenever the text changes
            clearSelection()
        } catch (e: Exception) {
            Log.e("CustomTextView", "Error in setText: ${e.message}")
        }
    }

    override fun setTextIsSelectable(selectable: Boolean) {
        try {
            super.setTextIsSelectable(selectable)
            isTextSelectable = selectable

            // Clear any existing selection when text selectability is changed
            if (!selectable) {
                clearSelection()
            }
        } catch (e: Exception) {
            Log.e("CustomTextView", "Error in setTextIsSelectable: ${e.message}")
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        try {
            super.onTextChanged(text, start, lengthBefore, lengthAfter)

            // Clear selection whenever the text changes
            clearSelection()
        } catch (e: Exception) {
            Log.e("CustomTextView", "Error in onTextChanged: ${e.message}")
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        try {
            super.onSelectionChanged(selStart, selEnd)

            // Ensure the selection is always valid
            if (selStart < 0 || selEnd < 0) {
                Log.w("CustomTextView", "Invalid selection range: ($selStart, $selEnd). Resetting to (0, 0).")
                Selection.setSelection(text as Spannable, 0, 0)
            }
        } catch (e: Exception) {
            Log.e("CustomTextView", "Error in onSelectionChanged: ${e.message}")
        }
    }
}