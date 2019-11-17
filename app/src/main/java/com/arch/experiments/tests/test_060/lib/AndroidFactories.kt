package com.arch.experiments.tests.test_060.lib

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.arch.experiments.common.widgets.AppEditText

interface AndroidFactories {
    fun AppEditText.getTextConfig() =
        StateConfig(onObserve = ::setSafeText, onPush = ::onTextChanged)

    fun TextView.getTextConfig() = StateConfig<String>(onObserve = ::setText)

    fun View.getVisibilityConfig() = StateConfig(onObserve = ::isVisible.setter)

    fun View.getClickConfig() =
        EventConfig<Unit>(onPush = { onClick -> setOnClickListener { onClick(Unit) } })

    fun Context.getToastConfig() = EventConfig<String>(onObserve = { message ->
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    })

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
