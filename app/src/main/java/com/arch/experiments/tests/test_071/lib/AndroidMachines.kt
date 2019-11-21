package com.arch.experiments.tests.test_071.lib

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

interface AndroidMachines {
    val scopeHandler: ScopeHandler

    fun View.createClickObserver(): Observer<Unit> {
        return getClickConfig().createObserver(Unit)
    }

    fun Context.createToastPusher(): Pusher<String> {
        return getToastConfig().createPusher("")
    }

    fun EditText.createTextMachine(initialText: String): Machine<String> {
        return getTextConfig().createMachine(initialText)
    }

    fun EditText.getTextConfig(): StateConfig<String> {
        return object : StateConfig<String>(AndroidTransformation()) {
            override fun observe(state: String) {
                this@getTextConfig.setText(state)
            }

            override fun onPush(push: (String) -> Unit) {
                this@getTextConfig.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(editable: Editable?) {
                        editable?.toString()?.let(push::invoke)
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // Empty
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        // Empty
                    }
                })
            }
        }
    }

    fun CompoundButton.getCheckConfig(): StateConfig<Boolean> {
        return object : StateConfig<Boolean>() {
            override fun observe(state: Boolean) {
                this@getCheckConfig.isChecked = state
            }

            override fun onPush(push: (Boolean) -> Unit) {
                this@getCheckConfig.setOnCheckedChangeListener { _, isChecked -> push(isChecked) }
            }
        }
    }

    fun TextView.getTextConfig(): StateConfig<String> {
        return object : StateConfig<String>() {
            override fun observe(state: String) {
                this@getTextConfig.text = state
            }
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

    fun View.getClickConfig(): EventConfig<Unit> {
        return object : EventConfig<Unit>() {
            override fun onPush(push: (Unit) -> Unit) {
                setOnClickListener { push(Unit) }
            }
        }
    }

    fun Context.getToastConfig(): EventConfig<String> {
        return object : EventConfig<String>() {
            override fun observe(state: String) {
                Toast.makeText(this@getToastConfig, state, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun <State> Config<State>.createMachine(initialState: State): Machine<State> {
        val machineLinker = MachineLinker(initialState)
        val viewMachine = machineLinker.attachMachine(this)
        val presenterMachine = machineLinker.attachMachine(EventConfig())
        scopeHandler.addLinkedMachines(machineLinker, viewMachine, presenterMachine)
        return presenterMachine
    }

    fun <State> Config<State>.createPusher(initialState: State): Pusher<State> {
        return createMachine(initialState)
    }

    fun <State> Config<State>.createObserver(initialState: State): Observer<State> {
        return createMachine(initialState)
    }
}