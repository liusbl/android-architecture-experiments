package com.arch.experiments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arch.experiments.common.lists.SingleViewTypeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_item.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter =
            SingleViewTypeAdapter<Fragment>(R.layout.activity_main_item) { itemView, fragment ->
                itemView.button.text = fragment.javaClass.simpleName
                itemView.button.setOnClickListener {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.rootLayout, fragment)
                        .commit()
                }
            }
    }
}
