package com.arch.experiments.tests.test_007

data class SomeViewState(
        val loadClicked: Boolean,
        val stopClicked: Boolean,
        val isLoading: Boolean,
        val input: String
) : State