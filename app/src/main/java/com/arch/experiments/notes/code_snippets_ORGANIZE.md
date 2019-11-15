package com.abus.splicelock.screen.wizard

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import com.abus.basesmartx.receiver.BluetoothStatusReceiver
import com.abus.splicelock.R
import com.abus.splicelock.base.BaseFeature
import com.abus.splicelock.base.BaseFragment
import com.abus.splicelock.entity.UserEntity
import com.abus.splicelock.extensionscore.isVisible
import com.abus.splicelock.screen.wizard.WizardStartFeature.Action
import com.abus.splicelock.screen.wizard.WizardStartFeature.State
import com.abus.splicelock.store.GlobalAction
import com.abus.splicelock.store.Store
import com.freeletics.rxredux.SideEffect
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import kotlinx.android.synthetic.main.fragment_wizard_start.*
import javax.inject.Inject

class WizardStartFragment : BaseFragment(R.layout.fragment_wizard_start) {
    private val feature by ViewModelDelegate(WizardStartFeature::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        wizard_start_logout.setOnClickListener {
            store.dispatch(GlobalAction.Logout)
        }
        wizard_start_scan_button.setOnClickListener {
            store.dispatch(
                GlobalAction.Navigate(
                    WizardStartFragmentDirections.actionWizardStartFragmentToWizardScanFragment()
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        store.state.subscribe { state ->
            TransitionManager.beginDelayedTransition(wizard_start_root)
            wizard_start_header.setSubtitle(state.user?.firstName)
            user_wizard_bt_switch.isEnabled = true
            wizard_start_scan_button.isVisible = state.btEnabled
            wizard_start_scan_info.isVisible = state.btEnabled
            user_wizard_bt_switch.isVisible = !state.btEnabled
            wizard_start_bt_disabled.isVisible = !state.btEnabled
            wizard_start_bt_explanation.isVisible = !state.btEnabled
            user_wizard_bt_switch.setOnCheckedChangeListener(null)
            user_wizard_bt_switch.isChecked = state.btEnabled
            user_wizard_bt_switch.setOnCheckedChangeListener { buttonView, isChecked ->
                feature.dispatch(Action.ActivateBluetooth(isChecked))
                user_wizard_bt_switch.isEnabled = false
            }
        }.addToDisposables()
    }
}

class WizardStartFeature @Inject constructor(
    private val bluetoothController: BluetoothStatusReceiver,
    private val store: Store
) : BaseFeature<State, Action>() {
    override fun getEffectsV2() = listOf(btEffectV2, btEnableEffectV2)

    override fun getEffects() = listOf(btEffect, btEnableEffect)

    override fun reducer(state: State, action: Action) = when (action) {
        is Action.ActivateBluetooth -> state
        is Action.BluetoothActivated -> state.copy(btEnabled = action.enabled)
    }

    private val btEffectV2: Observable<Action> =
        bluetoothController.getBluetoothStatus()
            .map { Action.BluetoothActivated(it) }

    private val btEffect: SideEffect<State, Action> = { actions, state ->
        bluetoothController.getBluetoothStatus()
            .map { Action.BluetoothActivated(it) }
    }

    private val btEnableEffectV2: Observable<Action> =
        actions.ofType<Action.ActivateBluetooth>()
            .doOnNext { action ->
                getState().btEnabled // If you need state
                bluetoothController.enableBluetooth(action.activate)
            }
            .doOnNext { store.dispatch(GlobalAction.Back) }
            .flatMap { Observable.empty<Action>() }

    private val btEnableEffect: SideEffect<State, Action> = { actions, state ->
        actions.ofType<Action.ActivateBluetooth>()
            .doOnNext { action ->
                bluetoothController.enableBluetooth(action.activate)
            }
            .doOnNext { store.dispatch(GlobalAction.Back) }
            .flatMap { Observable.empty<Action>() }
    }

    fun isBluetoothEnabled() = bluetoothController.isBluetoothEnabled()

    sealed class Action {
        class ActivateBluetooth(val activate: Boolean) : Action()
        class BluetoothActivated(val enabled: Boolean) : Action()
    }

    data class State(val btEnabled: Boolean, val userEntity: UserEntity?)
}
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
package com.abus.splicelock.base

import androidx.lifecycle.ViewModel
import com.abus.splicelock.base.MyFeature.Action
import com.abus.splicelock.base.MyFeature.State
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class Fragment {
    fun thing(feature: MyFeature) {
        feature.dispatch(Action.SomeUiEvent)
    }
}

abstract class CustomBaseFeature<State : Any, Action : Any> : ViewModel() {
    private val disposables = CompositeDisposable()
    private val incomingActions = PublishSubject.create<Action>()
    private val stateSubject = BehaviorSubject.create<State>()
    protected val actions: Observable<Action> = PublishSubject.create<Action>()

    // TODO what if multiple threads?
    fun dispatch(action: Action) {
        val newState = reduce(stateSubject.value ?: throw IllegalStateException("Initial state was not set"), action)
        stateSubject.onNext(newState)

    }

    fun start(initialState: State) {
        stateSubject.onNext(initialState)
        // SomeUiEvent reduce
        // Notify side effects
    }

    abstract fun effects(): List<Observable<Action>>

    abstract fun reduce(state: State, action: Action): State
}

class MyFeature : CustomBaseFeature<State, Action>() {
    override fun effects(): List<Observable<Action>> {
        return listOf(effect1, effect2)
    }

    override fun reduce(state: State, action: Action): State {
        return state.copy(a = "fddf")
    }

    private val effect1 = Observable.timer(1, TimeUnit.SECONDS)
        .flatMap { Observable.empty<Action>() }

    private val effect2: Observable<Action> = actions.ofType<Action.SomeUiEvent>()
        .map { Action.SomeEffect }

    data class State(val a: String)

    sealed class Action {
        object SomeUiEvent : Action()
        object SomeEffect : Action()
    }
}
------------
------------
------------
package com.abus.splicelock.base

import androidx.lifecycle.ViewModel
import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import com.freeletics.rxredux.reduxStore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class BaseFeature<State : Any, Action : Any> : ViewModel() {
    private val disposables = CompositeDisposable()

    private val actionsSubject = PublishSubject.create<Action>()
    private val currentState = BehaviorSubject.create<State>()
    private val newsSubject = PublishSubject.create<Action>()

    val state: Observable<State>
        get() = currentState.observeOn(AndroidSchedulers.mainThread())

    val stateValue: State? = currentState.value

    val news: Observable<Action>
        get() = newsSubject.observeOn(AndroidSchedulers.mainThread())

    fun start(initialState: State) {
        actionsSubject
            .observeOn(SchedulerProvider.appScheduler)
            .reduxStore(
                initialState,
                getEffectsUpdateV2(),
                ::internalReducer
            )
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                disposables += it
            }
            .subscribe(currentState)
    }


    val actions: Observable<Action> = Observable.empty<Action>()
    val getState: StateAccessor<State> = null

    private fun getEffectsUpdateV2(): List<SideEffect<State, Action>> {
        return getEffectsV2().map { observable: Observable<Action> ->
            { actions: Observable<Action>, state: StateAccessor<State> ->
                // Update getState method
                this@BaseFeature.actions.switchMap { actions }
                    .concatWith(observable)
            }
        }
    }

    fun dispatch(action: Action) {
        actionsSubject.onNext(action)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    abstract fun getEffectsV2(): List<Observable<Action>>

    abstract fun getEffects(): List<SideEffect<State, Action>>
    abstract fun reducer(state: State, action: Action): State

    private fun internalReducer(state: State, action: Action): State {
        return reducer(state, action).apply {
            newsSubject.onNext(action)
        }
    }
}

--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
 fun CompoundButton.handleChanges(
            updateState: State.(Boolean) -> State,
            isCheckedFromState: State.() -> Boolean
    ) {

        handleChanges<Boolean, CompoundButton, State>(
                stateUpdates,
                { isChecked -> },
                { listener -> setOnCheckedChangeListener { _, isChecked -> listener(isChecked) } },
                { _, state -> setOnCheckedChangeListener(null).also { isChecked = state.isCheckedFromState() } }
        )

        var latest: State? = null  l
        val listener: (buttonView: CompoundButton, isChecked: Boolean) -> Unit = { _, isChecked ->
            state.updateState(isChecked)
                    .also { latest = it }
                    .apply(stateUpdates::onNext)
        }
        setOnCheckedChangeListener(listener)
        stateUpdates.filter { it != latest }
                .subscribe { value ->
                    val switch = this@handleChanges
                    switch.setOnCheckedChangeListener(null)
                    switch.isChecked = value.isCheckedFromState()
                    switch.setOnCheckedChangeListener(listener)
                }.addDisposable()
    }
}
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
 package com.telesoftas.architecture.comparison.my_architecture.fragments.example6

import com.telesoftas.architecture.comparison.my_architecture.fragments.example6.Example6Fragment.StringPlusId
import com.telesoftas.architecture.comparison.my_architecture.fragments.example6.core_lib.Observer
import com.telesoftas.architecture.comparison.my_architecture.fragments.example6.core_lib.Pusher

class Example6Controller(
    private val editTextMachine: Observer<StringPlusId>,
//    private val editTextMachine2: Observer<StringPlus>,
    private val buttonMachine: Observer<Unit>,
    private val screenObserver: Pusher<StringPlusId>
) {
    fun start() {
        buttonMachine.observe {
            screenObserver.push { editTextMachine.value }
        }


//        buttonMachine.observe {
//            count++
//            toastMachine.push { "Updated count: $count" }
//            textViewMachine.push { "Clicked $count times" }
//        }
//        editTextMachine1.observe {
//            editTextMachine2.push { "$this*" }
//        }
//        editTextMachine2.observe { editTextMachine1.push { "$this+" } }
    }
}
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
PTR: if you do the traditional push/observe, then every screen has to have the same logic:

eidtText.onTextChanged { stateMachine.push...}
stateMachine.observe { ediText.text = state.text }

Currently divided between two approaches: State machines and viewModels

Actually things are a bit different..

This state belongs to the view! Wait is that true
That means that state should be able
  fun CompoundButton.handleChanges(
            updateState: State.(Boolean) -> State,
            isCheckedFromState: State.() -> Boolean
    ) {

        handleChanges<Boolean, CompoundButton, State>(
                stateUpdates,
                { isChecked -> },
                { listener -> setOnCheckedChangeListener { _, isChecked -> listener(isChecked) } },
                { _, state -> setOnCheckedChangeListener(null).also { isChecked = state.isCheckedFromState() } }
        )

        var latest: State? = null
        val listener: (buttonView: CompoundButton, isChecked: Boolean) -> Unit = { _, isChecked ->
            state.updateState(isChecked)
                    .also { latest = it }
                    .apply(stateUpdates::onNext)
        }
        setOnCheckedChangeListener(listener)
        stateUpdates.filter { it != latest }
                .subscribe { value ->
                    val switch = this@handleChanges
                    switch.setOnCheckedChangeListener(null)
                    switch.isChecked = value.isCheckedFromState()
                    switch.setOnCheckedChangeListener(listener)
                }.addDisposable()
    }
}
--------------------------------------------------
--------------------------------------------------
--------------------------------------------------
All examples at first will only be for a single screen, no fragments or activities meshugas
   sealed class State<T> : StateBase {
        object IsButtonClicked : State<Boolean>()
        object IsLoading : State<Boolean>()
        object Name : State<String>()
        object Greeting : State<String>()
    }
