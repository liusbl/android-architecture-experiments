package com.arch.experiments.common.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class AppEditText : AppCompatEditText {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val listeners = mutableListOf<TextWatcher>()

    override fun addTextChangedListener(watcher: TextWatcher?) {
        throw Exception("Should call onTextChanged")
        // TODO probably not the best idea to throw exception
    }

    fun onTextChanged(text: (String) -> Unit) {
        val listener = object : TextWatcher {
            override fun afterTextChanged(value: Editable?) {
                value?.toString()?.let(text::invoke)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Empty
            }
        }
        super.addTextChangedListener(listener)
        listeners.add(listener)
    }

    fun setSafeText(text: String) {
        listeners.forEach(::removeTextChangedListener)
        if (text != getText().toString()) {
            setText(text)
        }
        listeners.forEach { super.addTextChangedListener(it) }
    }
}