package com.arch.experiments.tests.test_009

import com.arch.experiments.common.extensions.toObservable
import com.arch.experiments.tests.test_008.Test8Fragment
import com.arch.experiments.tests.test_009.Test9Fragment.State
import com.arch.experiments.tests.test_009.misc.LoginUseCase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

// TBD should Test9Fragment hold it's own view state? Seems a bit incorrect
class Test9Reducer(
    private val loginUseCase: LoginUseCase
) {
    // TBD: I see a few ways to update Test9Fragment state:
    //  First: directly call onNext for stateUpdates
    //  Second: return state to Observable, which will be handled somewhere else
    //  I see that First would be "easier", but then it get's a bit weird, since you're directly
    //      querying and updating Test9Fragment state.
    //      Also, with second method, state is part of a stream, not like in first method
    //  Maybe you should only get Observable<State> from Test9Fragment?
    fun reduce_FIRST(stateUpdates: BehaviorSubject<Test8Fragment.State>): Disposable {
        return stateUpdates
            .distinctUntilChanged { state -> state.isLoginClicked }
            .filter { state -> state.isLoginClicked }
            .flatMapCompletable { state ->
                loginUseCase.login(state.username, state.password)
            }
            .doOnSubscribe {
                stateUpdates.value?.let { stateUpdates.onNext(it.copy(isLoading = true)) }
            }
            .doOnError {
                stateUpdates.value?.let { stateUpdates.onNext(it.copy(isLoading = false)) }
            }
            .subscribe({
                stateUpdates.value?.let { stateUpdates.onNext(it.copy(isLoading = false)) }
            }, { throwable -> })
    }

    // PTR: This one is better
    fun reduce_SECOND(stateUpdates: Observable<State>): Observable<State> {
        return stateUpdates
            .distinctUntilChanged(State::isLoginClicked)
            .filter(State::isLoginClicked)
            .flatMap { state ->
                loginUseCase.login(state.username, state.password)
                    .startWith(state.copy(isLoading = true).toObservable())
                    .onErrorReturnItem(state.copy(isLoading = false))
                    .concatWith(state.copy(isLoading = false).toObservable())
            }
    }
}