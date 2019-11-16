package com.arch.experiments.tests.test_015

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BasePresenter<State : Any, UserAction : Any, UiEffect : Any> {
    val stateUpdates = BehaviorSubject.create<State>()
    val uiEffects = PublishSubject.create<UiEffect>()

    abstract fun reduceState(action: UserAction, state: State): State

    fun dispatch(action: UserAction) {
        // TODO implement
        reduceState(action, stateUpdates.blockingFirst())
    }

    fun <T : UserAction> onUserAction(): Observable<UserAction> {
        TODO()
    }

    fun dispose() {

    }

    abstract fun getEffects(): List<Observable<StateOrEffect>>

    fun <UserAction> getUserActionUpdates(): Observable<StateOrEffect> {
        TODO("implement on final interations")
    }

    fun <T : State> getState(): T {
        TODO()
    }
}
