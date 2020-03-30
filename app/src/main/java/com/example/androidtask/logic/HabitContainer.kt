package com.example.androidtask.logic

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.androidtask.logic.database.Habit
import com.example.androidtask.logic.database.HabitsDatabase

class HabitContainer private constructor() {
    private object HOLDER {
        val INSTANCE = HabitContainer()
    }

    companion object {
        val instance: HabitContainer by lazy { HOLDER.INSTANCE }
    }

    private val goodHabits = MutableLiveData<List<Habit>>()
    private val badHabits = MutableLiveData<List<Habit>>()
    private lateinit var database: HabitsDatabase

    fun add(habit: Habit) {
        database.habitDao().insert(habit)
    }

    fun edit(habit: Habit) {
        database.habitDao().update(habit)
    }

    fun delete(habit: Habit) {
        database.habitDao().delete(habit)
    }

    fun getList(type: HabitType): LiveData<List<Habit>> = when (type) {
        HabitType.GOOD -> goodHabits
        HabitType.BAD -> badHabits
    }

    fun initialize(applicationContext: Context, owner: LifecycleOwner) {
        database = Room.databaseBuilder(
            applicationContext,
            HabitsDatabase::class.java,
            "Habits"
        ).allowMainThreadQueries().build()
        database.habitDao().getGoodHabits().observe(owner, Observer {
            goodHabits.value = it
        })
        database.habitDao().getBadHabits().observe(owner, Observer {
            badHabits.value = it
        })
    }
}