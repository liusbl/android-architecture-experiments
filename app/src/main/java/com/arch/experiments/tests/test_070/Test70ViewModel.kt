package com.arch.experiments.tests.test_070

import com.arch.experiments.common.extensions.toObservable
import com.arch.experiments.tests.test_070.Test70ViewModel.*
import com.arch.experiments.tests.test_070.Test70ViewModel.UiEffect.*
import com.arch.experiments.tests.test_070.Test70ViewModel.UserAction.*
import com.arch.experiments.tests.test_070.misc.MemeItem
import com.arch.experiments.tests.test_070.misc.MemeListProvider
import com.arch.experiments.tests.test_070.misc.NameLoader
import io.reactivex.Observable

class Test70ViewModel : BaseViewModel<State, UserAction, UiEffect>(
    State(false, "", "", emptyList()), { copy() }
) {
    private val memeListProvider = MemeListProvider()
    private val nameLoader = NameLoader()

    override fun updateState(action: UserAction): Observable<State> = when (action) {
        is ViewCreated -> state.copy(items = memeListProvider.getList()).toObservable()
        is NameChanged -> state.copy(name = action.name).toObservable()
        is SetNameClicked -> updateName
        is MemeClicked -> state.toObservable()
        is NoteChanged -> state.copy(items = state.items.toMutableList().apply {
            this[indexOf(action.item)] = action.item.copy(note = action.note)
        }).toObservable()
    }

    private val updateName: Observable<State> = nameLoader.load(state.name)
        .toObservable()
        .map { name ->
            state.copy(isLoading = false, greeting = name).withEffect(NameLoadSuccess)
        }
        .onErrorResumeNext { throwable: Throwable ->
            Observable.just(state.copy(isLoading = false).withEffect(NameLoadError(throwable)))
        }
        .startWith { emitter -> emitter.onNext(state.copy(isLoading = true).withEffect(NameLoadStarted)) }

    sealed class UserAction {
        object ViewCreated : UserAction()

        data class NameChanged(val name: String) : UserAction()

        object SetNameClicked : UserAction()

        data class MemeClicked(val item: MemeItem) : UserAction()

        data class NoteChanged(val note: String, val item: MemeItem) : UserAction()
    }

    data class State(
        val isLoading: Boolean,
        val name: String,
        val greeting: String,
        val items: List<MemeItem>
    ) : BaseState<UiEffect>()

    sealed class UiEffect : BaseEffect {
        object NameLoadStarted : UiEffect()

        data class NameLoadError(val error: Throwable) : UiEffect()

        object NameLoadSuccess : UiEffect()
    }
}