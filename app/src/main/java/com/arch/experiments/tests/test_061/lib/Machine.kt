package com.arch.experiments.tests.test_061.lib

interface Machine<State> : Pusher<State>, Observer<State> {
    interface Factory<State> : StateInteractor.Factory<State, Machine<State>>
}

interface Pusher<State> : StateProvider<State> {
    fun push(newState: State = state)

    interface Factory<State> : StateInteractor.Factory<State, Pusher<State>>
}

interface Observer<State> : StateProvider<State> {
    fun observe(onChanged: (State) -> Unit)

    interface Factory<State> : StateInteractor.Factory<State, Observer<State>>
}

interface StateProvider<State> : StateInteractor<State> {
    val state: State

    interface Factory<State> : StateInteractor.Factory<State, StateProvider<State>>
}

interface StateInteractor<State> {
    interface Factory<State, Handler : StateInteractor<State>> {
        fun create(initialState: State): Handler
    }
}