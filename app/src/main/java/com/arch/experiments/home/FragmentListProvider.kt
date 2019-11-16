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
import com.arch.experiments.tests.test_014.Test14Fragment
import com.arch.experiments.tests.test_015.Test15Fragment
import com.arch.experiments.tests.test_016.Test16Fragment
import com.arch.experiments.tests.test_017.Test17Fragment
import com.arch.experiments.tests.test_018.Test18Fragment
import com.arch.experiments.tests.test_019.Test19Fragment
import com.arch.experiments.tests.test_020.Test20Fragment
import com.arch.experiments.tests.test_021.Test21Fragment

class FragmentListProvider {
    // TODO add color to indicate whether the test is finished
    fun getList() = listOf(
        Test21Fragment(),
        Test20Fragment(),
        Test19Fragment(),
        Test18Fragment(),
        Test17Fragment(),
        Test16Fragment(),
        Test15Fragment(),
        Test14Fragment(),
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