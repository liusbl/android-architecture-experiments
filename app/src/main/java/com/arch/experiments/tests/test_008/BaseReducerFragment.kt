package com.arch.experiments.tests.test_008

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import androidx.annotation.CallSuper
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

abstract class BaseReducerFragment<Reducer : StateUpdateReducer<State>, State>(
    layoutRes: Int
) : BaseFragment(layoutRes) {
    lateinit var reducer: Reducer // Inject

    private val state
        get() = stateUpdates.value ?: defaultState

    private val disposables = CompositeDisposable()
    private val executor = Executors.newSingleThreadExecutor { Thread("state_updates") }
    private val singleThreadScheduler = /*Schedulers.from(executor)*/Schedulers.single()

    val stateUpdates: BehaviorSubject<State> by lazy {
        BehaviorSubject.createDefault(defaultState).apply {
            distinctUntilChanged()
                .subscribeOn(singleThreadScheduler)
                .observeOn(singleThreadScheduler)
        }
    }

    abstract val defaultState: State

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        reducer.reduce(stateUpdates, { state })
            .subscribeOn(singleThreadScheduler)
            .observeOn(singleThreadScheduler)
            .distinctUntilChanged()
            .subscribe {
                Timber.i("${threadPlusTime()}Next state: $it")
                stateUpdates.onNext(it)
            }
            .addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    // TODO: There are HUGE state update synchronization problems

    fun View.handleVisibility(visibilityFromState: State.() -> Boolean) {
        stateUpdates
            .subscribeOn(singleThreadScheduler)
            .distinctUntilChanged { state ->
                state.visibilityFromState().apply {
                    Timber.v("${threadPlusTime()}View.handleVisibility 111: $state")
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                val show = state.visibilityFromState()
                Timber.v("${threadPlusTime()}View.handleVisibility 222: $state")
                this@handleVisibility.visibility = if (show) View.VISIBLE else View.GONE
            }
            .addTo(disposables)
    }

    fun View.handleClicks(updateState: State.(Boolean) -> State) {
        setOnClickListener {
            val updateState1 = state.updateState(true)
                .apply {
                    Timber.i("${threadPlusTime()}View.handleClicks1: $this")
                }
            stateUpdates.onNext(updateState1)
        }
    }

    fun EditText.handleChanges(
        updateState: State.(String) -> State,
        textFromState: State.() -> String
    ) {
        var latest: State? = null
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                Timber.w("${threadPlusTime()}EditText user update: $editable")
                val state = state.updateState(editable.toString())
                latest = state
                stateUpdates.onNext(state)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Empty
            }
        }
        stateUpdates.distinctUntilChanged(textFromState::invoke)
            .filter { it != latest }
            .subscribeOn(singleThreadScheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { value ->
                Timber.w("${threadPlusTime()}EditText programmatic update: ${value.textFromState()}")
                val editText = this@handleChanges
                editText.removeTextChangedListener(textWatcher)
                editText.setText(value.textFromState())
                editText.addTextChangedListener(textWatcher)
            }.addTo(disposables)
    }

    fun CompoundButton.handleChanges(
        updateState: State.(Boolean) -> State,
        isCheckedFromState: State.() -> Boolean
    ) {
        var latest: State? = null
        val listener: (CompoundButton, Boolean) -> Unit = { _, isChecked ->
            Timber.e("${threadPlusTime()}CompoundButton user update: $isChecked")
            state.updateState(isChecked)
                .also { latest = it }
                .apply(stateUpdates::onNext)
        }
        setOnCheckedChangeListener(listener)
        stateUpdates.distinctUntilChanged(isCheckedFromState::invoke)
            .filter { it != latest }
            .subscribeOn(singleThreadScheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { value ->
                Timber.e("${threadPlusTime()}CompoundButton programmatic update: ${value.isCheckedFromState()}")
                val switch = this@handleChanges
                switch.setOnCheckedChangeListener(null)
                switch.isChecked = value.isCheckedFromState()
                switch.setOnCheckedChangeListener(listener)
            }.addTo(disposables)
    }
}

fun threadPlusTime(): String {
    val formatter = SimpleDateFormat("mm:ss:SSS")
    val date = Date(System.currentTimeMillis())
    val format = formatter.format(date)
    return "$format;${Thread.currentThread().name};"
}