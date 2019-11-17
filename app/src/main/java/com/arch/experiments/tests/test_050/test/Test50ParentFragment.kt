package com.arch.experiments.tests.test_050.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_050.lib_android.EventMachineFactory
import com.arch.experiments.tests.test_050.lib_android.createClickObserver
import com.arch.experiments.tests.test_050.lib_android.createTextPusher
import com.arch.experiments.tests.test_050.lib_android.new_approach.getFactory
import com.arch.experiments.tests.test_050.lib_core.ContainerProvider
import com.arch.experiments.tests.test_050.lib_core.MachineLinker
import kotlinx.android.synthetic.main.test_050_parent.*

class Test50ParentFragment : BaseFragment(R.layout.test_050_parent),
    ContainerProvider<Test50ChildContainer> {
    lateinit var container: Test50ParentContainer
    var childFragment: Test50ChildFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Shouldn't be like this, state needs to be initialized via factory
        val parentEditTextLinker = MachineLinker(true, "")
        val presenterMachine = parentEditTextLinker.attachLinkedMachine()
        parentEditTextLinker.attachMachineViaFactory(parentEditText.getFactory())

        container = Test50ParentContainer(
            parentButton.createClickObserver(),
            EventMachineFactory<String>(onObserve = { initialText ->
                childFragment = Test50ChildFragment.newInstance(initialText)
                childFragmentManager.beginTransaction()
                    .replace(R.id.container, childFragment!!)
                    .commit()
            }).createPusher(""),
            // TODO it's annoying when you have to provide initial state for events!

            presenterMachine,
            parentTextView.createTextPusher("")
        )
        val presenter = Test50ParentPresenter(this)
        presenter.start(container)
    }

    override fun getContainer() = childFragment?.childContainer
}