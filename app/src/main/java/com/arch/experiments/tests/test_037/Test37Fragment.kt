package com.arch.experiments.tests.test_037

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.onClick
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.tests.test_037.Test37ViewModel.State
import kotlinx.android.synthetic.main.test_037.*
import timber.log.Timber

class Test37Fragment : BaseFragment(R.layout.test_037) {
    private lateinit var viewModel: Test37ViewModel

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
    }

    private fun actionsToNothing() {
        viewModel.pushAction(updateClickCountInstantlyButton::onClick) {
            copy(changePreferenceValueInstantlyAction = it)
        }

        viewModel.pushAction(updateClickCountInstantlyButton::onClick) {
            copy(changePreferenceValueAsyncAction = it)
        }
    }

    private fun actionsToEffects() {
        viewModel.pushAction(showToastInstantlyButton::onClick) {
            copy(emitEffectInstantlyAction = it)
        }

        viewModel.pushAction(emitTwoEffectsInstantlyButton::onClick) {
            copy(emitTwoEffectsInstantlyAction = it)
        }

        viewModel.pushAction(showToastAsyncButton::onClick) {
            copy(emitEffectAsyncAction = it)
        }

        viewModel.pushAction(emitTwoEffectsAsyncButton::onClick) {
            copy(emitTwoEffectsAsyncAction = it)
        }
    }

    private fun actionsToStates() {
        viewModel.pushAction(changeStateInstantlyButton::onClick) {
            copy(changeStateInstantlyAction = it)
        }

        viewModel.pushAction(changeStateAsyncButton::onClick) {
            copy(changeStateAsyncAction = it)
        }

        viewModel.pushAction(changeTwoStatesAsyncButton::onClick) {
            copy(changeTwoStatesAsyncAction = it)
        }
    }

    private fun statesToNothing() {
        viewModel.pushState(incrementLetterCountInstantlyEditText::onTextChanged) {
            copy(changePreferenceValueInstantlyText = it)
        }

        viewModel.pushState(incrementLetterCountAsyncEditText::onTextChanged) {
            copy(changePreferenceValueAsyncText = it)
        }
    }

    private fun statesToEffects() {
        viewModel.pushState(emitEffectInstantlyEditText::onTextChanged) {
            copy(emitEffectInstantlyText = it)
        }

        viewModel.pushState(emitTwoEffectsInstantlyEditText::onTextChanged) {
            copy(emitTwoEffectsInstantlyText = it)
        }

        viewModel.pushState(emitEffectAsyncEditText::onTextChanged) {
            copy(emitEffectAsyncText = it)
        }

        viewModel.pushState(emitTwoEffectsAsyncEditText::onTextChanged) {
            copy(emitTwoEffectsAsyncText = it)
        }
    }

    private fun statesToStates() {
        viewModel.pushState(editText1::onTextChanged) {
            copy(changeStateInstantlyText1 = it)
        }

        viewModel.pushState(changeStateInstantlyEditText2::onTextChanged) {
            copy(changeStateInstantlyText2 = it)
        }

        viewModel.observe(
            State::changeStateInstantlyText1,
            editText1::setSafeText
        )

        viewModel.observe(
            State::changeStateInstantlyText2,
            changeStateInstantlyEditText2::setSafeText
        )


//
//        viewModel.pushState(changeStateAsyncEditText1::onTextChanged) {
//            copy(changeStateAsyncText1 = it)
//        }
//
//        viewModel.pushState(changeStateAsyncEditText2::onTextChanged) {
//            copy(changeStateAsyncText2 = it)
//        }
//
//        viewModel.pushState(changeTwoStatesAsyncEditText1::onTextChanged) {
//            copy(changeTwoStatesAsyncText1 = it)
//        }
//
//        viewModel.pushState(changeTwoStatesAsyncEditText2::onTextChanged) {
//            copy(changeTwoStatesAsyncText2 = it)
//        }
    }

    private fun react() {
//        viewModel.onState(
//            State::changeStateInstantlyButtonColor,
//            changeStateInstantlyButton::setBackgroundColor
//        )
//
//        viewModel.onState(
//            State::changeStateAsyncButtonColor,
//            changeStateAsyncButton::setBackgroundColor
//        )
//
//        viewModel.onState(
//            State::changeTwoStatesAsyncButtonColor,
//            changeTwoStatesAsyncButton::setBackgroundColor
//        )
//
//        viewModel.reactToEffect(
//            State::toastEffect,
//            { Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show() }
//        )

        viewModel.reactToEffect(
            State::popupEffect,
            { context?.showToast("Popup or smth") }
        )
    }
}