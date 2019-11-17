package com.arch.experiments.tests.test_031.lib


sealed class ToggleEffect : Effect<Unit> {
    object Active : ToggleEffect() {
        override fun getValue() = Unit
    }

    object Inactive : ToggleEffect() {
        override fun getValue() = Unit
    }
}

// PTR:
//  How to pass data? You probably shouldn't, since all state should be udpated
//  But what to do with lists?  You could have a "Current" item in the state in that case, maybe
sealed class Action { // Maybe could be named Toggle?
    object Active : Action()
    object Inactive : Action()
}

