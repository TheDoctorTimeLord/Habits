package com.example.androidtask.front.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitContainer
import com.example.androidtask.logic.HabitType

class HabitListViewModel : ViewModel() {
    private val goodHabitsForShow = MutableLiveData<List<Habit>>()
    private val badHabitsForShow = MutableLiveData<List<Habit>>()
    var filterSubstring: String = ""
        set(value) {
            goodHabitsForShow.value = filterByTitle(value, HabitType.GOOD)
            badHabitsForShow.value = filterByTitle(value, HabitType.BAD)
        }

    init {
        goodHabitsForShow.value = HabitContainer.instance.getList(HabitType.GOOD)
        badHabitsForShow.value = HabitContainer.instance.getList(HabitType.BAD)
    }

    fun getList(type: HabitType): MutableLiveData<List<Habit>> = when (type) {
        HabitType.GOOD -> goodHabitsForShow
        HabitType.BAD -> badHabitsForShow
    }

    private fun filterByTitle(containedSubstring: String, habitType: HabitType) : List<Habit>
            = HabitContainer.instance.getList(habitType)
                .filter { habit -> habit.title.contains(containedSubstring) }
}