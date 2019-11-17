package com.arch.experiments.tests.test_065.lib

import android.content.Context
import android.view.View
import android.widget.EditText

interface AndroidMachines : AndroidFactories {
    fun View.createClickObserver() = getClickConfig().createObserver(Unit)

    fun Context.createToastPusher() = getToastConfig().createPusher("")

    fun EditText.createTextMachine(initialText: String) =
        getTextConfig().createMachine(initialText)
}