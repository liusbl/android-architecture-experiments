package com.arch.experiments.tests.test_036.lib

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
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


    fun <T> pushAction(
        onValueUpdated: ((T) -> Unit) -> Unit,
        setState: State.() -> Action<T>
    ) {
        // Could track listeners and check if they are not repeated twice?

    }

    // TBD: Plural or singular?
    fun <T> pushState(
        onValueUpdated: ((T) -> Unit) -> Unit,
        setState: State.(T) -> State
    ) {

    }

    fun onActionToNothing(getToggle: State.() -> ToggleAction, action: () -> Unit) {
        // TODO
    }

    fun onActionToNothingCompletable(getToggle: State.() -> ToggleAction, action: Completable) {
        // TODO
    }

    fun onActionToEffect(getToggle: State.() -> ToggleAction, getEffect: State.() -> ToggleEffect) {
        // TODO
    }

    fun onActionToEffectList(
        getToggle: State.() -> ToggleAction,
        vararg getEffect: State.() -> ToggleEffect
    ) {
        // TODO
    }

    fun onActionToEffectSingle(
        getToggle: State.() -> ToggleAction,
        getEffect: Single<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    fun onActionToEffectObservable(
        getToggle: State.() -> ToggleAction,
        getEffect: Observable<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    fun onActionToState(
        getToggle: State.() -> ToggleAction,
        getEffect: State.() -> State
    ) {
        // TODO
    }

    fun onActionToStateSingle(
        getToggle: State.() -> ToggleAction,
        getEffect: Single<State.() -> State>
    ) {
        // TODO
    }

    fun onActionToStateObservable(
        getToggle: State.() -> ToggleAction,
        getEffect: Observable<State.() -> State>
    ) {
        // TODO
    }

// PTR: States:

    fun <Field> onStateToNothing(getToggle: State.() -> Field, action: () -> Unit) {
        // TODO
    }

    fun <Field> onStateToNothingCompletable(getToggle: State.() -> Field, action: Completable) {
        // TODO
    }

    fun <Field> onStateToEffect(getToggle: State.() -> Field, getEffect: State.() -> ToggleEffect) {
        // TODO
    }

    fun <Field> onStateToEffectList(
        getToggle: State.() -> Field,
        vararg getEffect: State.() -> ToggleEffect
    ) {
        // TODO
    }

    fun <Field> onStateToEffectSingle(
        getToggle: State.() -> Field,
        getEffect: Single<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    fun <Field> onStateToEffectObservable(
        getToggle: State.() -> Field,
        getEffect: Observable<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    abstract fun handleEverything()

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