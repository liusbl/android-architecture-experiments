package com.arch.experiments.tests.test_070

import androidx.lifecycle.Observer

class StateObserver<State, Value>(
    private val getStateValue: (State) -> Value,
    private val setValue: (Value) -> Unit
) : Observer<State> {
    private var previous: Value? = null

    override fun onChanged(item: State) {
        val value = getStateValue(item)
        if (previous != value) {
            setValue(value)
            previous = value
        }
    }
}