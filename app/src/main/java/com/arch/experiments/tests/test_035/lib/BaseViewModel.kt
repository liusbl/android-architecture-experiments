package com.arch.experiments.tests.test_035.lib

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<State>(
    private val initialState: State,
    private val observeScheduler: Scheduler = AndroidSchedulers.mainThread() // TODO remove this, since it adds additional dependency. Maybe move to lib/android
) {
    private val stateStream = BehaviorSubject.createDefault<State>(initialState)
    protected val state: State get() = stateStream.value ?: initialState

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()

    private val disposables = CompositeDisposable()

    // TBD: Plural or singular?
    fun pushAction(
        setState: State.() -> ToggleAction
    ) {
        // TODO
    }


    // TBD: Plural or singular?
    fun <T> pushAction(
        onValueUpdated: ((T) -> Unit) -> Unit,
        setState: State.() -> Action<T>
    ) {
        // Could track listeners and
        // TODO
    }

    // TBD: Plural or singular?
    fun <T> pushState(
        onValueUpdated: ((T) -> Unit) -> Unit,
        setState: State.(T) -> State
    ) {

    }

//    fun <T> pushStateWithData(
//        onValueUpdated: ((T) -> Unit) -> Unit,
//        setState: State.(T) -> State
//    ) {
//        // Could track listeners and
//        // TODO
//    }

    // Would this ever be used?
/*
    fun <T> pushState(
        onValueUpdated: (() -> Unit) -> Unit,
        setState: State.() -> State
    ) {
        // Could track listeners and
        // TODO
    }
*/


//    fun <Value> push(
//        onValueUpdated: ((Value) -> Unit) -> Unit,
//        setState: State.(Value) -> State
//    ) {
//         Could track listeners and
//         TODO
//    }

    // TBD: Should you have "reactEffect" to keep consistent with "pushAction"? My guess is Probably
    //  Answer: YES

    // TBD: Plural or singular?
    fun <Value> observeState(
        getStateValue: (State) -> Value,
        setValue: (Value) -> Unit
    ) {
        stateStream
            .map { state -> getStateValue.invoke(state) }
            .observeOn(observeScheduler)
            .subscribe({ value -> setValue(value) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    // TBD: Plural or singular?
    fun <T, E : Effect<T>> observeEffect(
        getEffect: (State) -> E,
        handleEffect: (T) -> Unit
    ) {
        // TODO
    }

    // PTR: When implementing, don't forget to not renotify the listener
    // For this type it's difficult to make this
//    fun <Value> pushAndReact(
//        push: ((Value) -> Unit) -> Unit,
//        setState: State.(Value) -> State,
//        getValue: (State) -> Value,
//        onState: (Value) -> Unit // Maybe reaction could wrap somehow so that listener is not notified or something?
//    ) {
//    }

    fun init(state: State) {
        stateStream.onNext(state)
    }

    fun reset() {
        init(initialState)
    }

    fun observeFullState(setState: (State) -> Unit) {
        stateStream
            .observeOn(observeScheduler)
            .subscribe({ state -> setState(state) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }


    fun clearDisposables() {
        disposables.clear()
    }

    private fun Disposable.addTo(disposables: CompositeDisposable) {
        disposables.add(this)
    }
}