package com.example.androidtask.logic

import com.example.androidtask.R

enum class HabitPriority(val title: String, val color: String) {
    Low("Низкий", "#009714"),
    Medium("Средний", "#EFAE08"),
    High("Высокий", "#8F0505"),
    Maximal("Максимальный", "#451003");

    companion object {
        fun extract(title: String) : HabitPriority? {
            for (el in values()) {
                if (el.title == title)
                    return el
            }
            return null
        }
    }
}