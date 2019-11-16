package com.arch.experiments.tests.test_007

import io.reactivex.Single

interface State
// TBD: How do you resolve a state update conflict?
//  Drop that update
//  I think that it's enough to have state and conflictingAction
abstract class ConflictResolver<State>(
    val currentAction: BaseAction<State>,
    val conflictingAction: BaseAction<State>,
    val currentState: State
) {
    abstract fun resolve(stateUpdate: Single<State>)

    data class Resolution(val updatedState: State, val newStream: Single<State>)
}

class DropConflictResolver<State>(
    current: BaseAction<State>,
    conflictingAction: BaseAction<State>,
    state: State
) : ConflictResolver<State>(current, conflictingAction, state) {
    override fun resolve(stateUpdate: Single<State>) {
        // Empty
    }
}

open class BaseAction<State> {
    open fun resolveConflict(conflictingAction: BaseAction<State>, state: State): ConflictResolver<State> {
        return DropConflictResolver(
            this,
            conflictingAction,
            state
        ) // PTR: by default you just drop the conflicting action and proceed with the current one.
    }
}

// PTR By default switch state but dont do the action.
