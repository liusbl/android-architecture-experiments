package com.arch.experiments.tests.test_041.test_with_vidma

class SomeViewModel(
    private val prefs: MockPrefs,
    private val sudas: Sudas<SomeViewState>
) {
    init {
        sudas.pushState {
            copy(name = prefs.getName())
        }

        sudas.observe({ name }, {

            //            emitState(copy(secondName = name))
            prefs.setName(name)
        })
    }
}