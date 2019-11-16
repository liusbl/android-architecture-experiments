package com.arch.experiments.tests.test_008

import com.arch.experiments.tests.test_008.Test8Fragment.State
import com.arch.experiments.tests.test_008.misc.LoginUseCase
import io.reactivex.Observable
import timber.log.Timber

// TODO: Before everything else, the first of all the first things
//  that should receive state updates are objects that don't edit state themselves,
//  only after that can you send it to thing that will edit the state.
//  Why? Because if you don't do that, a state might be missed by other components.
//  How to ensure that all components that require state receive the proper state?
//  TBD: Maybe state should only update one field at a time? This way it would be easier to handle
//   Maybe there is a convenient language way to copy state with only one parameter changes?
//   Now calling state.copy.. for only one parameter seems verbose

class Test8Reducer(private val loginUseCase: LoginUseCase) : StateUpdateReducer<State> {
    override fun reduce(
        stateUpdates: Observable<State>,
        getState: () -> State
    ): Observable<State> {
        return stateUpdates
            .distinctUntilChanged { state -> state.isLoginClicked }
            .filter { state -> state.isLoginClicked }
            .flatMap {
                loginUseCase.login(getState().username, getState().password)
                    .toObservable<State>()
                    .startWith(Observable.fromCallable {
                        Timber.v("${threadPlusTime()}BEFORE BaseViewStateFragment startWith: $it")
                        val copy = getState().copy(isLoginClicked = false, isLoading = true)
                        Timber.v("${threadPlusTime()}AFTER BaseViewStateFragment startWith: $copy")
                        copy
                    })
                    .onErrorResumeNext(Observable.fromCallable {
                        val copy = getState().copy(isLoading = false)
                        Timber.v("${threadPlusTime()}NOT BaseViewStateFragment onErrorResumeNext: $copy")
                        copy
                    })
                    .concatWith(Observable.fromCallable {
                        Timber.v("${threadPlusTime()}BEFORE BaseViewStateFragment concatWith: $it")
                        val copy = getState().copy(isLoading = false)
                        Timber.v("${threadPlusTime()}AFTER BaseViewStateFragment concatWith: $copy")
                        copy
                    })
            }
    }
}