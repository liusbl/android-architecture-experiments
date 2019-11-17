package com.arch.experiments.tests.test_037.lib

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
    private val state: State get() = stateStream.value ?: initialState

    private val effectStream = PublishSubject.create<ToggleEffect>()

    val unhandledErrorStream = PublishSubject.create<Throwable>()
    val THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM = PublishSubject.create<Throwable>()

    private val disposables = CompositeDisposable()

    // TBD: Plural or singular?
    fun pushAction(
        setState: State.() -> ToggleAction
    ) {
        // TODO
    }

    fun <Field> observe(
        getField: State.() -> Field,
        setField: (Field) -> Unit
    ) {
        stateStream
//            .distinctUntilChanged { state -> state.getField() }
            .map { state -> state.getField() }
            .observeOn(observeScheduler)
            .subscribe({ setField(it) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun <Value> reactToEffect(
        getEffect: State.() -> Effect<Value>,
        react: (Value) -> Unit
    ) {
        stateStream
//            .distinctUntilChanged { state -> state.getField() }
            .map { state -> state.getEffect() }
            .observeOn(observeScheduler)
            .subscribe({ react(it.value) }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    fun pushAction(
        onListenerCalled: (() -> Unit) -> Unit,
        setState: State.(ToggleAction) -> State
    ) {
        // TODO
    }

    fun <T> pushState(
        onListenerCalled: ((T) -> Unit) -> Unit,
        setState: State.(T) -> State
    ) {
        // TBD: Could track listeners and check if they are not repeated twice?
        onListenerCalled { value: T ->
            val newState = state.setState(value)
            stateStream.onNext(newState)
        }
    }

    protected fun onActionToNothing(getToggle: State.() -> ToggleAction, action: () -> Unit) {
        // TODO
    }

    protected fun onActionToNothingCompletable(
        getToggle: State.() -> ToggleAction,
        action: Completable
    ) {
        // TODO
    }

    protected fun onActionToEffect(
        getToggle: State.() -> ToggleAction,
        getEffect: State.() -> ToggleEffect
    ) {
        // TODO
    }

    protected fun onActionToEffectList(
        getToggle: State.() -> ToggleAction,
        vararg getEffect: State.() -> ToggleEffect
    ) {
        // TODO
    }

    protected fun onActionToEffectSingle(
        getToggle: State.() -> ToggleAction,
        getEffect: Single<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    protected fun onActionToEffectObservable(
        getToggle: State.() -> ToggleAction,
        getEffect: Observable<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    protected fun onActionToState(
        getToggle: State.() -> ToggleAction,
        getEffect: State.() -> State
    ) {
        // TODO
    }

    protected fun onActionToStateSingle(
        getToggle: State.() -> ToggleAction,
        getEffect: Single<State.() -> State>
    ) {
        // TODO
    }

    protected fun onActionToStateObservable(
        getToggle: State.() -> ToggleAction,
        getEffect: Observable<State.() -> State>
    ) {
        // TODO
    }

// PTR: States:

    protected fun <Field> onStateToNothing(
        getToggle: State.() -> Field,
        action: () -> Unit
    ) {
        stateStream
            .distinctUntilChanged { state -> state.getToggle() }
            .observeOn(observeScheduler)
            .subscribe({ action() }) {
                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
            }
            .addTo(disposables)
    }

    protected fun <Field> onStateToNothingCompletable(
        getToggle: State.() -> Field,
        action: Completable
    ) {
        stateStream
            .distinctUntilChanged { state -> state.getToggle() }
            .flatMapCompletable { action }
            .observeOn(observeScheduler)
            .subscribe({}, {
            })
            .addTo(disposables)
    }

    protected fun <Field> onStateToEffect(
        getToggle: State.() -> Field,
        getEffect: State.() -> ToggleEffect
    ) {
        stateStream
            .distinctUntilChanged { state -> state.getToggle() }
            .map { state -> state.getEffect() }
            .observeOn(observeScheduler)
            .subscribe({

            }, {
            })
            .addTo(disposables)
    }

    protected fun <Field> onStateToEffectList(
        getToggle: State.() -> Field,
        vararg getEffect: State.() -> ToggleEffect
    ) {
        // TODO
    }

    protected fun <Field> onStateToEffectSingle(
        getToggle: State.() -> Field,
        getEffect: Single<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    protected fun <Field> onStateToEffectObservable(
        getToggle: State.() -> Field,
        getEffect: Observable<State.() -> ToggleEffect>
    ) {
        // TODO
    }

    protected fun <Field> onStateToState(
        getField: State.() -> Field,
        setState: State.() -> State
    ) {
        stateStream
            .distinctUntilChanged { state -> state.getField() }
            .observeOn(observeScheduler)
            .subscribe({
                stateStream.onNext(setState(it))
            }, {
            })
            .addTo(disposables)
    }

    protected fun <Field> onStateToStateSingle(
        getToggle: State.() -> Field,
        setState: Single<State.() -> State>
    ) {

    }

    protected fun <Field> onStateToStateObservable(
        getToggle: State.() -> Field,
        setState: Observable<State.() -> State>
    ) {

    }


//    abstract fun handleEverything()


//    fun <Value> observeState(
//        getStateValue: (State) -> Value,
//        setValue: (Value) -> Unit
//    ) {
//        stateStream
//            .doOnNext { Timber.e("TESTING STATE: $it") }
//            .map { state -> getStateValue.invoke(state) }
//            .observeOn(observeScheduler)
//            .subscribe({ value -> setValue(value) }) {
//                THIS_SHOULD_NEVER_HAPPEN___ERROR_STREAM.onNext(it)
//            }
//            .addTo(disposables)
//    }

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