package com.arch.experiments.tests.test_034

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_034.Test34ViewModel.State
import com.arch.experiments.tests.test_034.misc.Database
import com.arch.experiments.tests.test_034.misc.Greeter
import kotlinx.android.synthetic.main.test_032.*

class Test34Fragment : BaseFragment(R.layout.test_033) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test34ViewModel(Database(), Greeter())
        // TODO don't forget to internally NOT renotify the "onState" part
        viewModel.pushState(nameEditText::onTextChanged, { name -> copy(name = name) })

        viewModel.pushActions(State::viewCreatedAction)

        viewModel.pushActions(
            { onClick -> greetButton.setOnClickListener { onClick(Unit) } },
            State::greetClickedAction
        )

        viewModel.pushActions(
            { onClick -> mockListItemButton.setOnClickListener { onClick(ListItem(213)) } },
            State::listItemClickedAction
        )

        viewModel.observeState(State::name, nameEditText::setSafeText)

        viewModel.observeState(State::isLoading, progressBar::isVisible.setter)

        viewModel.observeState(State::greeting, greetingTextView::setText)

        viewModel.observeEffect(State::failureEffect, { effect: Exception? ->
            context?.showToast("Fail, because: $effect")
        })

        viewModel.observeEffect(State::successEffect, { context?.showToast("Success") })
    }
}