package com.arch.experiments.tests.test_027

class Test27ViewModel : BaseViewModel() {
    val textLiveData = createMutableLiveData<String>()

    fun onButtonClicked() {
        val text = textLiveData.value
        textLiveData.postValue(text + text)
    }
}