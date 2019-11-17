package com.arch.experiments.tests.test_050.lib_android.new_approach

import com.arch.experiments.common.widgets.AppEditText

fun AppEditText.getFactory(): StateHandlerFactory<String> {
    return StateHandlerFactory(onObserve = ::setSafeText, onPush = ::onTextChanged)
}