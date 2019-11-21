package com.arch.experiments.tests.test_071

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_071.lib.*
import kotlinx.android.synthetic.main.test_071.*
import kotlinx.android.synthetic.main.test_071_list_item.view.*

class Test71Fragment : BaseFragment(R.layout.test_071), AndroidMachines {
    override val scopeHandler = ScopeHandler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val presenter = Test71Presenter()
        presenter.start(
            editText1.createTextMachine("init1_"),
            editText2.createTextMachine("init2_")
        )

        val adapter = Adapter()
        recyclerView.adapter = adapter

        adapter.setItems(
            listOf("a", "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "o")
                    + listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        )
    }

    override fun onDestroyView() {
        scopeHandler.clearConfigs()
        super.onDestroyView()
    }

    class Adapter : RecyclerView.Adapter<ViewHolder>(), AndroidMachines {
        private val linkers = mutableListOf<MachineLinker<String>>()
        private val machines = mutableListOf<Machine<String>>()

        override val scopeHandler = ScopeHandler()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.test_071_list_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return linkers.count()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val config = holder.itemView.noteEditText.getTextConfig()
            linkers[position].setConfig(machines[position], config)
        }

        override fun onViewRecycled(holder: ViewHolder) {
            val position = holder.adapterPosition
            val linker = linkers[position]
            val machine = machines[position]
            linker.clearConfig(machine)
        }

        fun setItems(list: List<String>) {
            list.forEach { item ->
                val linker = MachineLinker(item)
                val machine = linker.attachMachine(StateConfig())
                linkers.add(linker)
                machines.add(machine)
            }
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}