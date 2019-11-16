package com.arch.experiments.tests.test_003

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseFeature<State : Any, Action : Any> {
    val state: Observable<State> = BehaviorSubject.create<State>()
    val news: Observable<Action> = PublishSubject.create<Action>()
    protected val actions: Observable<Action> = BehaviorSubject.create<Action>()
    protected val currentState get() = (state as BehaviorSubject).value

    // TODO what if multiple threads?
    fun dispatch(action: Action) {
        val newState = reduce(
            (state as BehaviorSubject).value
                ?: throw IllegalStateException("Initial state was not set"), action
        )
        state.onNext(newState)
        effects().forEach { effect ->
            effect.doOnNext { action ->
                (news as PublishSubject).onNext(action)
                (actions as BehaviorSubject).onNext(action)
            }.subscribe()
        }
    }

    fun start(initialState: State) {
        (state as BehaviorSubject).onNext(initialState)
    }

    abstract fun effects(): List<Observable<Action>>

    abstract fun reduce(state: State, action: Action): State
}