package com.arch.experiments.tests.test_048.lib_android

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.arch.experiments.common.widgets.AppEditText
import com.arch.experiments.tests.test_048.lib_core.GlobalMachine
import com.arch.experiments.tests.test_048.lib_core.Machine

//TODO consider better method of state machine creation
fun createToastMachine(
    context: Context,
    initialState: String = ""
) = createPresenterEventMachine(initialState) {
    observe { Toast.makeText(context, value, Toast.LENGTH_SHORT).show() }
}

fun View.createClickMachine() = createPresenterEventMachine(Unit) {
    this@createClickMachine.setOnClickListener { push() }
}

fun TextView.createTextPusher(initialState: String) =
    createPresenterStateMachine(initialState) {
        observe { this@createTextPusher.text = value }
    }

fun AppEditText.createTextMachine(initialState: String): Machine<String> =
    createPresenterStateMachine(initialState) {
        val editText = this@createTextMachine
        observe { editText.setSafeText(value) }
        editText.onTextChanged { text -> push(text) }
    }

fun View.createVisibilityMachine(isVisible: Boolean) = createPresenterStateMachine(
    initialState = false,
    setUpViewMachine = {
        this@createVisibilityMachine.isVisible = isVisible
        observe { this@createVisibilityMachine.isVisible = value }
    })

// TODO
fun <T> createUnsafeMachine(
    initialState: T,
    update: (T) -> Unit,
    onChanged: ((T) -> Unit) -> Unit
): Machine<T> =
    createPresenterStateMachine(initialState) {
        // TODO
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

// TODO figure out how to not need initial state and then use this
//fun <State> createEventPusher(onObserve: (State) -> Unit): Pusher<State> {
//    val global = GlobalMachine(false, initialState) // TODO You shouldn't need to pass initial state here.....
//    val presenterMachine = global.createMachine()
//    val viewMachine = global.createMachine()
//    viewMachine.setUpViewMachine()
//    return presenterMachine
//}

fun <State> createPresenterEventMachine(
    initialState: State, // TODO You shouldn't need to pass initial state here.....
    setUpViewMachine: Machine<State>.() -> Unit
): Machine<State> {
    // TODO Fix!!!!! So that you didn't have to pass initial state
    val global = GlobalMachine(false, initialState)
    val presenterMachine = global.createMachine()
    val viewMachine = global.createMachine()
    viewMachine.setUpViewMachine()
    return presenterMachine
}

fun <State> createPresenterStateMachine(
    initialState: State,
    setUpViewMachine: Machine<State>.() -> Unit
): Machine<State> {
    val global = GlobalMachine(true, initialState)
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
