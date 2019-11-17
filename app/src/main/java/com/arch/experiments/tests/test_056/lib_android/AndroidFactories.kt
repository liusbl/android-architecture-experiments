package com.arch.experiments.tests.test_056.lib_android

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.arch.experiments.common.widgets.AppEditText

fun AppEditText.getTextConfig() =
    StateConfig(onObserve = ::setSafeText, onPush = ::onTextChanged)

fun TextView.getTextConfig() = StateConfig<String>(onObserve = ::setText)

fun View.getVisibilityConfig() = StateConfig(onObserve = ::isVisible.setter)

fun View.getClickConfig() =
    EventConfig<Unit>(onPush = { onClick -> setOnClickListener { onClick(Unit) } })

fun Context.getToastConfig() = EventConfig<String>(onObserve = { message ->
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
})

// BAD shouldn't need to pass initial state
fun <State> ((State) -> Unit).createEventPusher(initialState: State) =
    EventConfig(onObserve = ::invoke).createPusher(initialState)