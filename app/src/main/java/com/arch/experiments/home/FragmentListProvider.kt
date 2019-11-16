package com.arch.experiments.home

import com.arch.experiments.tests.test_001.Test1Fragment
import com.arch.experiments.tests.test_002.Test2Fragment
import com.arch.experiments.tests.test_003.Test3Fragment
import com.arch.experiments.tests.test_004.Test4Fragment
import com.arch.experiments.tests.test_005.Test5Fragment
import com.arch.experiments.tests.test_006.Test6Fragment
import com.arch.experiments.tests.test_007.Test7Fragment
import com.arch.experiments.tests.test_008.Test8Fragment
import com.arch.experiments.tests.test_009.Test9Fragment
import com.arch.experiments.tests.test_010.Test10Fragment
import com.arch.experiments.tests.test_011.Test11Fragment
import com.arch.experiments.tests.test_012.Test12Fragment
import com.arch.experiments.tests.test_013.Test13Fragment

class FragmentListProvider {
    fun getList() = listOf(
        Test13Fragment(),
        Test12Fragment(),
        Test11Fragment(),
        Test10Fragment(),
        Test9Fragment(),
        Test8Fragment(),
        Test7Fragment(),
        Test6Fragment(),
        Test5Fragment(),
        Test4Fragment(),
        Test3Fragment(),
        Test2Fragment(),
        Test1Fragment()
    )
}