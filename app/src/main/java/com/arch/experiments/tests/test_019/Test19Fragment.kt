package com.arch.experiments.tests.test_019

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_019.lib.StateMachineHandler
import com.arch.experiments.tests.test_019.lib.StateMachineImpl
import kotlinx.android.synthetic.main.test_019.*

class Test19Fragment : BaseFragment(R.layout.test_019) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val stateMachineHandler = StateMachineHandler<Test19StateMachine.State>()
        val viewStateMachine = StateMachineImpl(stateMachineHandler)
        val presenterStateMachine = Test19StateMachine(stateMachineHandler)

        stateMachineHandler.addStateMachine(viewStateMachine)
        stateMachineHandler.addStateMachine(presenterStateMachine)

        nameEditText.onTextChanged { text ->
            viewStateMachine.push { copy(name = text) }
        }
        viewStateMachine.observe({ name }, { nameEditText.setSafeText(name) })
    }
}