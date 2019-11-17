package com.arch.experiments.tests.test_038.lib


// TBD: Perhaps Toggle is not the best name? Maybe "Instant"?

class EmptyEffect<T> : Effect<T> {
    override fun getValue() = null
}

class EmptyAction<T> : Action<T> {
    override fun getValue() = null
}

sealed class ToggleEffect : Effect<Unit> {
    object Active : ToggleEffect() {
        override fun getValue() = Unit
    }

    object Inactive : ToggleEffect() {
        override fun getValue() = Unit
    }
}

sealed class ToggleAction : Action<Unit> {
    object Active : ToggleAction() { // Maybbe  could be better nam? Like RUN
        override fun getValue() = Unit
    }

    object Inactive : ToggleAction() { // Maybe could be better name?
        override fun getValue() = Unit
    }
}
