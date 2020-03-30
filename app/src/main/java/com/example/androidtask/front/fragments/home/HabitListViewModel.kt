package com.example.androidtask.front.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Observer
import com.example.androidtask.logic.HabitContainer
import com.example.androidtask.logic.HabitType
import com.example.androidtask.logic.database.Habit
import java.util.*

class HabitListViewModel : ViewModel() {
    private val goodHabitsForShow = MutableLiveData<List<Habit>>()
    private val badHabitsForShow = MutableLiveData<List<Habit>>()
    var filterSubstring: String = ""
        set(value) {
            goodHabitsForShow.value = filterByTitle(value, HabitType.GOOD)
            badHabitsForShow.value = filterByTitle(value, HabitType.BAD)
        }

    private val goodObserver: Observer<List<Habit>>
    private val badObserver: Observer<List<Habit>>

    init {
        goodObserver = Observer { list ->
            goodHabitsForShow.value = list
        }
        HabitContainer.instance.getList(HabitType.GOOD).observeForever(goodObserver)
        badObserver = Observer { list ->
            badHabitsForShow.value = list
        }
        HabitContainer.instance.getList(HabitType.BAD).observeForever(badObserver)
    }

    fun getList(type: HabitType): MutableLiveData<List<Habit>> = when (type) {
        HabitType.GOOD -> goodHabitsForShow
        HabitType.BAD -> badHabitsForShow
    }

    private fun filterByTitle(containedSubstring: String, habitType: HabitType) : List<Habit>
            = HabitContainer.instance.getList(habitType).value
                ?.filter { habit ->
                    habit.title.toLowerCase(Locale.ROOT).contains(containedSubstring.toLowerCase(Locale.ROOT))
                } ?: emptyList()

    override fun onCleared() {
        super.onCleared()
        HabitContainer.instance.getList(HabitType.GOOD).removeObserver(goodObserver)
        HabitContainer.instance.getList(HabitType.BAD).removeObserver(badObserver)
    }
}