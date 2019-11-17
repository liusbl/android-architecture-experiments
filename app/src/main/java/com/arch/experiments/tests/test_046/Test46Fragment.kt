package com.arch.experiments.tests.test_046

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_046.android_lib.createClickMachine
import com.arch.experiments.tests.test_046.android_lib.createTextMachine
import com.arch.experiments.tests.test_046.android_lib.createVisibilityMachine
import kotlinx.android.synthetic.main.test_046.*
import kotlinx.android.synthetic.main.test_046_list_item.view.*

// TODO barely working, bad all around
class Test46Fragment : BaseFragment(R.layout.test_046) {
    private val presenter by lazy { Iteration4Presenter() }
    private lateinit var adapter: Iteration4ListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.start(
            { onMachineCreated: (MachineContainer) -> Unit ->
                adapter = Iteration4ListAdapter(onMachineCreated)
                recyclerView.adapter = adapter
            }, { viewData: List<Test46ViewData> ->
                adapter.addContainer(viewData)
            })
    }
}

class Iteration4ListAdapter(
    private val onCreateMachineContainer: (MachineContainer) -> Unit
) : RecyclerView.Adapter<Iteration4ViewHolder>() {
    private val list = mutableListOf<Test46ViewData>()

    fun addContainer(viewData: List<Test46ViewData>) {
        list.addAll(viewData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Iteration4ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.test_046_list_item, parent, false)
        return Iteration4ViewHolder(itemView, onCreateMachineContainer)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Iteration4ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }
}

class Iteration4ViewHolder(
    itemView: View,
    private val onCreateMachineContainer: (MachineContainer) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: Test46ViewData) {
        val buttonMachine = itemView.button.createClickMachine()
        val progressMachine = itemView.progressBar.createVisibilityMachine(data.isProgressShowing)
        val textMachine = itemView.editText.createTextMachine(data.text)
        val container = MachineContainer(buttonMachine, progressMachine, textMachine)
        onCreateMachineContainer(container)
    }
}