package com.arch.experiments.tests.test_049.test

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_049.lib_android.EventMachineFactory
import com.arch.experiments.tests.test_049.lib_android.createClickObserver
import com.arch.experiments.tests.test_049.lib_android.createTextMachine
import com.arch.experiments.tests.test_049.lib_android.createTextPusher
import com.arch.experiments.tests.test_049.lib_core.ContainerProvider
import kotlinx.android.synthetic.main.test_049_parent.*

class Test49ParentFragment : BaseFragment(R.layout.test_049_parent),
    ContainerProvider<Test49ChildContainer> {
    lateinit var container: Test49ParentContainer
    var childFragment: Test49ChildFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        container = Test49ParentContainer(
            parentButton.createClickObserver(),
            EventMachineFactory<String>(onObserve = { initialText ->
                childFragment = Test49ChildFragment.newInstance(initialText)
                childFragmentManager.beginTransaction()
                    .replace(R.id.container, childFragment!!)
                    .commit()
            }).createPusher(""),
            // TODO it's annoying when you have to provide initial state for events!

            parentEditText.createTextMachine(""),
            parentTextView.createTextPusher("")
        )
        val presenter = Test49ParentPresenter(this)
        presenter.start(container)
    }

    override fun getContainer() = childFragment?.childContainer
}