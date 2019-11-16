package com.arch.experiments.tests.test_015

interface StateChangeObserver<State : Any, UiEffect : Any> {
    fun onStateChange(state: State)

    fun onUiEffect(uiEffect: UiEffect)
}