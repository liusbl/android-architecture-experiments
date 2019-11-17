package com.arch.experiments.tests.test_040.test_with_sigis

class ExampleViewModel(private val prefs: MyPreferences): StateMachineImpl<ExampleState>() {
    init {
        updateState { state -> state.copy(name = prefs.getName()) }

        observeState({ state -> state.name }, { state ->
            prefs.setName(state.name)
        })
    }
}