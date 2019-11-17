package com.arch.experiments.tests.test_057.lib_android

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

// BAD now every lambda has these strange methods
// TODO make them scoped to only fragments or activities somehow

    // BAD shouldn't need to pass initial state
    fun <State> ((State) -> Unit).createEventPusher(initialState: State) =
        EventConfig(onObserve = ::invoke).createPusher(initialState)

    fun (() -> Unit).createEventPusher() =
        EventConfig<Unit>(onObserve = { this.invoke() }).createPusher(Unit)
}
