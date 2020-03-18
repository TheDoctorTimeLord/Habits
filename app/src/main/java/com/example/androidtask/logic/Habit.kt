package com.example.androidtask.logic

class Habit (
    val title: String,
    val description: String,
    val priority: String,
    val type: HabitType,
    val progress: Int,
    val periodicity: Int,
    val color: String
) {

    companion object {
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val PRIORITY = "priority"
        const val TYPE = "type"
        const val PROGRESS = "progress"
        const val PERIODICITY = "periodicity"
        const val COLOR = "color"
    }
}