package com.arch.experiments.tests.test_002

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseReducer<State, Action, Effect>(private val initialState: State) {
    protected val disposables = CompositeDisposable()
    private val stateUpdates = BehaviorSubject.create<State>()
    private val effectUpdates = BehaviorSubject.create<Effect>()
    val states: Observable<State> = stateUpdates
    val effects: Observable<Effect> = effectUpdates

    val state: State
        get() = stateUpdates.value ?: initialState

    abstract fun reduce(action: Action): State

    fun dispatch(action: Action) {
        stateUpdates.onNext(reduce(action))
    }

    protected fun dispatchEffect(effect: Effect) {
        effectUpdates.onNext(effect)
    }

    fun clear() {
        disposables.clear()
    }
}