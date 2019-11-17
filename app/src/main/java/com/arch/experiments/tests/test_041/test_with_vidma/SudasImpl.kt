package com.arch.experiments.tests.test_041.test_with_vidma

class SudasImpl<State>(val initialState: State) :
    Sudas<State> {
    var state = initialState

    override fun pushState(updateState: State.() -> State) {
        state = state.updateState()
//        state.publish()
    }

    override fun observe(trigger: State.() -> Any, react: State.() -> Unit) {

    }
}