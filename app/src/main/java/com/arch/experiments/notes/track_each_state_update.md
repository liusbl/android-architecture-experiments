Consider the pros / cons


---------
Example 1:

loginButton.setOnClickListener {
    presenter.onLoginClicked(usernameEditText.text, passwordEditText.text)
}

vs

usernameEditText.onTextChanged { text -> presenter.onUsernameChanged(text) }
passwordEditText.onTextChanged { text -> presenter.onPasswordChanged(text) }
loginButton.setOnClickListener { presenter.onLoginClicked() }

---------
Example 2:

(Sigis example)

class ExampleFragment {
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

If you are not passing long click, then it's easier to handle state,
but in that case you are quietly changing the state.