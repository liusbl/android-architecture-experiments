package com.arch.experiments.tests.test_040.test_with_sigis

import android.widget.ImageView

class ExampleFragment2 {
    val imageView: ImageView = TODO()

    fun onCreate() {
        imageView.setOnClickListener { }
    }
}

enum class ImageViewState {
    Selected, Unselected
}

class Presenter {
    var state: ImageViewState = ImageViewState.Unselected

    fun onLongClicked() {
        state = ImageViewState.Selected
    }

    fun onClicked() {
        if (state == ImageViewState.Selected) {
//            view.showDialog()
        }
    }
}