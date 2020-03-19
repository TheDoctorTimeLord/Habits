package com.example.androidtask.logic

import java.io.Serializable

class HabitContainer : Serializable {
    val habits = mutableListOf<Habit>()

    fun add(habit: Habit) {
        habits.add(habit)
    }

    fun edit(habit: Habit, position: Int) {
        habits[position] = habit
    }
}