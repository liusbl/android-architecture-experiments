package com.arch.experiments.tests.test_020

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment

// PTR: A possible solution for state storage between configuration changes
class Test20Fragment : BaseFragment(R.layout.test_020) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // PTR: Should NOT be created here but rather scoped in the full app scope
        val stateStorage = StateStorage()
//        stateStorage.attach(viewModel, activity)
    }
}