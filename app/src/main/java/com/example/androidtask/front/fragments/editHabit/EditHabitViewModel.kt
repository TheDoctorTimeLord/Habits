package com.example.androidtask.front.fragments.editHabit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.logic.HabitContainer
import com.example.androidtask.logic.HabitPriority
import com.example.androidtask.logic.HabitType
import com.example.androidtask.logic.database.Habit

class EditHabitViewModel : ViewModel() {
    val actionType = MutableLiveData<EditHabitFragment.ActionTypes>()
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val priority = MutableLiveData<HabitPriority>()
    val type = MutableLiveData<HabitType>()
    val progress = MutableLiveData<Int>()
    val periodicity = MutableLiveData<Int>()
    val id = MutableLiveData<Int>()

    fun changeHabits() {
        when (actionType.value) {
            EditHabitFragment.ActionTypes.ADD -> {
                HabitContainer.instance.add(
                    Habit(
                        getIfNotNull(title.value),
                        getIfNotNull(description.value),
                        getIfNotNull(priority.value),
                        getIfNotNull(type.value),
                        getIfNotNull(progress.value),
                        getIfNotNull(periodicity.value),
                        Habit.COLOR
                    )
                )
            }
            EditHabitFragment.ActionTypes.EDIT -> {
                val habit = Habit(
                    getIfNotNull(title.value),
                    getIfNotNull(description.value),
                    getIfNotNull(priority.value),
                    getIfNotNull(type.value),
                    getIfNotNull(progress.value),
                    getIfNotNull(periodicity.value),
                    Habit.COLOR
                )
                habit.id = id.value
                HabitContainer.instance.edit(habit)
            }
        }
    }

    @Throws(IllegalStateException::class)
    private fun <T> getIfNotNull(arg: T?) : T {
        if (arg == null)
            throw IllegalStateException("Argument is null")
        return arg
    }
}
