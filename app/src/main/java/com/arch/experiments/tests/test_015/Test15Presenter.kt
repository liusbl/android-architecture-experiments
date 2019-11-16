package com.arch.experiments.tests.test_015

import com.arch.experiments.tests.test_015.Test15Presenter.*
import com.arch.experiments.tests.test_015.Test15Presenter.UserAction.*
import com.arch.experiments.tests.test_015.misc.Api
import io.reactivex.Observable

class Test15Presenter(
    private val api: Api
) : BasePresenter<State, UserAction, UiEffect>() {
    override fun reduceState(action: UserAction, state: State) = when (action) {
        is NameUpdated -> State.Name(action.name)
        is UpdateNameClicked -> state
        is StopClicked -> State.IsLoading(false)
    }

    override fun getEffects() = listOf(update)

    val update: Observable<StateOrEffect> = onUserAction<UpdateNameClicked>()
        .takeUntil(getUserActionUpdates<StopClicked>())
        .flatMap { api.update(getState<State.Name>().name).toObservable() }
        .flatMap<StateOrEffect> { newName ->
            Observable.just(State.Name(newName), State.IsLoading(false))
        }
        .onErrorResumeNext { error: Throwable ->
            Observable.just(State.Name(""), State.IsLoading(false), UiEffect.ShowError(error))
        }

    // TBD: Is it worth it?
    //  Though it has the upside of not copying every time
    //  and also could force the observer (view) to handle
    sealed class State : StateOrEffect {
        data class Name(val name: String) : State()
        data class IsLoading(val isLoading: Boolean) : State()
    }

    sealed class UserAction {
        data class NameUpdated(val name: String) : UserAction()

        object UpdateNameClicked : UserAction()

        object StopClicked : UserAction()
    }

    sealed class UiEffect : StateOrEffect {
        data class ShowError(val error: Throwable) : UiEffect()
    }
}