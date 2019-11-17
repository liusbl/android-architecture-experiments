package com.arch.experiments.tests.test_036

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.onClick
import com.arch.experiments.tests.test_036.Test36ViewModel.State
import kotlinx.android.synthetic.main.test_036.*

class Test36Fragment : BaseFragment(R.layout.test_036) {
    private lateinit var viewModel: Test36ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionsToNothing()
        actionsToEffects()
        actionsToStates()
        statesToNothing()
        statesToEffects()
        statesToStates()
    }

    private fun actionsToNothing() {
        viewModel.pushAction(
            updateClickCountInstantlyButton::onClick,
            State::changePreferenceValueInstantlyAction
        )

        viewModel.pushAction(
            updateClickCountInstantlyButton::onClick,
            State::changePreferenceValueAsyncAction
        )
    }

    private fun actionsToEffects() {
        viewModel.pushAction(showToastInstantlyButton::onClick, State::emitEffectInstantlyAction)

        viewModel.pushAction(
            emitTwoEffectsInstantlyButton::onClick,
            State::emitTwoEffectsInstantlyAction
        )

        viewModel.pushAction(showToastAsyncButton::onClick, State::emitEffectAsyncAction)

        viewModel.pushAction(emitTwoEffectsAsyncButton::onClick, State::emitTwoEffectsAsyncAction)
    }

    private fun actionsToStates() {
        viewModel.pushAction(changeStateInstantlyButton::onClick, State::changeStateInstantlyAction)

        viewModel.pushAction(changeStateAsyncButton::onClick, State::changeStateAsyncAction)

        viewModel.pushAction(changeTwoStatesAsyncButton::onClick, State::changeTwoStatesAsyncAction)
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
//        viewModel.pushState(changeStateInstantlyEditText::onTextChanged) {
//            copy(changeStateInstantlyText = it)
//        }

//        viewModel.pushState(changeStateAsyncEditText::onTextChanged) {
//            copy(changeStateAsyncText = it)
//        }

//        viewModel.pushState(changeTwoStatesAsyncEditText::onTextChanged) {
//            copy(changeTwoStatesAsyncText = it)
//        }
    }
}