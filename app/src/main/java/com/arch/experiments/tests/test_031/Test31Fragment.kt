package com.arch.experiments.tests.test_031

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_031.Test31ViewModel.State
import com.arch.experiments.tests.test_031.lib.Action
import com.arch.experiments.tests.test_031.misc.Database
import com.arch.experiments.tests.test_031.misc.Greeter
import kotlinx.android.synthetic.main.test_028.*

// No Actions, they are replaced with quick changed between state.
// Example: data class State(... isButtonClicked = false) // Is flipped to true and back to false

// widgets that only onState (progress bar)
// widgets that onState and push (edit name, switch)
// widget that only push (button)

class Test31Fragment : BaseFragment(R.layout.test_031) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test31ViewModel(Database(), Greeter())
        // TODO Should toggle state instead, and also lifecycle events should be handled differently
        viewModel.push({ copy(viewCreatedAction = Action.Active) })
        viewModel.push(
            greetButton::setOnClickListener,
            { copy(greetClickedAction = Action.Active) })
        viewModel.pushAndReact(
            nameEditText::onTextChanged,
            { name -> copy(name = name) },
            State::name,
            nameEditText::setSafeText
        )
        viewModel.react(State::isLoading, progressBar::isVisible.setter)
        viewModel.react(State::greeting, greetingTextView::setText)

        viewModel.reactEffect(State::failureEffect, { cause: Exception? ->
            context?.showToast("Fail, because: $cause")
        })
        viewModel.react(
            State::successEffect,
            { context?.showToast("Success") }
        )
    }
}