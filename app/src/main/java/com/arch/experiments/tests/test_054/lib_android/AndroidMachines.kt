package com.arch.experiments.tests.test_054.lib_android

import android.content.Context
import android.view.View
import com.arch.experiments.common.widgets.AppEditText

fun View.createClickObserver() = getClickConfig().createObserver(Unit)

fun Context.createToastPusher() = getToastConfig().createPusher("")

fun AppEditText.createTextMachine(initialText: String) = getTextConfig().createMachine(initialText)