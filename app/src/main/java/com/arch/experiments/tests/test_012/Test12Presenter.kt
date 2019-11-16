package com.arch.experiments.tests.test_012

import com.arch.experiments.common.extensions.addTo
import com.arch.experiments.tests.test_012.misc.Api
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Test12Presenter(private val api: Api) {
    val uiEffects = PublishSubject.create<UiEffect>()
    val stateUpdates = BehaviorSubject.create<State>()
    private val currentState: State
        get() = stateUpdates.value ?: State.DEFAULT
    private val disposables = CompositeDisposable()

    fun dispatch(action: UserAction) {
        reduceState(action)
        startSomething(action)
    }

    private fun reduceState(action: UserAction) {
        val state = when (action) {
            is UserAction.NameUpdated -> currentState.copy(name = action.name)
            is UserAction.UpdateNameClicked -> currentState
        }
        stateUpdates.onNext(state)
    }

    private fun startSomething(action: UserAction) {
        if (action is UserAction.UpdateNameClicked) {
            api.update(currentState.name)
                .doOnSubscribe { stateUpdates.onNext(currentState.copy(isLoading = true)) }
                .subscribe({ newName ->
                    stateUpdates.onNext(currentState.copy(name = newName, isLoading = false))
                }, { error ->
                    stateUpdates.onNext(currentState.copy(name = "", isLoading = false))
                    uiEffects.onNext(UiEffect.ShowError(error))
                })
                .addTo(disposables)
        }
    }

    fun dispose() {
        disposables.clear()
    }

    data class State(val name: String, val isLoading: Boolean) {
        companion object {
            val DEFAULT = State("", false)
        }
    }

    sealed class UserAction {
        data class NameUpdated(val name: String) : UserAction()

        object UpdateNameClicked : UserAction()
    }

    sealed class UiEffect {
        data class ShowError(val error: Throwable) : UiEffect()
    }
}