package com.arch.experiments.tests.test_001

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

// TODO maybe this is more a screen reducer, since Action comes from UI?
abstract class BaseReducer<State, Action>(private val initialState: State) {
    private val stateUpdates = BehaviorSubject.create<State>()
    private val disposables = CompositeDisposable()
    val states: Observable<State> = stateUpdates

    val state: State
        get() = stateUpdates.value ?: initialState

    abstract fun reduce(action: Action): Observable<State>

    fun dispatch(action: Action) {
        disposables.add(reduce(action).subscribe(stateUpdates::onNext))
    }

    fun clear() {
        disposables.clear()
    }
}