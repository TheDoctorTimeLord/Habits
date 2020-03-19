package com.example.androidtask.logic

import java.lang.IllegalArgumentException

enum class HabitType(val title: String) {
    GOOD("Хорошая"),
    BAD("Плохая");

    companion object {
        fun extract(title: String?): HabitType? {
            for(element in values()) {
                if (element.title == title) {
                    return element
                }
            }
            return null
        }
    }
}
