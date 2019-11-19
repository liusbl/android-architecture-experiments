package com.arch.experiments.tests.test_068

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import kotlinx.android.synthetic.main.test_068.*
import kotlinx.android.synthetic.main.test_068_list_item.view.*
import timber.log.Timber
import java.util.*

class Test68Fragment : BaseFragment(R.layout.test_068) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = Test68Adapter()
        recyclerView.adapter = adapter
        val list = listOf(
            ViewData("1", "01"),
            ViewData("2", "02"),
            ViewData("3", "03"),
            ViewData("4", "04"),
            ViewData("5", "05"),
            ViewData("6", "06"),
            ViewData("7", "07"),
            ViewData("8", "08"),
            ViewData("9", "09")
        )
        adapter.setItems(list)
    }

    class Test68Adapter : RecyclerView.Adapter<Test68ViewHolder>() {
        private val list = mutableListOf<ViewData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test68ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.test_068_list_item, parent, false)
            val viewHolder = Test68ViewHolder(itemView)
            Timber.v("test68 onCreateViewHolder $viewHolder")
            return viewHolder
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: Test68ViewHolder, position: Int) {
            Timber.d("test68 onBindViewHolder: $holder, position: $position, item: ${list[position]}")
            holder.bind(list[position])
        }

        override fun onViewAttachedToWindow(holder: Test68ViewHolder) {
            Timber.i("test68 onViewAttachedToWindow: $holder")
        }

        override fun onViewRecycled(holder: Test68ViewHolder) {
            Timber.w("test68 onViewRecycled: $holder")
        }

        override fun onViewDetachedFromWindow(holder: Test68ViewHolder) {
            Timber.e("test68 onViewDetachedFromWindow: $holder")
        }

        fun setItems(list: List<ViewData>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    class Test68ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = UUID.randomUUID().toString().take(6)

        fun bind(data: ViewData) {
            itemView.textView.text = data.text1
            itemView.editText.setText(data.text2)
        }

        override fun toString(): String {
            return id
        }
    }

    data class ViewData(val text1: String, val text2: String)
}