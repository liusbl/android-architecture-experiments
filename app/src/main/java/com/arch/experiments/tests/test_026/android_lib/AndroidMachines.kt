package com.arch.experiments.tests.test_026.android_lib

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.arch.experiments.common.widgets.AppEditText
import com.arch.experiments.tests.test_026.core_lib.GlobalMachine
import com.arch.experiments.tests.test_026.core_lib.Machine

//TODO consider better method of state machine creation
fun createToastMachine(
    context: Context,
    initialState: String = ""
) = createPresenterMachine(false, initialState) {
    observe { Toast.makeText(context, value, Toast.LENGTH_SHORT).show() }
}

fun View.createClickMachine() = createPresenterMachine(false, Unit) {
    this@createClickMachine.setOnClickListener { push() }
}

fun TextView.createMachine(initialState: String = "") =
    createPresenterMachine(true, initialState) { observe { this@createMachine.text = value } }

fun AppEditText.createMachine(initialState: String = ""): Machine<String> =
    createPresenterMachine(true, initialState) {
        val editText = this@createMachine
        observe { editText.setSafeText(value) }
        editText.onTextChanged { text -> push { text } }
    }


// TODO: initialization cia factory class
//class PresenterMachine<T>(
//    private val callObserveOnInit: Boolean,
//    private val initialState: T,
//    private val global: GlobalMachine<T> = GlobalMachine(callObserveOnInit, initialState),
//    setUpViewMachine: Machine<T>.() -> Unit
//) : Machine<T> by global.createTextMachine() {
//    init {
//        val global = GlobalMachine(callObserveOnInit, initialState)
//        val viewMachine = global.createTextMachine()
//        viewMachine.setUpViewMachine()
//    }
//}

fun <State> createPresenterMachine(
    callObserveOnInit: Boolean,
    initialState: State,
    setUpViewMachine: Machine<State>.() -> Unit
): Machine<State> {
    val global = GlobalMachine(callObserveOnInit, initialState)
    val presenterMachine = global.createMachine()
    val viewMachine = global.createMachine()
    viewMachine.setUpViewMachine()
    return presenterMachine
}
