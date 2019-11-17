package com.arch.experiments.tests.test_042.test_with_zilvinas

import android.widget.Button
import com.arch.experiments.common.widgets.AppEditText

class Fragment {
    val textArea: AppEditText = TODO()
    val button: Button = TODO()
    val controller: Controller = Controller(this)

    fun onCreate() {
        button.setOnClickListener {
            controller.onButtonClicked()
        }

        textArea.onTextChanged { text ->
            controller.onTextChanged(text)
        }
    }

    fun setText(text: String) {
        textArea.setSafeText(text)
    }
}

class Controller(private val fragment: Fragment) {
    private var text = ""

    fun onButtonClicked() {
        text += text
        fragment.setText(text)
    }

    fun onTextChanged(text: String) {
        this.text = text
    }
}