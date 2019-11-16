package com.arch.experiments.tests.test_017

import com.arch.experiments.common.extensions.addTo
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

open class BaseStateMachine<State>(
    private val initialState: State,
    private val observeScheduler: Scheduler = AndroidSchedulers.mainThread()
    // TODO remove AndroidScheduler, since it adds additional dependency. Maybe move to lib/android
) : StateMachine<State> {
    private val stateStream =
        BehaviorSubject.createDefault<StateUpdate<State>>(
            StateUpdate(
                initialState,
                false
            )
        )

    override fun react(
        reactionTrigger: State.() -> Any,
        stateSupplier: State.((setState: State) -> Unit) -> Unit
    ) {
        // Empty
    }

    private val disposables = CompositeDisposable()

    private val state: State get() = stateStream.value?.state ?: initialState

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()


    override fun push(updateState: State.() -> State) {
        val newState = state.updateState()
        stateStream.onNext(
            StateUpdate(
                newState,
                true
            )
        )
    }

    fun reactToUpdateRequest(
        reactionTrigger: State.() -> Any,
        stateSupplier: State.((setState: State) -> Unit) -> Unit
    ) {
        stateStream
            .distinctUntilChanged { stateUpdate -> stateUpdate.state.reactionTrigger() }
            .filter { stateUpdate -> !stateUpdate.isFromView }
// TODO How to do this?
//  Probably this shouldn't be coupled with some "View" object, both should be equels
//  Maybe it should be "isFromFirst" or "isFromSecond" or something like that?
            .observeOn(observeScheduler)
            .subscribe({ stateUpdate: StateUpdate<State> ->
                stateSupplier(stateUpdate.state, { state: State ->
                    stateStream.onNext(StateUpdate(state, false))
                })
            }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun clearDisposables() {
        disposables.clear()
    }

    private data class StateUpdate<State>(val state: State, val isFromView: Boolean)
}