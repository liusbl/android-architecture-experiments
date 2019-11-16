package com.arch.experiments.tests.test_011

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.arch.experiments.R
import com.arch.experiments.common.BaseFragment
import kotlinx.android.synthetic.main.test_010.*

class Test11Fragment : BaseFragment(R.layout.test_011), NameModifierView {
    lateinit var presenter: Test11Presenter

    private var textWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.takeView(this)
        nameEditText.onTextChanged { name ->
            presenter.onNameUpdated(name)
        }
        updateNameButton.setOnClickListener {
            presenter.onUpdateTextClicked()
        }
    }

    override fun setName(name: String) {
        nameEditText.removeTextChangedListener(textWatcher)
        nameEditText.setText(name)
        nameEditText.addTextChangedListener(textWatcher)
    }

    override fun showError(error: Throwable) {
        Toast.makeText(context!!, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        presenter.dropView()
        super.onDestroyView()
    }
}