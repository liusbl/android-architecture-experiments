package com.arch.experiments.tests.test_059.lib

import android.content.Context
import android.view.View
import com.arch.experiments.common.widgets.AppEditText

// PTR: Put into interface just so you could scope
interface AndroidMachines :
    AndroidFactories {
    fun View.createClickObserver() = getClickConfig().createObserver(Unit)

    fun Context.createToastPusher() = getToastConfig().createPusher("")

    fun AppEditText.createTextMachine(initialText: String) =
        getTextConfig().createMachine(initialText)
}
