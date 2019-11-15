package com.arch.experiments.tests.test_001

import com.arch.experiments.common.extensions.toObservable
import com.arch.experiments.tests.test_001.misc.LoginUseCase
import com.arch.experiments.tests.test_001.misc.MyPreferences
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

// PTR: Learn how to explain why you need state.copy, instead of having each field as a variable
class Test1Reducer(
    private val api: LoginUseCase,
    private val preferences: MyPreferences
) : BaseReducer<Test1Reducer.State, Test1Reducer.Action>(
    State(
        preferences.username,
        "",
        preferences.rememberUsername,
        false,
        ErrorState(null, false)
    )
) {
    override fun reduce(action: Action): Observable<State> {
        return when (action) {
            is Action.RememberUsernameChecked -> {
                Observable.fromCallable {
                    preferences.rememberUsername = action.isChecked
                    state.copy(rememberUsername = action.isChecked)
                }
            }
            is Action.UsernameEntered -> state.copy(username = action.username).toObservable()
            is Action.PasswordEntered -> state.copy(password = action.password).toObservable()
            is Action.LoginClicked -> reduceLoginClickedAction()
        }
    }

    private fun reduceLoginClickedAction(): Observable<State> {
        return api.login(state.username, state.password)
            .toObservable<State>()
            .startWith(state.copy(isLoading = true))
            .onErrorResumeNext { throwable: Throwable ->
                Observable.timer(1, TimeUnit.SECONDS, Schedulers.computation())
                    .map { state.copy(password = "", toastErrorState = ErrorState(throwable, true)) }
                    .startWith(state.copy(toastErrorState = ErrorState(null, false)))
            }
            .concatWith(state.copy(isLoading = false).toObservable())
    }

    data class State(
        val username: String,
        val password: String,
        val rememberUsername: Boolean,
        val isLoading: Boolean,
        val toastErrorState: ErrorState // TBD should I specify "toast"? I'm thinking there might be more error states
    )

    sealed class Action {
        data class RememberUsernameChecked(val isChecked: Boolean) : Action()
        data class UsernameEntered(val username: String) : Action()
        data class PasswordEntered(val password: String) : Action()
        object LoginClicked : Action()
    }
}

// TODO figure out standard Toast show time.

data class ErrorState(val throwable: Throwable?, val isShowing: Boolean)

// TODO figure out a way to access state of wider scope
// PTR: now more things might get moved to "presenter"
// PTR: this architecture is good because it exposes the actual complexity of the feature, even when it seems simple and without logic.

