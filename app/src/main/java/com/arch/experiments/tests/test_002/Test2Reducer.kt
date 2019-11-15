package com.arch.experiments.tests.test_002

import com.arch.experiments.common.extensions.addTo
import com.arch.experiments.tests.test_002.Test2Reducer.*
import com.arch.experiments.tests.test_002.misc.LoginUseCase

class Test2Reducer(private val api: LoginUseCase) : BaseReducer<State, Action, Effect>(
    State("", "", false)
) {
    override fun reduce(action: Action): State {
        return when (action) {
            is Action.UsernameEntered -> state.copy(username = action.username)
            is Action.PasswordEntered -> state.copy(password = action.password)
            is Action.LoginClicked -> { // PTR: Non-one-liner state changes could be extracted to methods
                // TODO How to handle when request is finished?
                //  How to set for example isLoading=false after login is done?
                api.login(state.username, state.password)
                    .doOnSubscribe {
                        dispatchEffect(Effect.LoginStarted) // TODO is this okay? seems like an unhandled mutation
                    }
                    .doOnComplete {
                        dispatchEffect(Effect.LoginFinished) // TODO is this okay? seems like an unhandled mutation
                    }.subscribe().addTo(disposables)
                state
            }
        }
    }

    // PTR it's gonna be difficult to sync all the states
    // TODO maybe username and password also should be async?
    data class State(val username: String, val password: String, val isLoading: Boolean)
    // TODO maybe there should be a separate state, where user would be sync?

    // TODO something like this:
//    data class SyncState(val username: String, val password: String, val user: User, val isLoading: Boolean)
//    private data class AsyncState(val getUser: Single<User>) (only for


    // PTR: These come SYNC, from UI
    sealed class Action {
        data class UsernameEntered(val username: String) : Action()
        data class PasswordEntered(val password: String) : Action()
        object LoginClicked : Action()
    }

    // PTR: These come ASYNC, from system
    sealed class Effect {
        object LoginStarted : Effect()
        object LoginFinished : Effect()
    }
}
// PTR: Database state is also a state, just that it takes some time to update and access