package com.arch.experiments.tests.test_062.lib

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.common.extensions.doAfterTextChanged

open class EventConfig<State>(
    override val onObserve: (state: State) -> Unit = {},
    override val onPush: (pushState: (State) -> Unit) -> Unit = {}
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = false
}

open class StateConfig<State>(
    override val onObserve: (state: State) -> Unit = {},
    override val onPush: (pushState: (State) -> Unit) -> Unit = {}
) : EmptyConfig<State>() {
    override val notifyObserverOnInit = true
}

abstract class EmptyConfig<State>(
    override val onObserve: (State) -> Unit = {},
    override val onPush: (push: (State) -> Unit) -> Unit = {}
) : Config<State> {
    override val onObserveTransform: (state: State) -> Unit = { state ->
    }
    override val onPushTransform: (state: State) -> Unit = { state ->
    }
}


interface Config<State> {
    val onObserve: (State) -> Unit
    val onPush: (push: (State) -> Unit) -> Unit
    val onObserveTransform: (state: State) -> Unit
    val onPushTransform: (state: State) -> Unit
    val notifyObserverOnInit: Boolean

//    fun transform() {
//
//    }
}

//fun <State> Config<State>.transform(): Config<State> {
//    this.onObserve
//}


//class AndroidConfig<State>(
//
//) : StateConfig<State>() {
//    private var lastValue: State? = null
//
//    override val onObserve: (State) -> Unit = { value: State ->
//        lastValue = value
////        editText.setText(value)
//        super.onObserve
//    }
//    override val onPush: (push: (State) -> Unit) -> Unit = super.onPush
//}

fun test00(editText: EditText): Config<String> {
    return StateConfig(
        onObserve = { value: String ->
        },
        onPush = { onTextChanged: (String) -> Unit ->
            editText.doAfterTextChanged { text ->
                onTextChanged(text)
            }
        }
    )
}

fun test0(editText: EditText): Config<String> {
    return StateConfig(
        onObserve = { value: String ->
            editText.setText(value)
        },
        onPush = { onTextChanged: (String) -> Unit ->
            editText.doAfterTextChanged { text ->
                onTextChanged(text)
            }
        }
    )
}

fun test1(editText: EditText): Config<String> {
    var lastValue: String? = null
    return StateConfig(
        onObserve = { value: String ->
            lastValue = value
            editText.setText(value)
        },
        onPush = { onTextChanged: (String) -> Unit ->
            editText.doAfterTextChanged { text ->
                if (lastValue != text) {
                    onTextChanged(text)
                }
                lastValue = null
            }
        }
    )
}

class TestConfig(
    editText: EditText
) : StateConfig<String>() {
    override val onObserve = { value: String ->
        if (true) {
            onObserveTransform(value)
        }
//        editText.setText(value)
    }
    override val onPush = { onTextChanged: (String) -> Unit ->
        editText.doAfterTextChanged { text ->
            if (true) {
                onPushTransform(text)
            }
//            onTextChanged(text)
        }
    }
}


class AndroidConfig<State>(
    override val onObserve: (State) -> Unit = {},
    override val onPush: (push: (State) -> Unit) -> Unit = {}
) : StateConfig<State>() {
    private var lastState: State? = null
    override val onObserveTransform = { state: State ->
        lastState = state
        super.onObserveTransform(state)
    }
    override val onPushTransform = { state: State ->
        if (lastState != state) {
            super.onPushTransform(state)
        }
        lastState = null
    }
}

class ListItemConfig<State>(
    config: Config<State>,
    private val viewHolder: RecyclerView.ViewHolder,
    private val position: Int
) : StateConfig<State>() {
    override val onObserve: (state: State) -> Unit = config.onObserve
    override val onPush: (pushState: (State) -> Unit) -> Unit = config.onPush
    override val onObserveTransform = { state: State ->
        if (viewHolder.adapterPosition == position) {
            super.onObserveTransform(state)
        }
    }
    override val onPushTransform = { state: State ->
        if (viewHolder.adapterPosition == position) {
            super.onPushTransform(state)
        }
    }
}


//}

//// PTR: I think the previous approach is better, because:
//      It's more explicit
//      Doesn't
//class AndroidConfig<State>(
////    val onObserveAction:
//// val onPushAction:
//) : StateConfig<State>() {
//    private var lastState: State? = null
//
//    override val onObserve: (State) -> Unit = { state ->
//        lastState = state
//    }
//    override val onPush: (push: (State) -> Unit) -> Unit = {
//
//    }
//
//
//}