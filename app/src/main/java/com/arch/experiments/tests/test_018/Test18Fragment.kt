package com.arch.experiments.tests.test_018

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_018.misc.MyPreferences
import kotlinx.android.synthetic.main.test_018.*

class Test18Fragment : BaseFragment(R.layout.test_018) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val stateMachine = Test18StateMachine(MyPreferences())
        nameEditText.onTextChanged { text ->
            stateMachine.push { copy(name = text) }
        }
        stateMachine.react({ name }, { nameEditText.setSafeText(name) })
    }
}