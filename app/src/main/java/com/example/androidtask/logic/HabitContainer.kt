package com.example.androidtask.logic

import java.io.Serializable

class HabitContainer : Serializable {
    val habitsGood = mutableListOf<Habit>()
    val habitsBad = mutableListOf<Habit>()

    fun add(habit: Habit) {
        val list = getList(habit.type)
        list.add(habit)
    }

    fun edit(habit: Habit, position: Int, oldType: HabitType?) {
        if (habit.type == oldType) {
            val list = getList(habit.type)
            list[position] = habit
        } else if (oldType != null) {
            val list = getList(oldType)
            list.removeAt(position)
            val oList = getList(habit.type)
            oList.add(habit)
        }
    }

    fun getList(type: HabitType): MutableList<Habit>  = when (type) {
            HabitType.GOOD -> habitsGood
            HabitType.BAD -> habitsBad
        }
}