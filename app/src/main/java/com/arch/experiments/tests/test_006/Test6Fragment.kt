package com.arch.experiments.tests.test_006

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import com.arch.experiments.tests.test_006.Test6ViewModel.*
import com.arch.experiments.tests.test_006.misc.MemeItem
import kotlinx.android.synthetic.main.test_006.*
import kotlinx.android.synthetic.main.test_006_list_item.view.*

class Test6Fragment : BaseFragment(R.layout.test_006) {
    private lateinit var viewModel: Test6ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[Test6ViewModel::class.java]
        viewModel.dispatch(UserAction.ViewCreated)
        val adapter = SingleViewTypeAdapter<MemeItem>(R.layout.test_006_list_item) { itemView, item ->
            itemView.imageView.setImageResource(item.icon)
            itemView.noteEditText.setSafeText(item.note)
            itemView.noteEditText.onTextChanged { text ->
                viewModel.dispatch(UserAction.NoteChanged(text, item))
            }
            itemView.showButton.setOnClickListener {
                viewModel.dispatch(UserAction.SetNameClicked)
            }
        }
        nameEditText.onTextChanged { text -> viewModel.dispatch(UserAction.NameChanged(text)) }
        recyclerView.adapter = adapter
        viewModel.observe(State::name, nameEditText::setSafeText)
        viewModel.observe(State::isLoading) { isLoading -> progressBar.isVisible = isLoading }
        viewModel.observe(State::greeting, greetingTextView::setText)
        viewModel.observe(State::items, adapter::setItems)
        viewModel.observe(State::effect, ::handleEffect)
    }

    private fun handleEffect(uiEffect: UiEffect) {
        when (uiEffect) {
            is UiEffect.NameLoadError -> context?.showToast(uiEffect.error.message)
            is UiEffect.NameLoadSuccess -> Toast.makeText(context!!, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun <Value> BaseViewModel<State, UserAction, UiEffect>.observe(
        getStateValue: (State) -> Value,
        setValue: (Value) -> Unit
    ) {
        states.observe(viewLifecycleOwner, Observer { state ->
            val value = getStateValue(state)
            if (value is Effect) {
                if (value != UiEffect.EmptyEffect) {
                    setValue(value)
                    clearEffect()
                }
            } else {
                setValue(value)
            }
        })
    }
}