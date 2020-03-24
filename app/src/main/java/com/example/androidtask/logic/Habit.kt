package com.example.androidtask.logic

import java.io.Serializable

class Habit (
    val title: String,
    val description: String,
    val priority: String,
    val type: HabitType,
    val progress: Int,
    val periodicity: Int,
    val color: String
) : Serializable {

    var id: Int? = null

    companion object {
        const val COLOR = "color"
        const val HABIT = "habit"
    }
}