package com.arch.experiments.tests.test_070

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.arch.experiments.R
import com.arch.experiments.common.extensions.showToast
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import com.arch.experiments.tests.test_070.Test70ViewModel.*
import com.arch.experiments.tests.test_070.Test70ViewModel.UiEffect.*
import com.arch.experiments.tests.test_070.misc.MemeItem
import kotlinx.android.synthetic.main.test_070.*
import kotlinx.android.synthetic.main.test_070_list_item.view.*

class Test70Fragment : BaseViewModelFragment(R.layout.test_070) {
    private lateinit var viewModel: Test70ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[Test70ViewModel::class.java]
        val adapter = SingleViewTypeAdapter<MemeItem>(R.layout.test_070_list_item) { itemView, item ->
            itemView.imageView.setImageResource(item.icon)
            itemView.noteEditText.setSafeText(item.note)
            itemView.noteEditText.onTextChanged { text ->
                viewModel.dispatch(UserAction.NoteChanged(text, item))
            }
            itemView.showButton.setOnClickListener {
                viewModel.dispatch(UserAction.SetNameClicked)
            }
        }
        recyclerView.adapter = adapter

        viewModel.dispatch(UserAction.ViewCreated)
        nameEditText.onTextChanged { text -> viewModel.dispatch(UserAction.NameChanged(text)) }
        setNameButton.setOnClickListener { viewModel.dispatch(UserAction.SetNameClicked) }

        viewModel.observe(State::name) { nameEditText.setSafeText(it) }
        viewModel.observe(State::isLoading) { isLoading -> progressBar.isVisible = isLoading }
        viewModel.observe(State::greeting) { greetingTextView.text = it }
        viewModel.observe(State::items) { adapter.setItems(it) }
        viewModel.observe(State::effect, ::handleEffect)
    }

    private fun handleEffect(uiEffect: UiEffect?) {
        when (uiEffect) {
            is NameLoadStarted -> context?.showToast("Started")
            is NameLoadError -> context?.showToast(uiEffect.error.message)
            is NameLoadSuccess -> context?.showToast("Success!")
        }
    }
}