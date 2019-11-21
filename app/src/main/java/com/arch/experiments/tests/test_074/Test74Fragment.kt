package com.arch.experiments.tests.test_074

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.common.extensions.inflate
import com.arch.experiments.tests.test_074.lib.AndroidMachines
import com.arch.experiments.tests.test_074.lib.MachineWithLinker
import kotlinx.android.synthetic.main.test_073.*
import kotlinx.android.synthetic.main.test_073_list_item.view.*

@Suppress("LocalVariableName")
class Test74Fragment : BaseFragment(R.layout.test_074), AndroidMachines {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test74Presenter()

        val adapter = Adapter()
        recyclerView.adapter = adapter

        val list = (1..50).map {
            if (it % 5 == 0) {
                ViewData(it.toString(), true)
            } else {
                ViewData(it.toString(), false)
            }
        }

        adapter.setItems(list)
    }

    class Adapter : RecyclerView.Adapter<ViewHolder>() {
        private val linkersList = mutableListOf<Linker>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = parent.inflate(R.layout.test_073_list_item)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return linkersList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(linkersList[position])
        }

        override fun onViewRecycled(holder: ViewHolder) {
            holder.unbind(linkersList[holder.adapterPosition])
        }

        fun setItems(list: List<ViewData>) {
            list.forEach { item ->
                // TODO: This could be done async
                val linker = Linker(
                    MachineWithLinker.create(item.text),
                    MachineWithLinker.create(item.isChecked)
                )
                linkersList.add(linker)
            }
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(linkers: Linker) {
            linkers.apply {
                textLink.setViewConfig(itemView.editText.getTextConfig())
                switchLink.setViewConfig(itemView.someSwitch.getCheckConfig())
            }
        }

        fun unbind(linkers: Linker) {
            linkers.textLink.clearViewConfig()
            linkers.switchLink.clearViewConfig()
        }
    }

    data class ViewData(val text: String, val isChecked: Boolean)

    class Linker(
        val textLink: MachineWithLinker<String>,
        val switchLink: MachineWithLinker<Boolean>
    ) : AndroidMachines
}