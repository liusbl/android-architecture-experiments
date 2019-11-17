package com.arch.experiments.tests.test_033

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_033.Test33ViewModel.State
import com.arch.experiments.tests.test_033.misc.Database
import com.arch.experiments.tests.test_033.misc.Greeter
import kotlinx.android.synthetic.main.test_032.*

class Test33Fragment : BaseFragment(R.layout.test_033) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test33ViewModel(Database(), Greeter())
        viewModel.pushAction(State::viewCreatedAction)

        viewModel.pushAction(
            { onClick -> greetButton.setOnClickListener { onClick() } },
            State::greetClickedAction
        )

        viewModel.pushActionWithData({ onClick ->
            mockListItemButton.setOnClickListener { onClick(ListItem(213)) }
        }, State::listItemClickedAction)

        // TODO is this okay or even possible?
        viewModel.combine(nameEditText) { editText ->
            reactToState(State::name, editText::setSafeText)
            pushStateWithData(editText::onTextChanged, { name -> copy(name = name) })
        }

        viewModel.reactToState(State::isLoading, progressBar::isVisible.setter)

        viewModel.reactToState(State::greeting, greetingTextView::setText)

        viewModel.reactToEffectWithItem(State::failureEffect, { cause: Exception? ->
            context?.showToast("Fail, because: $cause")
        })

        viewModel.reactToEffect(State::successEffect, { context?.showToast("Success") })
    }
}