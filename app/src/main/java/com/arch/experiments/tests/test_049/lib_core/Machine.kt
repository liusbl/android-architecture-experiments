package com.arch.experiments.tests.test_049.lib_core

interface Machine<State> : Pusher<State>, Observer<State> {
    interface Factory<State> {
        fun create(initialState: State): Machine<State>
    }
}

interface Pusher<State> : StateProvider<State> {
    fun push(newState: State = state)

    interface Factory<State> {
        fun create(initialState: State): Pusher<State>
    }
}

interface Observer<State> : StateProvider<State> {
    fun observe(onChanged: (State) -> Unit)

    interface Factory<State> {
        fun create(initialState: State): Observer<State>
    }
}

interface StateProvider<State> {
    val state: State

    interface Factory<State> {
        fun create(initialState: State): StateProvider<State>
    }
}