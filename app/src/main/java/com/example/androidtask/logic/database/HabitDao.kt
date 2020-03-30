package com.example.androidtask.logic.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.androidtask.logic.HabitType

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit WHERE habit.type == 'Хорошая'")
    fun getGoodHabits() : LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE habit.type == 'Плохая'")
    fun getBadHabits() : LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)
}