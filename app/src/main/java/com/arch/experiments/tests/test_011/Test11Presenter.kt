package com.arch.experiments.tests.test_011

import com.arch.experiments.tests.test_011.misc.Api
import io.reactivex.android.schedulers.AndroidSchedulers

class Test11Presenter(private val api: Api) : BasePresenter<NameModifierView>() {
    private var state: State = State("")

    fun onNameUpdated(name: String) {
        state = state.copy(name = name)
    }

    fun onUpdateTextClicked() {
        api.update(state.name)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onView { showProgress() } }
            .subscribe({ newName ->
                state = state.copy(name = newName)
                onView {
                    hideProgress()
                    setName(newName)
                }
            }, { error ->
                onView { showError(error) }
            })
            .addDisposable()
    }

    data class State(val name: String)
}