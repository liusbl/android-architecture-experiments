package com.arch.experiments.tests.test_012

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.addTo
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_012.Test12Presenter.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.test_010.*

class Test12Fragment : BaseFragment(R.layout.test_011) {
    private val disposables = CompositeDisposable()
    lateinit var presenter: Test12Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleUserAction()
        handleStateUpdates()
        handleUiEffects()
    }

    private fun handleUserAction() {
        nameEditText.onTextChanged { name ->
            presenter.dispatch(UserAction.NameUpdated(name))
        }
        updateNameButton.setOnClickListener {
            presenter.dispatch(UserAction.UpdateNameClicked)
        }
    }

    private fun handleStateUpdates() {
        presenter.stateUpdates
            .distinctUntilChanged(State::name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                nameEditText.setText(state.name)
            }
            .addTo(disposables)

        presenter.stateUpdates
            .distinctUntilChanged(State::isLoading)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                //                    progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
            }
            .addTo(disposables)
    }

    private fun handleUiEffects() {
        presenter.uiEffects
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { effect ->
                when (effect) {
                    is UiEffect.ShowError -> context?.showToast(effect.error.message)
                }
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        presenter.dispose()
        super.onDestroyView()
    }
}