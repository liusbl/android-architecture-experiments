package com.arch.experiments.tests.test_074.lib

class AndroidTransformation<State> : ConfigTransformation<State> {
    private var observedValue: State? = null

    override fun transformObserve(state: State, observe: (State) -> Unit) {
        observedValue = state
        observe(state)
    }

    override fun transformOnPush(state: State, push: (State) -> Unit) {
        if (observedValue == null) {
            push(state)
        }
        observedValue = null
    }
}