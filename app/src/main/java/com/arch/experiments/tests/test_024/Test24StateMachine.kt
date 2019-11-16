package com.arch.experiments.tests.test_024

import com.arch.experiments.tests.test_024.lib.Action
import com.arch.experiments.tests.test_024.lib.StateMachineImpl
import com.arch.experiments.tests.test_024.lib.ToggleStateMachine
import com.arch.experiments.tests.test_024.misc.MyPreferences

// TODO how to make it so there is no reaction to initial state?? Or is this correct
class Test24StateMachine(
    private val prefs: MyPreferences,
    private val buttonStateMachine: ToggleStateMachine
) : StateMachineImpl<Test24StateMachine.State>() {
    override fun start() {
//         PTR: Bad api design, easy to confuse with regular "observe" methoid
//        observeToggle({ clickAction }, { prefs.incrementClickCount() })

        buttonStateMachine.observe { prefs.incrementClickCount() }
    }

    data class State(val clickAction: Action)
}