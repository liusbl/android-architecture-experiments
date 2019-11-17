package com.arch.experiments.tests.test_049.lib_android

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.common.widgets.AppEditText
import com.arch.experiments.tests.test_049.lib_core.Pusher

fun TextView.createTextPusher(initialState: String): Pusher<String> =
    createTextPusherFactory().create(initialState)

fun TextView.createTextPusherFactory(): Pusher.Factory<String> =
    StateMachineFactory<String>(onObserve = ::setText).createPusherFactory()

fun View.createVisibilityPusher(initialState: Boolean) =
    StateMachineFactory<Boolean>(onObserve = { visible -> isVisible = visible }).createPusher(initialState)

fun AppEditText.createTextMachine(initialState: String) =
    createTextMachineFactory().create(initialState)

fun AppEditText.createTextMachineFactory() =
    StateMachineFactory(onObserve = ::setSafeText, onPush = ::onTextChanged).createMachineFactory()

fun View.createClickObserver() =
    EventMachineFactory<Unit>(onPush = { push -> setOnClickListener { push(Unit) } })
        .createObserver(Unit)

fun Context.createToastPusher() =
    EventMachineFactory<String>(onObserve = ::showToast).createPusher("")


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