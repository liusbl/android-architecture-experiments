package com.arch.experiments.tests.test_030.lib

// How to pass data? Maybe you shouldn't, it should come from state?
sealed class Effect {
    object Active : Effect()
    object Inactive : Effect()
}

// How to pass data? You probably shouldn't, since all state should be udpated
// But what to do with lists?  You could have a "Current" item in the state in that case, maybe
sealed class Action { // Maybe could be named Toggle?
    object Active : Action()
    object Inactive : Action()
}