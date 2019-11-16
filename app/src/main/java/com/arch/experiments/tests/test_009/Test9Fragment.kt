package com.arch.experiments.tests.test_009

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_009.misc.LoginUseCase

class Test9Fragment : BaseFragment(R.layout.test_009) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val reducer = Test9Reducer(LoginUseCase())
    }

    data class State(
        val isLoginClicked: Boolean,
        val autoLogin: Boolean,
        val username: String,
        val password: String,
        val isLoading: Boolean
        // error
    )
}