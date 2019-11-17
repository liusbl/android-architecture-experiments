package com.arch.experiments.tests.test_057

import android.os.Bundle
import android.view.View
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import com.arch.experiments.tests.test_057.test.Test57ParentFragment

class Test57Fragment : BaseFragment(R.layout.test_057) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, Test57ParentFragment())
            .commit()
    }
}