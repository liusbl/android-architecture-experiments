As an example, I will be using EditText

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

-