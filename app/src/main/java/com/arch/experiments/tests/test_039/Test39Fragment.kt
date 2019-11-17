package com.arch.experiments.tests.test_039

import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.onClick
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_039.Test39ViewModel.State
import com.arch.experiments.tests.test_039.misc.MyPreferences
import kotlinx.android.synthetic.main.test_039.*
import timber.log.Timber

class Test39Fragment : BaseFragment(R.layout.test_039) {
    private lateinit var viewModel: Test39ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = Test39ViewModel(MyPreferences())
        viewModel.observeFullState {
            Timber.e("TESTING state: $it")
        }

        actionsToNothing()
        actionsToEffects()
        actionsToStates()

        statesToNothing()
        statesToEffects()
        statesToStates()

        observe()

        example1_greeting()
    }

    private fun actionsToNothing() {
        viewModel.pushAction(changePreferenceValueInstantlyButton::onClick) {
            copy(changePreferenceValueInstantlyAction = it)
        }

        viewModel.pushAction(changePreferenceValueAsyncButton::onClick) {
            copy(changePreferenceValueAsyncAction = it)
        }
    }

    private fun actionsToEffects() {
        viewModel.pushAction(emitEffectInstantlyButton::onClick) {
            copy(emitEffectInstantlyAction = it)
        }

        viewModel.pushAction(emitEffectAsyncButton::onClick) {
            copy(emitEffectAsyncAction = it)
        }
    }

    private fun actionsToStates() {
        viewModel.pushAction(changeStateInstantlyButton::onClick) {
            copy(changeStateInstantlyAction = it)
        }

        viewModel.pushAction(changeStateAsyncButton::onClick) {
            copy(changeStateAsyncAction = it)
        }
    }

    private fun statesToNothing() {
        viewModel.pushState(changePreferenceValueInstantlyEditText::onTextChanged) {
            copy(changePreferenceValueInstantlyText = it)
        }

        viewModel.pushState(changePreferenceValueAsyncEditText::onTextChanged) {
            copy(changePreferenceValueAsyncText = it)
        }
    }

    private fun statesToEffects() {
        viewModel.pushState(emitEffectInstantlyEditText::onTextChanged) {
            copy(emitEffectInstantlyText = it)
        }

        viewModel.pushState(emitEffectAsyncEditText::onTextChanged) {
            copy(emitEffectAsyncText = it)
        }
    }

    private fun statesToStates() {
        viewModel.pushState(changeStateInstantlyEditText1::onTextChanged) {
            copy(changeStateInstantlyText1 = it)
        }

        viewModel.pushState(changeStateInstantlyEditText2::onTextChanged) {
            copy(changeStateInstantlyText2 = it)
        }

        viewModel.pushState(changeStateAsyncEditText1::onTextChanged) {
            copy(changeStateAsyncText1 = it)
        }

        viewModel.pushState(changeStateAsyncEditText2::onTextChanged) {
            copy(changeStateAsyncText2 = it)
        }

        viewModel.observe(
            State::changeStateInstantlyText1,
            changeStateInstantlyEditText1::setSafeText
        )

        viewModel.observe(
            State::changeStateInstantlyText2,
            changeStateInstantlyEditText2::setSafeText
        )
    }

    private fun observe() {
        viewModel.observe(
            State::changeStateInstantlyButtonColor,
            changeStateInstantlyButton::setBackgroundColor
        )

        viewModel.observe(
            State::changeStateAsyncButtonColor,
            changeStateAsyncButton::setBackgroundColor
        )

        viewModel.observeEffect(
            State::toastEffect, {
                Timber.i("TESTING showing First Toast")
                context?.showToast("First Toast")
            }
        )

        viewModel.observeEffect(
            State::popupEffect, {
                Timber.i("TESTING showing Second Toast")
                context?.showToast("Second Toast")
            }
        )
    }

    private fun example1_greeting() {
        fun animateButton() {
            createGreetingButton.animate()
                .rotation(70f)
                .setDuration(100)
                .setInterpolator(BounceInterpolator())
                .start()
        }

        viewModel.pushState(nameEditText::onTextChanged) { copy(greetName = it) }
        viewModel.pushAction(createGreetingButton::onClick) { copy(greetClickedAction = it) }
        viewModel.observe(State::greeting, greetingTextView::setText)
        viewModel.observe(State::isGreetingLoading, greetingProgressBar::isVisible.setter)
        viewModel.observeEffect(State::animateButtonEffect) { animateButton() }
        viewModel.observeEffect(State::showGreetStartedEffect) { context?.showToast("Success") }
        viewModel.observeEffect(State::showGreetFinishedEffect) { context?.showToast("Finished") }
    }
}