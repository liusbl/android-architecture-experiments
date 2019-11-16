package com.arch.experiments.tests.test_023

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_023.Test23StateMachine.State
import com.arch.experiments.tests.test_023.lib.GlobalStateMachine
import com.arch.experiments.tests.test_023.lib.StateMachine
import com.arch.experiments.tests.test_023.lib.StateMachineImpl
import kotlinx.android.synthetic.main.test_023.*

class Test23Fragment : BaseFragment(R.layout.test_023) {
    private lateinit var stateMachine: StateMachine<State>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpDependencies()

        passwordEditText.onTextChanged { text -> stateMachine.push { copy(password = text) } }
        stateMachine.observe({ password }, { passwordEditText.setSafeText(password) })

        stateMachine.observe({ isPasswordValid }, { setPasswordColor() })
    }

    private fun State.setPasswordColor() {
        if (isPasswordValid) {
            val anim = android.animation.ObjectAnimator.ofInt(
                passwordEditText, "textColor",
                android.graphics.Color.RED, android.graphics.Color.GREEN
            )
            anim.setEvaluator(android.animation.ArgbEvaluator())
            anim.duration = 200
            anim.start()
        } else {
            val anim = android.animation.ObjectAnimator.ofInt(
                passwordEditText, "textColor",
                android.graphics.Color.GREEN, android.graphics.Color.RED
            )
            anim.setEvaluator(android.animation.ArgbEvaluator())
            anim.duration = 200
            anim.start()
        }
    }

    private fun setUpDependencies() {
        stateMachine = StateMachineImpl()
        val presenterStateMachine = Test23StateMachine()
        GlobalStateMachine(
            stateMachine,
            presenterStateMachine,
            // TBD: It is okay if initial state is passed here? Feels like that's not correct
            State("", false)
        ).start()
    }
}