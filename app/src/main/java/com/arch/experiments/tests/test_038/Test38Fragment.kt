package com.arch.experiments.tests.test_038

import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.onClick
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_038.Test38ViewModel.State
import kotlinx.android.synthetic.main.test_038.*
import timber.log.Timber

class Test38Fragment : BaseFragment(R.layout.test_038) {
    private lateinit var viewModel: Test38ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        viewModel.reactToEffect(
            State::toastEffect,
            { context?.showToast("Toast") }
        )

        viewModel.reactToEffect(
            State::popupEffect,
            { context?.showToast("Popup or smth") }
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