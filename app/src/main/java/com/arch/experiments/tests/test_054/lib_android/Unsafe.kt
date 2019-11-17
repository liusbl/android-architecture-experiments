package com.arch.experiments.tests.test_054.lib_android

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