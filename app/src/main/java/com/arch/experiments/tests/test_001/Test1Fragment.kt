package com.arch.experiments.tests.test_001

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.addTo
import com.arch.experiments.tests.test_001.Test1Reducer.Action
import com.arch.experiments.tests.test_001.misc.LoginUseCase
import com.arch.experiments.tests.test_001.misc.MyPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.test_001.*

class Test1Fragment : BaseFragment(R.layout.test_001) {
    private val reducer by lazy { Test1Reducer(LoginUseCase(), MyPreferences()) }
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleUserActions()
        handleStateChanges()
    }

    private fun handleUserActions() {
        loginButton.setOnClickListener {
            reducer.dispatch(Action.LoginClicked)
        }
        usernameEditText.onTextChanged { text ->
            reducer.dispatch(Action.UsernameEntered(text))
        }
        passwordEditText.onTextChanged { text ->
            reducer.dispatch(Action.PasswordEntered(text))
        }
        rememberUsernameCheckbox.setOnClickListener {
            // PTR: Hack to not need custom checkbox implementation
            reducer.dispatch(Action.RememberUsernameChecked(rememberUsernameCheckbox.isChecked))
        }
    }

    private fun handleStateChanges() {
        reducer.states
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                usernameEditText.setSafeText(state.username)
                passwordEditText.setSafeText(state.password)
                rememberUsernameCheckbox.isChecked = state.rememberUsername
                progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                val toastState = state.toastErrorState
                if (toastState.isShowing) {
                    Toast.makeText(context, toastState.throwable?.message, Toast.LENGTH_SHORT).show()
                }
            }.addTo(disposables)
    }

    override fun onDestroyView() {
        reducer.clear()
        super.onDestroyView()
    }
}