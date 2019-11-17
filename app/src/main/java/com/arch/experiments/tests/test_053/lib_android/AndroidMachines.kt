package com.arch.experiments.tests.test_053.lib_android

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.common.widgets.AppEditText
import com.arch.experiments.tests.test_053.lib_core.Observer
import com.arch.experiments.tests.test_053.lib_core.Pusher

// TODO: Figure out general name for Pusher, Observer and Machine

fun TextView.createTextPusher(initialState: String): Pusher<String> =
    createTextPusherFactory().create(initialState)

fun TextView.createTextPusherFactory(): Pusher.Factory<String> =
    StateHandlerFactory<String>(onObserve = ::setText, isEvent = false).createPusherFactory()

fun View.createVisibilityPusher(initialState: Boolean) =
    StateHandlerFactory(onObserve = ::isVisible.setter, isEvent = false)
        .createPusher(initialState)

fun AppEditText.getFactory() = StateHandlerFactory<String>(
    onObserve = { setSafeText(it) },
    onPush = { push -> onTextChanged { text -> push(text) } },
    isEvent = false
)

fun AppEditText.createTextMachineFactory() =
    getFactory().createMachineFactory()

fun AppEditText.createTextMachine(initialState: String) =
    createTextMachineFactory().create(initialState)

fun View.createClickObserver(): Observer<Unit> {
    return StateHandlerFactory<Unit>(
        onPush = { onClick -> setOnClickListener { onClick(Unit) } },
        isEvent = true
    ).createObserver(Unit)
}

fun Context.createToastPusher(): Pusher<String> =
    StateHandlerFactory<String>(onObserve = ::showToast, isEvent = true).createPusher("")

// TODO
//fun <T> createUnsafeMachine(
//    initialState: T,
//    update: (T) -> Unit,
//    onChanged: ((T) -> Unit) -> Unit
//): Machine<T> =
//    createPresenterStateMachine(initialState) {
//        // TODO
//        var latest: T = initialState
//        observe {
//            latest = state
//            update(state)
//        }
//
//        onChanged { data: T ->
//            if (data != latest) {
//                push(data)
//            }
//        }
//    }

// TODO figure out how to not need initial state and then use this
//fun <State> createEventPusher(onObserve: (State) -> Unit): Pusher<State> {
//    val global = GlobalMachine(false, initialState) // TODO You shouldn't need to pass initial state here.....
//    val presenterMachine = global.createMachine()
//    val viewMachine = global.createMachine()
//    viewMachine.setUpViewMachine()
//    return presenterMachine
//}