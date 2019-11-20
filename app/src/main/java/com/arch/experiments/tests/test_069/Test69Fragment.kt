package com.arch.experiments.tests.test_069

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import kotlinx.android.synthetic.main.test_069.*
import kotlinx.android.synthetic.main.test_069_list_item.view.*
import kotlinx.android.synthetic.main.test_069_list_item.view.textView
import kotlinx.android.synthetic.main.test_069_list_item_2.view.*
import timber.log.Timber
import java.util.*

class Test69Fragment : BaseFragment(R.layout.test_069) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = Test69Adapter()
        recyclerView.adapter = adapter
        val random = Random()
        val list = (1..50).map {
            val addNewline = random.nextBoolean()
            if (addNewline) {
                ViewData("$it\nNewline")
            } else {
                ViewData(it.toString())
            }
        }
        adapter.setItems(list)
    }

    class Test69Adapter : RecyclerView.Adapter<Test69ViewHolder>() {
        private val list = mutableListOf<ViewData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test69ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.test_069_list_item, parent, false)
            val viewHolder = Test69ViewHolder(itemView)
            Timber.v("test69 onCreateViewHolder $viewHolder")
            return viewHolder
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: Test69ViewHolder, position: Int) {
            Timber.d("test69 onBindViewHolder: $holder, position: $position, item: ${list[position]}")
            holder.bind(list[position])
        }

        override fun onViewAttachedToWindow(holder: Test69ViewHolder) {
            Timber.i("test69 onViewAttachedToWindow: $holder")
        }

        override fun onViewRecycled(holder: Test69ViewHolder) {
            Timber.w("test69 onViewRecycled: $holder")
        }

        override fun onViewDetachedFromWindow(holder: Test69ViewHolder) {
            Timber.e("test69 onViewDetachedFromWindow: $holder")
        }

        fun setItems(list: List<ViewData>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    class Test69ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = UUID.randomUUID().toString().take(6)

        fun bind(data: ViewData) {
            itemView.textView.text = data.text
            val adapter = Test69Adapter2()
            itemView.recyclerView.adapter = adapter

            val random = Random()
            val list = (1..50).map {
                val addNewline = random.nextBoolean()
                ViewData2(it.toString(), addNewline)
            }
            adapter.setItems(list)
        }

        override fun toString(): String = id
    }

    class Test69Adapter2 : RecyclerView.Adapter<Test69ViewHolder2>() {
        private val list = mutableListOf<ViewData2>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Test69ViewHolder2 {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.test_069_list_item_2, parent, false)
            val viewHolder = Test69ViewHolder2(itemView)
            Timber.v("test69_2 onCreateViewHolder $viewHolder")
            return viewHolder
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: Test69ViewHolder2, position: Int) {
            Timber.d("test69_2 onBindViewHolder: $holder, position: $position, item: ${list[position]}")
            holder.bind(list[position])
        }

        override fun onViewAttachedToWindow(holder: Test69ViewHolder2) {
            Timber.i("test69_2 onViewAttachedToWindow: $holder")
        }

        override fun onViewRecycled(holder: Test69ViewHolder2) {
            Timber.w("test69_2 onViewRecycled: $holder")
        }

        override fun onViewDetachedFromWindow(holder: Test69ViewHolder2) {
            Timber.e("test69_2 onViewDetachedFromWindow: $holder")
        }

        fun setItems(list: List<ViewData2>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    class Test69ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = UUID.randomUUID().toString().take(6)

        fun bind(data: ViewData2) {
            itemView.textView.text = data.text
            itemView.textView2.text = data.text
        }

        override fun toString(): String = id
    }

    data class ViewData(val text: String)

    data class ViewData2(val text: String, val isDouble: Boolean)
}