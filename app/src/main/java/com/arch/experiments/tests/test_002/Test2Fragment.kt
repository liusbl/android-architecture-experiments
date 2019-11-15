package com.arch.experiments.tests.test_002

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.addTo
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_002.Test2Reducer.Action
import com.arch.experiments.tests.test_002.Test2Reducer.Effect
import com.arch.experiments.tests.test_002.misc.LoginUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.test_002.*

class Test2Fragment : BaseFragment(R.layout.test_002) {
    private val reducer by lazy { Test2Reducer(LoginUseCase()) }
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleUserActions()
        handleStateChanges()
        handleEffects()
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
    }

    private fun handleStateChanges() {
        reducer.states
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                usernameEditText.setSafeText(state.username)
                passwordEditText.setSafeText(state.password)
                progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            }.addTo(disposables)
    }

    private fun handleEffects() {
        reducer.effects
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { effect ->
                when (effect) {
                    is Effect.LoginStarted -> context?.showToast("Started")
                    is Effect.LoginFinished -> context?.showToast("Finished")
                }
            }.addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.clear()
        reducer.clear()
        super.onDestroyView()
    }
}