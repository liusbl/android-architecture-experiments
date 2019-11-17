package com.arch.experiments.tests.test_029

import com.arch.experiments.common.extensions.toObservable
import com.arch.experiments.tests.test_029.Test29ViewModel.*
import com.arch.experiments.tests.test_029.lib.BaseViewModel
import com.arch.experiments.tests.test_029.misc.Database
import com.arch.experiments.tests.test_029.misc.Greeter
import io.reactivex.Observable

class Test29ViewModel(
    private val db: Database,
    private val greeter: Greeter
) : BaseViewModel<State, Action, Effect>(State(false, "", "")) {
    override fun onAction(action: Action): Observable<State> {
        return when (action) {
            is Action.ViewCreated -> (db.getName()?.let { state.copy(name = it) }
                ?: state).toObservable()
            is Action.GreetClicked -> greeter.createGreeting(state.name)
                .map { name ->
                    db.setName(name)
                    state.copy(name = name)
                }
            is Action.NameUpdated -> state.copy(name = action.name).toObservable()
        }
    }

    // TODO consider if this should be located in view
    data class State(val isLoading: Boolean, val name: String, val greeting: String)

    sealed class Action {
        object ViewCreated : Action()
        object GreetClicked : Action()
        data class NameUpdated(val name: String) : Action()
    }

    sealed class Effect {
        object ShowSuccess : Effect()

        object ShowFailure : Effect()
    }
}