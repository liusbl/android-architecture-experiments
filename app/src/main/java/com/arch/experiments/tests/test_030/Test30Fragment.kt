package com.arch.experiments.tests.test_030

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_030.Test30ViewModel.State
import com.arch.experiments.tests.test_030.lib.Action
import com.arch.experiments.tests.test_030.misc.Database
import com.arch.experiments.tests.test_030.misc.Greeter
import kotlinx.android.synthetic.main.test_028.*

// No Actions, they are replaced with quick changed between state.
// Example: data class State(... isButtonClicked = false) // Is flipped to true and back to false

// widgets that only onState (progress bar)
// widgets that onState and push (edit name, switch)
// widget that only push (button)

class Test30Fragment : BaseFragment(R.layout.test_030) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test30ViewModel(Database(), Greeter())
        // TODO Should toggle state instead, and also lifecycle events should be handled differently
        viewModel.push({ copy(viewCreated = Action.Active) })
        viewModel.push(greetButton::setOnClickListener, { copy(greetClicked = Action.Active) })
        viewModel.pushAndReact(
            nameEditText::onTextChanged,
            { name -> copy(name = name) },
            State::name,
            nameEditText::setSafeText
        )
        viewModel.react(State::isLoading, progressBar::isVisible.setter)
        viewModel.react(State::greeting, greetingTextView::setText)

        // PTR: There is a problem that if we first onState to isFailureShowing instead of failureException, t
        //  then there will be no failureEffect exception, even though they are linked :(
        var failure: Exception? = null
        viewModel.react(State::failureException, { failure = it })
        viewModel.react(
            State::isFailureShowing,
            {
                // TBD: should you pass exception to effect OR set it in separate "onState" block?
                context?.showToast("Fail, because: $failure")
            }
        )
        viewModel.react(
            State::isSuccessShowing,
            { context?.showToast("Success") }
        )
    }
}