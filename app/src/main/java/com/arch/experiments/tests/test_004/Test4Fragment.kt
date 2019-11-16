package com.arch.experiments.tests.test_004

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.test_002.*

class Test4Fragment : BaseFragment(R.layout.test_004) {
    private val reducer = LoginReducer()
    private val disposables = CompositeDisposable()
    private lateinit var toaster: Toaster

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toaster = Toaster(context!!)
        handleStateChanges()
    }

    private fun handleStateChanges() {
        doOnStateUpdate(LoginReducer.State::isLoading) { isLoading ->
            progressBar.isVisible = isLoading
        }

        // TODO avoid infinite loops somehow
        // TODO create performance benchmarks (maybe they could be done automatically?)
        // TODO check performance impact
        // I have a feeling that these should be done even redundantly (have to think about that more)
        // , so that view always has the latest state (ideally view should have state, but we cannot choose)
        doOnStateUpdate(
            LoginReducer.State::password,
            passwordEditText::setSafeText
        ) // TODO this should be made safe, so that text watcher is not notified again, because infinite loop can occur
        doOnStateUpdate(
            LoginReducer.State::username,
            usernameEditText::setSafeText
        ) // TODO this should be made safe, so that text watcher is not notified again, because infinite loop can occur

        // Does this mean that animations are part of business logic?

        // TODO this is more akin to the idea of News, but that would introduce state to view, which I would like to get rid of.
        // TODO that means, that you should be able to specify how long the Toast is showing and so you cannot use the default Android Toast.
        // TODO also, that means that error message has to be set separately from showing the Toast, since these are two different states.
        // TODO that also means duration
        doOnStateUpdate(LoginReducer.State::error, toaster::setError)
        doOnStateUpdate(LoginReducer.State::isShowingError) { isShowingError ->
            // TODO maybe you should call state.error ? Instead of setting error for toaster ? So that there wouldn't be two separate calls for toaster
            if (isShowingError) toaster.show() else toaster.hide()
        }
    }

    private fun <T> doOnStateUpdate(
        something: LoginReducer.State.() -> T,
        function: (T) -> Unit
    ) {
        reducer.stateUpdates
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { newState ->
                function(newState.something())
            }.addTo(disposables)
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}