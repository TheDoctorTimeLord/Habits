package com.example.androidtask.logic.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Habit::class], version = 1)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitDao() : HabitDao
}