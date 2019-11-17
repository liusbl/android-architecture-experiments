package com.arch.experiments.tests.test_055

class Test55ViewModel : BaseViewModel() {
    val text1LiveData = createMutableLiveData<String>()
    val text2LiveData = createMutableLiveData<String>()

    fun onClicked() {
        val value = listOf("1", "a", "b", "Z", "S", "e", "3").shuffled()
            .joinToString(separator = "")
        text1LiveData.postValue(value)
        text2LiveData.postValue(value)
    }

    fun onText1Changed() {
        text1LiveData.postValue(text1LiveData.value + "x")
    }

    fun onText2Changed() {
        text2LiveData.postValue(text2LiveData.value + "0")
    }
}