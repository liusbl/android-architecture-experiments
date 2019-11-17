package com.arch.experiments.tests.test_058

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_058.test.Test58ParentFragment

class Test58Fragment : BaseFragment(R.layout.test_058) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test58ParentFragment())
            .commit()
    }
}