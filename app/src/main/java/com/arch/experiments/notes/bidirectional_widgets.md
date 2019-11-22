## Explain

- Don't notify "programmer" updates to widgets when state is changed from user side.
    This could result in useless callbacks or infinite loops.
    Example:
        1. TextWatcher for EditText is attached
        a. StateHandler state changes are observed by EditText
        2. User inputs text
        3. TextWatcher for EditText is notified by the framework
        4. TextWatcher dispatches Action to StateHandler
        5. Action updates the state in StateHandler
        6. EditText observes change from StateHandler
        7. EditText changes text, even though it's the same.

- TODO: Check how text in EditText is set from keyboard, maybe there is some way to hack something.
TextKeyListener

- Override EditText class and create methods setSafeText and onTextChanged
    Problems:

- Create EditText.setSafeText and remove/reattach listeners via reflection
    Problems:

- Create EditText.setSafeText and only set text if it doesn't match
    Problems:

- Use LayoutInflater to replace EditText with SafeEditTexts (somehow??)

- Using tags and two extension methods (ask Rimvydas/Vaidas for clarificiation), probably the best solution now

- For Checkbox, you could use setOnClickListener instead of setOnCheckedChange

- Some kind of value buffer solution:
```kotlin
    private var valueBuffer = mutableListOf<Any?>()

    fun <T> LiveData<T>.observe(onObserve: (T) -> Unit) {
        observe(this@Test8Fragment, Observer { value ->
            synchronized(valueBuffer) {
                if (valueBuffer.contains(value)) {
                    onObserve(value)
                    valueBuffer.remove(value)
                } else {
                    valueBuffer.clear()
                }
            }
        })
    }

    fun <T> LiveData<T>.postValue(value: T) {
        synchronized(valueBuffer) {
            valueBuffer.add(value)
            (this as MutableLiveData<T>).postValue(value)
        }
    }
```