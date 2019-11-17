package com.arch.experiments.tests.test_043

import android.annotation.SuppressLint
import com.arch.experiments.tests.test_043.misc.Combiner
import com.arch.experiments.tests.test_043.misc.StringProvider

class Test43Presenter(
    private val stringProvider: StringProvider,
    private val combiner: Combiner,
    // BAD: Direct responsibility to tell view what to do
    private val view: Test43View
) {
    // BAD: You're not forced to provide initial state, therefore view state can be in improper state

    // BAD: Storing internal controller state (clickCount) at the same place as view state (text1, text2),
    //  which doesn't seem consistent. Feels like they should be separate
    private var clickCount = 0
    private var text1 = "init1"
    private var text2 = "init2"

    // BAD: No field for result, which is inconsistent
    //  You could add it, but since it is used only in one place, it would be weird.
    //  So neither solution is "elegant"

    // BAD: You are forced to call setter methods on initialization, this should be done by default
    //  Also, it's easy to forget to do this, thereby making view state unsynchronized from controller state
    fun onViewCreated() {
        view.setClickCountText(stringProvider.getClickCountText(clickCount))
        view.setText1(text1)
        view.setText2(text2)

        // BAD: since result doesn't have a field, you are forced to do inconsistent state initialization
        view.setResult("No Result")
    }

    @SuppressLint("CheckResult")
    fun onButtonClicked() {
        clickCount++
        view.setClickCountText(stringProvider.getClickCountText(clickCount))
        view.showToastMessage(stringProvider.getStartedText())
        view.setProgressVisibility(true)

        combiner.combine(text1, text2)
            .subscribe({ result ->
                view.showToastMessage(stringProvider.getFinishedText())
                view.setProgressVisibility(false)

                // BAD: Since result comes from external call, no field exists for it, even though it's part of state
                //  You could add field and set it here, as in:
                //      this.result = result
                //      view.setResult(result)
                //  But that looks highly boilerplate'y and
                //  also doesn't make a lot of sense since you won't be using it anywhere else

                view.setResult(stringProvider.getResultText(result))
            }, {})
    }

    // BAD: Much boilerplate just for updating view state from view.
    fun onText1Changed(text: String) {
        text1 = text
    }

    // BAD: Much boilerplate just for updating view state from view
    fun onText2Changed(text: String) {
        text2 = text

        // BAD: Here you have no choice but to remember to update both text1 internally
        //  and also set text1 for view, which is boilerplate and can be forgotten.
        //  If you do not do this, view state and presenter state will get unsynchronized
        text1 += "*"
        view.setText1(text1)
    }
}