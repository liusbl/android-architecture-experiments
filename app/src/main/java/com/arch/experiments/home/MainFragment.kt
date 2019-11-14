package com.arch.experiments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import kotlinx.android.synthetic.main.activity_main_item.view.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val fragmentListProvider = FragmentListProvider()
    private lateinit var adapter: SingleViewTypeAdapter<Fragment>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = javaClass.simpleName

        adapter = SingleViewTypeAdapter(R.layout.activity_main_item) { itemView, fragment ->
            itemView.button.text = fragment.javaClass.simpleName
            itemView.button.setOnClickListener {
                (activity as MainActivity).showFragment(fragment)
            }
        }

        recyclerView.adapter = adapter
        adapter.setItems(fragmentListProvider.getList())
    }
}