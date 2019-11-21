package com.arch.experiments.tests.test_070

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseViewModelFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)

    protected fun <Value, State : BaseState<UiEffect>, UserAction : Any, UiEffect : BaseEffect>
            BaseViewModel<State, UserAction, UiEffect>.observe(
        getStateValue: (State) -> Value,
        setValue: (Value) -> Unit
    ) {
        states.observe(viewLifecycleOwner, StateObserver(getStateValue, setValue))
    }
}
