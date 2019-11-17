package com.arch.experiments.tests.test_052.lib_core

interface Machine<State> : Pusher<State>, Observer<State> {
    interface Factory<State> : StateHandler.Factory<State, Machine<State>>
}

interface Pusher<State> : StateProvider<State> {
    fun push(newState: State = state)

    interface Factory<State> : StateHandler.Factory<State, Pusher<State>>
}

interface Observer<State> : StateProvider<State> {
    fun observe(onChanged: (State) -> Unit)

    interface Factory<State> : StateHandler.Factory<State, Observer<State>>
}

interface StateProvider<State> : StateHandler<State> {
    val state: State

    interface Factory<State> : StateHandler.Factory<State, StateProvider<State>>
}

interface StateHandler<State> {
    interface Factory<State, Handler : StateHandler<State>> {
        fun create(initialState: State): Handler
    }
}