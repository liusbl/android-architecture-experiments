package com.arch.experiments.tests.test_028

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_028.Test28ViewModel.*
import com.arch.experiments.tests.test_028.misc.Database
import com.arch.experiments.tests.test_028.misc.Greeter
import kotlinx.android.synthetic.main.test_028.*

class Test28Fragment : BaseFragment(R.layout.test_028) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test28ViewModel(Database(), Greeter())
        viewModel.postAction(Action.ViewCreated)

        greetButton.setOnClickListener { viewModel.postAction(Action.GreetClicked) }

        nameEditText.onTextChanged {
            viewModel.postStateUpdate(StateUpdate.NameUpdated(it))
        }

        viewModel.observe({ isLoading }, { isLoading -> progressBar.isVisible = isLoading })
        viewModel.observe(State::name, nameEditText::setSafeText)
        viewModel.observe(State::greeting, greetingTextView::setText)
    }
}