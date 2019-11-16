package com.arch.experiments.tests.test_007

import com.arch.experiments.common.extensions.addTo
import com.arch.experiments.common.extensions.toSingle
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.atomic.AtomicReference

sealed class LoginStateUpdateRequest : OutsideUpdateRequest {
    data class LoadClicked(val isClicked: Boolean) : LoginStateUpdateRequest()
    data class StopClicked(val isClicked: Boolean) : LoginStateUpdateRequest()
    data class TextUpdated(val text: String) : LoginStateUpdateRequest()
}

sealed class InnerLoginStateUpdateRequest : InnerUpdateRequest {
    data class TextReceived(val text: String) : InnerLoginStateUpdateRequest()
}

class LoginReducer(val api: Api) {
    private val disposables = CompositeDisposable()

    val state: LoginState
        get() = TODO("Actually provide current state")

    private var runningStateUpdate = AtomicReference<Single<LoginState>?>()

    fun reduce(stateUpdateRequest: LoginStateUpdateRequest) {
        if (runningStateUpdate.compareAndSet(null, process(stateUpdateRequest))) {
            // Run state update
            runningStateUpdate.get()
                ?.subscribe({

                }, { throwable ->

                })
                ?.addTo(disposables)
        } else {
            // Resolve conflict

        }
    }

    private fun process(stateUpdateRequest: LoginStateUpdateRequest): Single<LoginState> {
        return when (stateUpdateRequest) {
            is LoginStateUpdateRequest.LoadClicked -> {
                api.modifyText(state.input)
                    .map { state.copy(input = it) }
            }
            is LoginStateUpdateRequest.StopClicked -> {
                state.toSingle() // Also interrupt loading event
            }
            is LoginStateUpdateRequest.TextUpdated -> {
                state.copy(input = stateUpdateRequest.text).toSingle()
            }
        }
    }
}

data class LoginState(
    val loadClicked: Boolean,
    val stopClicked: Boolean,
    val input: String
)

// PTR: THIS WILL NOT WORK, THERE NEEDS TO BE A PLACE WHERE STATE IS CHANGED SYNC,
//  OTHERWISE CONFLICT WILL ALWAYS BE A PROBLEM


// PTR in effect, these are pairs: each Action has an associated AsyncStateUpdate

// TODO: Add to debug program checker that checks if each state update is distinct, otherwise crash. On release delete this.