package com.arch.experiments.common.extensions

fun <E> List<E>.set(current: E, new: E): List<E> = toMutableList().apply {
    set(indexOf(current), new)
}