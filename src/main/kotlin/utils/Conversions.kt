package com.github.ratneshjain40.utils

fun dayFromInt(day: Int): String {
    return day.toString().padStart(2, '0')
}