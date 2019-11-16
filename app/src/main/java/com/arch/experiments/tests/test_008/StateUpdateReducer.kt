package com.arch.experiments.tests.test_008

import io.reactivex.Observable

interface StateUpdateReducer<State> {
    fun reduce(stateUpdates: Observable<State>, getState: () -> State): Observable<State>
}