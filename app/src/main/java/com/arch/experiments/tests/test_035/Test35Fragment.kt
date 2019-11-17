package com.arch.experiments.tests.test_035

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_035.Test35ViewModel.State
import com.arch.experiments.tests.test_035.misc.Database
import com.arch.experiments.tests.test_035.misc.Greeter
import kotlinx.android.synthetic.main.test_032.*

class Test35Fragment : BaseFragment(R.layout.test_035) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test35ViewModel(Database(), Greeter())

        // TODO don't forget to internally NOT renotify the "onState" part of the view
        //  Also, what if viewmodel needs to onState to these name changes?
        viewModel.pushState(nameEditText::onTextChanged, { name -> copy(name = name) })

        viewModel.pushAction(State::viewCreatedAction)

        viewModel.pushAction(
            { onClick -> greetButton.setOnClickListener { onClick(Unit) } },
            State::greetClickedAction
        )

        viewModel.pushAction(
            { onClick -> mockListItemButton.setOnClickListener { onClick(ListItem(213)) } },
            State::listItemClickedAction
        )

        viewModel.observeState(State::name, nameEditText::setSafeText)

        viewModel.observeState(State::isLoading, progressBar::isVisible.setter)

        viewModel.observeState(State::greeting, greetingTextView::setText)

        viewModel.observeEffect(State::failureEffect, { exception: Exception? ->
            context?.showToast("Fail, because: $exception")
        })

        viewModel.observeEffect(State::successEffect, { context?.showToast("Success") })
    }
}