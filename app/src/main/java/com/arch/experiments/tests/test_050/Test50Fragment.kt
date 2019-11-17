package com.arch.experiments.tests.test_050

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_050.test.Test50ParentFragment

class Test50Fragment : BaseFragment(R.layout.test_050) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test50ParentFragment())
            .commit()
    }
}