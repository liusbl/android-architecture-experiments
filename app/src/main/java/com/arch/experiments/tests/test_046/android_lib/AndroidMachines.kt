package com.arch.experiments.tests.test_046.android_lib

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.arch.experiments.common.widgets.AppEditText
import com.arch.experiments.tests.test_046.core_lib.GlobalMachine
import com.arch.experiments.tests.test_046.core_lib.Machine

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

fun TextView.createTextMachine(initialState: String) =
    createPresenterMachine(true, initialState) { observe { this@createTextMachine.text = value } }

fun AppEditText.createTextMachine(initialState: String): Machine<String> =
    createPresenterMachine(true, initialState) {
        val editText = this@createTextMachine
        observe { editText.setSafeText(value) }
        editText.onTextChanged { text -> push(text) }
    }

fun View.createVisibilityMachine(isVisible: Boolean) = createPresenterMachine(
    callObserveOnInit = true,
    initialState = false,
    setUpViewMachine = {
        this@createVisibilityMachine.isVisible = value
        observe { this@createVisibilityMachine.isVisible = value }
    })

fun <T> createUnsafeMachine(
    initialState: T,
    update: (T) -> Unit,
    onChanged: ((T) -> Unit) -> Unit
): Machine<T> =
    createPresenterMachine(
        true,
        initialState
    ) {
        var latest: T = initialState
        observe {
            latest = value
            update(value)
        }

        onChanged { data: T ->
            if (data != latest) {
                push(data)
            }
        }
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

class ViewModelMachineFactory(lifecycle: LifecycleOwner) {
//    val a = createViewModelMachine()
}

fun <State> createViewModelMachine(
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
