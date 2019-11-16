package com.arch.experiments.tests.test_005

import androidx.annotation.ColorInt
import com.arch.experiments.R
import com.arch.experiments.tests.test_005.Test5StateHandler.*
import com.arch.experiments.tests.test_005.Test5StateHandler.UserAction.*
import com.arch.experiments.tests.test_005.misc.MemeItem
import com.arch.experiments.tests.test_005.misc.NameLoader
import io.reactivex.Observable

class Test5StateHandler constructor(
    private val nameLoader: NameLoader
) : BaseStateHandler<State, UserAction, UiEffect>() {
    override val defaultState = State(
        false,
        "",
        "",
        0,
        listOf(),
        UiEffect.NameLoadError(Throwable())
    ) // TODO Should actually start with some empty effect

    override fun updateState(action: UserAction, state: State) = when (action) {
        is ViewCreated -> state.copy(
            items = listOf(
                MemeItem(R.drawable.guy, "No code, no bugs"),
                MemeItem(R.drawable.shrug, "Guess i'll die"),
                MemeItem(R.drawable.cj, "Ah shit"),
                MemeItem(R.drawable.pain, "Hide the pain"),
                MemeItem(R.drawable.dyatlov, "Not great not terrible"),
                MemeItem(R.drawable.guy, "aaa"),
                MemeItem(R.drawable.shrug, "bbb"),
                MemeItem(R.drawable.cj, "ccc"),
                MemeItem(R.drawable.pain, "ddd"),
                MemeItem(R.drawable.dyatlov, "eee"),
                MemeItem(R.drawable.guy, "111"),
                MemeItem(R.drawable.shrug, "222"),
                MemeItem(R.drawable.cj, "333"),
                MemeItem(R.drawable.pain, "444"),
                MemeItem(R.drawable.dyatlov, "555")
            )
        )
        is NameChanged -> state.copy(name = action.name)
        is SetNameClicked -> state
        is MemeClicked -> state
        is NoteChanged -> state.copy(items = state.items.toMutableList().apply {
            this[indexOf(action.item)] = action.item.copy(note = action.note)
        })
    }

    override fun updateStateAsync() = listOf(updateName)

    private val updateName = onUserAction(SetNameClicked::class.java)
        .flatMap {
            nameLoader.load(currentState.name)
                .toObservable()
                .map { currentState.copy(isLoading = false, name = it) }
                .onErrorResumeNext { throwable: Throwable ->
                    Observable.just(currentState.copy(isLoading = false, effect = UiEffect.NameLoadError(throwable)))
                }
                .startWith { emitter -> emitter.onNext(currentState.copy(isLoading = true)) }
        }

    data class State(
        val isLoading: Boolean,
        val name: String,
        val greeting: String,
        @ColorInt val nameColor: Int,
        val items: List<MemeItem>,
        override val effect: UiEffect
    ) : StateWithEffect<UiEffect>

    sealed class UserAction {
        object ViewCreated : UserAction()

        data class NameChanged(val name: String) : UserAction()

        object SetNameClicked : UserAction()

        data class MemeClicked(val item: MemeItem) : UserAction()

        data class NoteChanged(val note: String, val item: MemeItem) : UserAction()
    }

    sealed class UiEffect : Effect {
        data class NameLoadError(val error: Throwable) : UiEffect()
    }
}