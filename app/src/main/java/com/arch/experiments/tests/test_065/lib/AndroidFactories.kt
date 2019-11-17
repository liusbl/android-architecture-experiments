package com.arch.experiments.tests.test_065.lib

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AndroidTransformation<State> : ConfigTransformation<State> {
    private var observedValue: State? = null

    override fun transformObserve(state: State, observe: (State) -> Unit) {
        observedValue = state
        observe(state)
    }

    override fun transformOnPush(state: State, push: (State) -> Unit) {
        if (observedValue == null) {
            push(state)
        }
        observedValue = null
    }
}

interface AndroidFactories {
    fun EditText.getTextConfig() = object : StateConfig<String>(AndroidTransformation()) {
        override fun observe(state: String) {
            this@getTextConfig.setText(state)
        }

        override fun onPush(push: (String) -> Unit) {
            this@getTextConfig.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    editable?.toString()?.let(push::invoke)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Empty
                }
            })
        }
    }

    fun CompoundButton.getCheckConfig() = object : StateConfig<Boolean>() {
        override fun observe(state: Boolean) {
            this@getCheckConfig.isChecked = state
        }

        override fun onPush(push: (Boolean) -> Unit) {
            this@getCheckConfig.setOnCheckedChangeListener { _, isChecked -> push(isChecked) }
        }
    }

    fun TextView.getTextConfig() = object : StateConfig<String>() {
        override fun observe(state: String) {
            this@getTextConfig.text = state
        }
    }

    fun View.getVisibilityConfig() = object : StateConfig<ViewVisibility>() {
        override fun observe(state: ViewVisibility) {
            this@getVisibilityConfig.visibility = state.value
        }
    }

    enum class ViewVisibility(val value: Int) {
        VISIBLE(View.VISIBLE), GONE(View.GONE), INVISIBLE(View.INVISIBLE)
    }

    fun View.getClickConfig() = object : EventConfig<Unit>() {
        override fun onPush(push: (Unit) -> Unit) {
            this@getClickConfig.setOnClickListener { push(Unit) }
        }
    }

    fun Context.getToastConfig() = object : EventConfig<String>() {
        override fun observe(state: String) {
            Toast.makeText(this@getToastConfig, state, Toast.LENGTH_SHORT).show()
        }
    }

    fun <State> Config<State>.createMachine(initialState: State): Machine<State> {
        val machineLinker = MachineLinker(initialState)
        machineLinker.attachMachine(this)
        return machineLinker.attachMachine(EventConfig())
    }

    fun <State> Config<State>.createPusher(initialState: State): Pusher<State> {
        return createMachine(initialState)
    }

    fun <State> Config<State>.createObserver(initialState: State): Observer<State> {
        return createMachine(initialState)
    }
}