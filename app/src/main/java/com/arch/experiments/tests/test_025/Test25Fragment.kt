package com.arch.experiments.tests.test_025

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_025.Test25StateMachine.State
import com.arch.experiments.tests.test_025.lib.Action
import com.arch.experiments.tests.test_025.lib.GlobalStateMachine
import com.arch.experiments.tests.test_025.lib.StateMachine
import com.arch.experiments.tests.test_025.lib.StateMachineImpl
import kotlinx.android.synthetic.main.test_025.*

class Test25Fragment : BaseFragment(R.layout.test_025) {
    private lateinit var stateMachine: StateMachine<State>

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDependencies()

        button.setOnClickListener { stateMachine.toggle { copy(clickAction = it) } }
        stateMachine.observe(
            { clickCount },
            { countTextView.text = "How many times clicked: $clickCount" }
        )
    }

    private fun setUpDependencies() {
        stateMachine = StateMachineImpl()
        val presenterStateMachine = Test25StateMachine()
        GlobalStateMachine(
            stateMachine,
            presenterStateMachine,
            // TBD: It is okay if initial state is passed here? Feels like that's not correct
            State(0, Action.INACTIVE)
        ).start()
    }
}