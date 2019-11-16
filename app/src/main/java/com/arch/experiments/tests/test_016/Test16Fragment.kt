package com.arch.experiments.tests.test_016

import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_016.misc.Greeter
import kotlinx.android.synthetic.main.test_016.*

class Test16Fragment : BaseFragment(R.layout.test_016) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = Test16ViewModel(Greeter())
        nameEditText.onTextChanged { text -> viewModel.pushState { copy(greetName = text) } }
        createGreetingButton.setOnClickListener { viewModel.pushAction { copy(greetClickedAction = it) } }
        viewModel.observe({ greeting }, greetingTextView::setText)
        viewModel.observe({ isGreetingLoading }, { isLoading -> greetingProgressBar.isVisible = isLoading })
        viewModel.observeEffect({ animateButtonEffect }) { animateButton() }
        viewModel.observeEffect({ showGreetStartedEffect }) { context?.showToast("Started creating greeting") }
        viewModel.observeEffect({ showGreetFinishedEffect }) { context?.showToast("Finished") }
    }

    private fun animateButton() {
        createGreetingButton.animate()
            .rotation(360f)
            .setDuration(300)
            .setInterpolator(BounceInterpolator())
            .start()
    }
}