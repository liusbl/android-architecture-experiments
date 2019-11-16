package com.arch.experiments.tests.test_013

import com.arch.experiments.tests.test_013.Test13Presenter.*
import com.arch.experiments.tests.test_013.misc.Api
import io.reactivex.Observable

class Test13Presenter(
    private val api: Api
) : BasePresenter<State, UserAction, UiEffect>() {
    override fun getEffects() = listOf(update)

    override fun reduceState(action: UserAction) = when (action) {
        is UserAction.NameUpdated -> currentState.copy(name = action.name)
        is UserAction.UpdateNameClicked -> currentState
    }

    val update: Observable<StateOrEffect> = onUserAction<UserAction.UpdateNameClicked>()
        .flatMap { api.update(currentState.name).toObservable() }
        .map<StateOrEffect> { newName -> currentState.copy(name = newName, isLoading = false) }
        .onErrorResumeNext { error: Throwable ->
            Observable.just(
                currentState.copy(name = "", isLoading = false),
                UiEffect.ShowError(error)
            )
        }

    data class State(val name: String, val isLoading: Boolean) : StateOrEffect

    sealed class UserAction {
        data class NameUpdated(val name: String) : UserAction()

        object UpdateNameClicked : UserAction()
    }

    sealed class UiEffect : StateOrEffect {
        data class ShowError(val error: Throwable) : UiEffect()
    }
}