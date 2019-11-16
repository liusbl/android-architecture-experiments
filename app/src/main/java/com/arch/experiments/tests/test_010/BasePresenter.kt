package com.arch.experiments.tests.test_010

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<V> {
    private val disposables = CompositeDisposable()

    private var view: V? = null

    fun takeView(view: V) {
        this.view = view
    }

    fun onView(action: V.() -> Unit) {
        view?.let(action::invoke)
    }

    fun Disposable.addDisposable() {
        disposables.add(this)
    }

    fun dropView() {
        disposables.clear()
        view = null
    }
}