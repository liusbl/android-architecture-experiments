package com.arch.experiments.tests.test_007

import io.reactivex.Single

sealed class SomeViewAction : BaseAction<SomeViewState>() {
    data class LoadClicked(val isClicked: Boolean) : SomeViewAction() {
        override fun resolveConflict(
            conflictingAction: BaseAction<SomeViewState>,
            state: SomeViewState
        ): ConflictResolver<SomeViewState> {
            return when (conflictingAction) {
                is LoadClicked -> DropConflictResolver(this, conflictingAction, state)
                is StopClicked -> DropConflictResolver(this, conflictingAction, state)
                is IsProgressVisible -> DropConflictResolver(this, conflictingAction, state)
                is TextUpdated -> DropConflictResolver(this, conflictingAction, state)
                else -> DropConflictResolver(
                    this,
                    conflictingAction,
                    state
                ) // PTR: Sad that you can't have exhaustive Action
            }
        }
    }

    class LoadClickedToLoadClickedResolver(
        currentAction: LoadClicked,
        conflictingAction: LoadClicked,
        currentState: SomeViewState
    ) : ConflictResolver<SomeViewState>(currentAction, conflictingAction, currentState) {
        override fun resolve(stateUpdate: Single<SomeViewState>) {
            if (currentState.isLoading) {
                // TODO
            }
        }
    }

    data class StopClicked(val isClicked: Boolean) : SomeViewAction()
    data class IsProgressVisible(val isVisible: Boolean) : SomeViewAction()
    data class TextUpdated(val text: String) : SomeViewAction()
}

