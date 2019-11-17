package com.arch.experiments.tests.test_008

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import kotlinx.android.synthetic.main.test_008.*

class Test8Fragment : BaseReducerFragment<Test8Reducer, Test8Fragment.State>(R.layout.test_008) {
    override val defaultState: State = State.DEFAULT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress.handleVisibility { isLoading }
        loginButton.handleClicks { copy(isLoginClicked = it) }
        rememberPasswordSwitch.handleChanges({ copy(autoLogin = it) }, { autoLogin })
        usernameEditText.handleChanges({ copy(username = it) }, { username })
        passwordEditText.handleChanges({ copy(password = it) }, { password })
    }

    data class State(
        val isLoginClicked: Boolean,
        val autoLogin: Boolean,
        val username: String,
        val password: String,
        val isLoading: Boolean
        // error
    ) {
        override fun toString(): String {
            return "$isLoginClicked, $username, $password, $isLoading"
        }

        companion object {
            val DEFAULT = State(false, false, "", "", false)
        }
    }
}