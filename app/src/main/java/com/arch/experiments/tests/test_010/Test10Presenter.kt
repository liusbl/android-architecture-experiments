package com.arch.experiments.tests.test_010

import com.arch.experiments.tests.test_010.misc.Api
import io.reactivex.android.schedulers.AndroidSchedulers

class Test10Presenter(
    private val api: Api
) : BasePresenter<NameModifierView>() {
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