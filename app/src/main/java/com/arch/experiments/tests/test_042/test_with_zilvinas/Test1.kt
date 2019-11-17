package com.arch.experiments.tests.test_042.test_with_zilvinas

import android.widget.Button
import com.arch.experiments.common.widgets.AppEditText

class Fragment2 {
    val textArea: AppEditText = TODO()
    val button: Button = TODO()
    val controller: Controller2 = Controller2()

    fun onCreate() {
        textArea.text

        button.setOnClickListener {
            controller.onButtonClicked()
        }

        textArea.onTextChanged { text ->
            controller.onTextChanged(text)
        }

        controller.textContainer.observe { text ->
            textArea.setSafeText(text)
        }
    }
}

fun setSafeText(textArea: AppEditText, text: String) {
    if (textArea.text.toString() != text) {
        textArea.setText(text)
    }
}

class Controller2 {
    val textContainer = Container()

    fun onButtonClicked() {
//        textContainer.postValue(text)
    }

    fun onTextChanged(text: String) {
        textContainer.saveState(text)
    }
}

class Container {
    val latestText = ""

    fun saveState(text: String) {

    }

    fun postValue(text: String) {

    }

    fun observe(action: (String) -> Unit) {

    }
}