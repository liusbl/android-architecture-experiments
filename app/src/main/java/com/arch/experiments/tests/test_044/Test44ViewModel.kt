package com.arch.experiments.tests.test_044

import com.arch.experiments.tests.test_044.misc.Combiner
import com.arch.experiments.tests.test_044.misc.StringProvider

// BAD: Cannot be generic. Also "hard" to inject
class Test44ViewModel : BaseViewModel() {
    private val stringProvider = StringProvider()
    private val combiner = Combiner()

    // BAD: You're not forced to provide initial state, therefore view state can be in improper state

    // BAD: Storing internal controller state (clickCount) at the same place as view state (text1, text2),
    //  which doesn't seem consistent. Feels like they should be separate
    private var clickCount = 0

    // BAD: text1 and text2 is duplicated with text1LiveData and text2LiveData,
    //  even though they represent the same UI field
    //  That means that each field has to be synchronized twice, which requires extra caution.
    private var text1 = stringProvider.getInitialText1()
    private var text2 = stringProvider.getInitialText2()

    // BAD: The Controller shouldn't be responsible to decide whether the view should do something
    //  Once, or on each configuration change. UI should decide this part.
    val toastLiveData = createSingleLiveData<String>()

    val clickCountLiveData = createMutableLiveData(stringProvider.getClickCountText(clickCount))
    val progressVisibilityLiveData = createMutableLiveData(false)
    val text1LiveData = createMutableLiveData(text1)
    val text2LiveData = createMutableLiveData(text2)
    val resultLiveData = createMutableLiveData(stringProvider.getInitialResult())

    fun onButtonClicked() {
        clickCount++
        clickCountLiveData.postValue(stringProvider.getClickCountText(clickCount))
        toastLiveData.postValue(stringProvider.getStartedText())
        progressVisibilityLiveData.postValue(true)
        resultLiveData.postValue("No Result")

        // BAD: Here you can make a mistake since if you forget to set initial state to text1LiveData
        //  then it will not work like this:
//        combiner.combine(text1LiveData.value, text2LiveData.value)
        combiner.combine(text1, text2)
            .subscribe({ result ->
                toastLiveData.postValue(stringProvider.getFinishedText())
                progressVisibilityLiveData.postValue(false)
                resultLiveData.postValue(stringProvider.getResultText(result))
            }, {}).attachToViewModel()
    }

    fun onText1Changed(text: String) {
        // BAD:  this is not synchronized with text1LiveData.
        //  And you cannot do text1LiveData.postValue(text1),
        //  since that will notify the view again.
        //  So really, I don't even know the exact solution to this in MVVM
        text1 = text
    }

    // BAD: So much code and unclarity for so little value:
    //  1. Updating text2 in one way,
    //  2. Then updating text1 in another way,
    //  3. Then also updating text1 in YET ANOTHER way.
    fun onText2Changed(text: String) {
        text2 = text
        text1 += "*"
        text1LiveData.postValue(text1)
    }

    // BAD: in general it seems like mutableLiveData will
    //  usually be outdated with bi-directional components.
}