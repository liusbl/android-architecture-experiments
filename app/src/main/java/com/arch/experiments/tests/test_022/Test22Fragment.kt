package com.arch.experiments.tests.test_022

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_022.Test22StateMachine.State
import com.arch.experiments.tests.test_022.lib.GlobalStateMachine
import com.arch.experiments.tests.test_022.lib.StateMachine
import com.arch.experiments.tests.test_022.lib.StateMachineImpl
import kotlinx.android.synthetic.main.test_022.*

class Test22Fragment : BaseFragment(R.layout.test_022) {
    private lateinit var stateMachine: StateMachine<State>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDependencies()

        editText1.onTextChanged { text -> stateMachine.push { copy(text1 = text) } }
        editText2.onTextChanged { text -> stateMachine.push { copy(text2 = text) } }
        stateMachine.observe({ text1 }, { editText1.setSafeText(text1) })
        stateMachine.observe({ text2 }, { editText2.setSafeText(text2) })
    }

    private fun setUpDependencies() {
        stateMachine = StateMachineImpl()
        val presenterStateMachine =
            Test22StateMachine()
        GlobalStateMachine(
            stateMachine,
            presenterStateMachine,
            State("text1", "text2")
        ).start()
    }
}