package com.arch.experiments.tests.test_029

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_029.Test29ViewModel.Action
import com.arch.experiments.tests.test_029.Test29ViewModel.State
import com.arch.experiments.tests.test_029.misc.Database
import com.arch.experiments.tests.test_029.misc.Greeter
import kotlinx.android.synthetic.main.test_028.*

class Test29Fragment : BaseFragment(R.layout.test_028) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test29ViewModel(Database(), Greeter())
        viewModel.postAction(Action.ViewCreated)
        greetButton.setOnClickListener { viewModel.postAction(Action.GreetClicked) }
        nameEditText.onTextChanged { viewModel.postStateUpdate(Action.NameUpdated(it)) } // But now view descides if update post state update or post action
        viewModel.observe(State::isLoading, { isLoading -> progressBar.isVisible = isLoading })
        viewModel.observe(State::name, nameEditText::setSafeText)
        viewModel.observe(State::greeting, greetingTextView::setText)
    }
}