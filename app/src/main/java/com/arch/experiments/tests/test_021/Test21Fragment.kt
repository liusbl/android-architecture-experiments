package com.arch.experiments.tests.test_021

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import com.arch.experiments.tests.test_021.Test21ViewModel.Action
import com.arch.experiments.tests.test_021.Test21ViewModel.State
import kotlinx.android.synthetic.main.test_021.*
import kotlinx.android.synthetic.main.test_021_list_item.view.*

class Test21Fragment : BaseFragment(R.layout.test_021) {
    private val viewModel = Test21ViewModel()
    private val adapter by lazy(::createAdapter)

    private fun createAdapter(): SingleViewTypeAdapter<ServerItem> {
        return SingleViewTypeAdapter(R.layout.test_021_list_item) { itemView, item ->
            itemView.shouldConnectCheckbox.setOnClickListener {
                viewModel.postAction(
                    Action.ShouldConnectChecked(
                        itemView.shouldConnectCheckbox.isChecked,
                        item
                    )
                )
            }

            itemView.titleTextView.text = item.title

            // TODO decide if this conditional should be part of logic,
            //  maybe there should be a separate ServerViewData item?
            val drawable = if (item.isConnected) R.drawable.ic_yes else R.drawable.ic_no
            itemView.isConnectedImageView.setImageResource(drawable)

            itemView.shouldConnectCheckbox.isChecked = item.shouldConnected
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.adapter = adapter

        viewModel.observe(State::list, adapter::setItems)
    }
}