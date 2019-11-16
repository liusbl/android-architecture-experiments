package com.arch.experiments.tests.test_024

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_024.Test24StateMachine.State
import com.arch.experiments.tests.test_024.lib.*
import com.arch.experiments.tests.test_024.misc.MyPreferences
import kotlinx.android.synthetic.main.test_024.*

class Test24Fragment : BaseFragment(R.layout.test_024) {
    private lateinit var stateMachine: StateMachine<State>

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonStateMachine = ToggleStateMachine()
        setUpDependencies(buttonStateMachine)

        button.setOnClickListener { buttonStateMachine.toggle() }
    }

    private fun setUpDependencies(buttonStateMachine: ToggleStateMachine) {
        stateMachine = StateMachineImpl()
        val presenterStateMachine = Test24StateMachine(
            MyPreferences(context!!),
            buttonStateMachine
        )
        GlobalStateMachine(
            stateMachine,
            presenterStateMachine,
            // TBD: It is okay if initial state is passed here? Feels like that's not correct
            State(Action.INACTIVE)
        ).start()
    }
}