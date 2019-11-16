package com.arch.experiments.tests.test_014

import com.arch.experiments.tests.test_014.Test14Presenter.*
import com.arch.experiments.tests.test_014.Test14Presenter.UserAction.*
import com.arch.experiments.tests.test_014.misc.Api
import io.reactivex.Observable

class Test14Presenter(
    private val api: Api
) : BasePresenter<State, UserAction, UiEffect>() {
    override fun getEffects() = listOf(update)

    override fun reduceState(action: UserAction) = when (action) {
        is NameUpdated -> currentState.copy(name = action.name)
        is UpdateNameClicked -> currentState
        is StopClicked -> currentState.copy(isLoading = false)
    }

    val update: Observable<StateOrEffect> = onUserAction<UpdateNameClicked>()
        .takeUntil(getUserActionUpdates<StopClicked>())
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

        object StopClicked : UserAction()
    }

    sealed class UiEffect : StateOrEffect {
        data class ShowError(val error: Throwable) : UiEffect()
    }
}