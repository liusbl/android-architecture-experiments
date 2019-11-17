@file:Suppress("UNREACHABLE_CODE")

package com.arch.experiments.tests.test_040.test_with_sigis

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import kotlinx.android.synthetic.main.test_040.*

class ExampleFragment : BaseFragment(R.layout.test_040) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityStateMachine = StateMachineImpl<ExampleState>()
        val viewStateMachine = StateMachineImpl<ExampleState>()
        val viewModel = ExampleViewModel(MyPreferences())

        GlobalStateMachine(listOf(viewStateMachine, viewModel))

//        activityStateMachine.observeState({ list })

        nameEditText.onTextChanged { text ->
            viewStateMachine.updateState { state -> state.copy(name = text) }
        }

        viewStateMachine.observeState({ state -> state.name }, { state ->
            nameEditText.setSafeText(state.name)
        })
    }
}