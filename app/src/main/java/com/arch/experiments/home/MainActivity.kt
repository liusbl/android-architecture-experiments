package com.arch.experiments.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arch.experiments.R
import com.arch.experiments.common.extensions.replaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(
            R.id.fragmentContainer,
            MainFragment()
        )
    }

    fun showFragment(fragment: Fragment) {
        title = fragment.javaClass.simpleName
        replaceFragment(R.id.fragmentContainer, fragment)
    }
}
