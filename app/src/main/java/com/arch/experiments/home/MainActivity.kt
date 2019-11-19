package com.arch.experiments.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arch.experiments.R
import com.arch.experiments.common.extensions.replaceFragment
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
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
