package com.arch.experiments.tests.test_064.lib

interface Filter {
    fun filter(value: String, emitValue: (String) -> Unit)
}

class Filter1 : Filter {
    override fun filter(value: String, emitValue: (String) -> Unit) {
        if (value.contains("A")) {
            emitValue(value)
        }
    }
}

class Filter2 : Filter {
    override fun filter(value: String, emitValue: (String) -> Unit) {
        if (value.contains("\\d")) {
            emitValue(value)
        }
    }
}

class FilterWrapper(val data: String) {
    fun apply(filter: Filter): FilterWrapper {
        filter.filter(data) {
            FilterWrapper(it)
        }

        return FilterWrapper(data)
    }
}

class Usage {
    fun a() {
        val wrapper = FilterWrapper("sdf34")
            .apply(Filter1())
            .apply(Filter2())
    }
}