package com.arch.experiments.tests.test_011

import com.arch.experiments.tests.test_011.misc.Api
import io.reactivex.android.schedulers.AndroidSchedulers

class Test11Presenter(private val api: Api) : BasePresenter<NameModifierView>() {
    fun onUpdateNameClicked(name: String) {
        api.update(name)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onView { showProgress() } }
            .subscribe({ newName ->
                onView {
                    hideProgress()
                    setName(newName)
                }
            }, { error ->
                onView { showError(error) }
            })
            .addDisposable()
    }
}