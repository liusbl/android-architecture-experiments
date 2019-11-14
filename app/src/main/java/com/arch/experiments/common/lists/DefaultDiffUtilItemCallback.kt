package com.arch.experiments.common.lists

import androidx.recyclerview.widget.DiffUtil
import java.util.*

class DefaultDiffUtilItemCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

    override fun areContentsTheSame(oldItem: T, newItem: T) = Objects.equals(oldItem, newItem)
}