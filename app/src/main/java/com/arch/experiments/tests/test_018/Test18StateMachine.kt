package com.arch.experiments.tests.test_018

import com.arch.experiments.tests.test_018.Test18StateMachine.State
import com.arch.experiments.tests.test_018.misc.MyPreferences

class Test18StateMachine(
    prefs: MyPreferences
) : StateUpdaterImpl<State>() {
    init {
        react({ name }, { prefs.name = name })
    }

    data class State(val name: String = "")
}