package com.arch.experiments.tests.test_005

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import com.arch.experiments.tests.test_005.Test5StateHandler.*
import com.arch.experiments.tests.test_005.misc.MemeItem
import kotlinx.android.synthetic.main.test_005.*
import kotlinx.android.synthetic.main.test_005_list_item.view.*

class Test5Fragment : BaseFragment(R.layout.test_005) {
    private lateinit var stateHandler: Test5StateHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = SingleViewTypeAdapter<MemeItem>(R.layout.test_005_list_item) { itemView, item ->
            itemView.imageView.setImageResource(item.icon)
            itemView.noteEditText.setSafeText(item.note)
            itemView.noteEditText.onTextChanged { text ->
                stateHandler.dispatch(
                    UserAction.NoteChanged(
                        text,
                        item
                    )
                )
            }
        }

        recyclerView.adapter = adapter

        stateHandler.start()
        stateHandler.dispatch(UserAction.ViewCreated)
        stateHandler.onStateChange(State::items, adapter::setItems)

        stateHandler.onStateChange(State::isLoading) { progressBar.isVisible = it }

        setNameButton.setOnClickListener { stateHandler.dispatch(UserAction.SetNameClicked) }

        stateHandler.onStateChange(State::name) { nameEditText.setSafeText(it) }
        nameEditText.onTextChanged { stateHandler.dispatch(UserAction.NameChanged(it)) }

        stateHandler.onStateChange(State::greeting) { greetingTextView.text = it }

        stateHandler.onUiEffect { effect ->
            when (effect) {
                is UiEffect.NameLoadError ->
                    Toast.makeText(context!!, effect.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}