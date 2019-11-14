package com.arch.experiments.common.lists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class SingleViewTypeAdapter<T>(
    @LayoutRes private val itemLayout: Int,
    diffCallback: DiffUtil.ItemCallback<T> = DefaultDiffUtilItemCallback(),
    private val onBind: (itemView: View, item: T) -> Unit
) : RecyclerView.Adapter<SingleViewTypeAdapter.SimpleViewHolder<T>>() {
    private val listDiffer = AsyncListDiffer<T>(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return SimpleViewHolder(
            view,
            onBind
        )
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    override fun onBindViewHolder(viewHolder: SimpleViewHolder<T>, position: Int) {
        viewHolder.bind(listDiffer.currentList[position])
    }

    fun setItems(items: List<T>) {
        listDiffer.submitList(items)
    }

    class SimpleViewHolder<T>(
        itemView: View,
        private val onBind: (itemView: View, item: T) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {
            onBind(itemView, item)
        }
    }
}
