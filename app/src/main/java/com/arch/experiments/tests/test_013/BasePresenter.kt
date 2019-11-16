package com.arch.experiments.tests.test_013

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BasePresenter<State : Any, UserAction : Any, UiEffect : Any> {
    val stateUpdates = BehaviorSubject.create<State>()
    val uiEffects = PublishSubject.create<UiEffect>()

    fun dispatch(action: UserAction) {
        reduceState(action)
    }

    abstract fun reduceState(action: UserAction): State

    fun <T : UserAction> onUserAction(): Observable<UserAction> {
        TODO()
    }

    fun dispose() {

    }

    lateinit var currentState: State

    abstract fun getEffects(): List<Observable<StateOrEffect>>
}
