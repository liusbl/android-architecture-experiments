package com.arch.experiments.tests.test_029

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_029.misc.Database
import com.arch.experiments.tests.test_029.misc.Greeter
import kotlinx.android.synthetic.main.test_028.*

// No Actions, they are replaced with quick changed between state.
// Example: data class State(... isButtonClicked = false) // Is flipped to true and back to false

// widgets that only onState (progress bar)
// widgets that onState and push (edit name, switch)
// widget that only push (button)

class Test29Fragment : BaseFragment(R.layout.test_029) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test29ViewModel(Database(), Greeter())
        // TODO Should toggle state instead, and also lifecycle events should be handled differently
        viewModel.push({ copy(isViewCreated = true) })

        // TODO Should toggle state instead
        viewModel.push(greetButton::setOnClickListener, { copy(isGreetClicked = true) })

        viewModel.pushAndReact(
            nameEditText::onTextChanged,
            { value -> copy(name = value) },
            Test29ViewModel.State::name,
            nameEditText::setSafeText
        ) // TBD: BLOAT?
        viewModel.react(Test29ViewModel.State::isLoading, { isLoading -> progressBar.isVisible = isLoading })
        viewModel.react(Test29ViewModel.State::greeting, greetingTextView::setText)
    }
}