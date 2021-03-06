package com.arch.experiments.tests.test_029

import com.arch.experiments.tests.test_029.lib.BaseViewModel
import com.arch.experiments.tests.test_029.misc.Database
import com.arch.experiments.tests.test_029.misc.Greeter

class Test29ViewModel(
    private val db: Database,
    private val greeter: Greeter
)  : BaseViewModel<Test29ViewModel.State>(State(false, false, false, "", "")) {
//    override fun onStateChange(action: Action): Observable<State> {
//         TODO implement
//    }

    data class State(
        val isViewCreated: Boolean,
        val isGreetClicked: Boolean,
        val isLoading: Boolean,
        val name: String,
        val greeting: String
    )

    sealed class Effect {
        object ShowSuccess : Effect()

        object ShowFailure : Effect()
    }
}