package com.example.androidtask.logic.database

import androidx.room.TypeConverter
import com.example.androidtask.logic.HabitPriority
import java.lang.IllegalArgumentException

class HabitPriorityConverter {
    @TypeConverter
    fun fromHabitType(habitType: HabitPriority) : String
            = habitType.title

    @TypeConverter
    fun toHabitType(titleHabitType: String) : HabitPriority = HabitPriority.extract(titleHabitType)
        ?: HabitPriority.valueOf(titleHabitType)
}