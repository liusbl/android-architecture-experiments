package com.arch.experiments.tests.test_015

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_015.Test15Presenter.*
import kotlinx.android.synthetic.main.test_010.*

class Test15Fragment : BaseFragment(R.layout.test_015), StateChangeObserver<State, UiEffect> {
    lateinit var presenter: Test15Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleUserAction()
    }

    private fun handleUserAction() {
        nameEditText.onTextChanged { name ->
            presenter.dispatch(UserAction.NameUpdated(name))
        }
        updateNameButton.setOnClickListener {
            presenter.dispatch(UserAction.UpdateNameClicked)
        }
    }

    override fun onStateChange(state: State) {
        when (state) {
            is State.Name -> {
                nameEditText.setText(state.name)
            }
            is State.IsLoading -> {
                progressBar.isVisible = state.isLoading
            }
        }
    }

    override fun onUiEffect(uiEffect: UiEffect) {
        when (uiEffect) {
            is UiEffect.ShowError -> {
                context?.showToast(uiEffect.error.message)
            }
        }
    }

    override fun onDestroyView() {
        presenter.dispose()
        super.onDestroyView()
    }
}