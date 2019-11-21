package com.arch.experiments.tests.test_072.lib

interface Machine<State> : Supplier<State>, Observer<State> {
    interface Factory<State> : StateInteractor.Factory<State, Machine<State>>
}

interface Supplier<State> : StateProvider<State> {
    fun push(newState: State = state)

    interface Factory<State> : StateInteractor.Factory<State, Supplier<State>>
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