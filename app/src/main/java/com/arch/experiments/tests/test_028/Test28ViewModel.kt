package com.arch.experiments.tests.test_028

import com.arch.experiments.common.extensions.toObservable
import com.arch.experiments.tests.test_028.Test28ViewModel.*
import com.arch.experiments.tests.test_028.lib.BaseViewModel
import com.arch.experiments.tests.test_028.misc.Database
import com.arch.experiments.tests.test_028.misc.Greeter
import io.reactivex.Observable

class Test28ViewModel(
    private val db: Database,
    private val greeter: Greeter
) : BaseViewModel<State, StateUpdate, Action, Effect>(State(false, "", "")) {
    override fun onStateChange(stateUpdate: StateUpdate): State {
        return when (stateUpdate) {
            is StateUpdate.NameUpdated -> state.copy(name = stateUpdate.name) // Boilerplate
        }
    }

    override fun onAction(action: Action): Observable<State> {
        return when (action) {
            is Action.ViewCreated -> (
                    db.getName()?.let { state.copy(name = it) } ?: state).toObservable()
            is Action.GreetClicked -> greeter.createGreeting(state.name)
                .map { name ->
                    db.setName(name)
                    state.copy(name = name)
                }
        }
    }

    // TODO consider if this should be located in view
    data class State(val isLoading: Boolean, val name: String, val greeting: String)

    // TBD: Maybe instead of StateUpdate class,
    //  there should be a single Action class and two separate dispatch methods,
    //  one for async, other for not
    sealed class StateUpdate {
        data class NameUpdated(val name: String) : StateUpdate()
    }

    sealed class Action {
        object ViewCreated : Action()
        object GreetClicked : Action()
    }

    sealed class Effect {
        object ShowSuccess : Effect()
        object ShowFailure : Effect()
    }
}