package com.arch.experiments.home

import com.arch.experiments.tests.test_001.Test1Fragment
import com.arch.experiments.tests.test_002.Test2Fragment
import com.arch.experiments.tests.test_003.Test3Fragment

class FragmentListProvider {
    fun getList() = listOf(
        Test3Fragment(),
        Test2Fragment(),
        Test1Fragment()
    )
}