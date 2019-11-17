package com.arch.experiments.tests.test_047

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment

class Test47Fragment : BaseFragment(R.layout.test_047) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test47ParentFragment())
            .commit()
    }
}