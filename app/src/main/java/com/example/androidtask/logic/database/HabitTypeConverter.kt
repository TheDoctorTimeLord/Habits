package com.example.androidtask.logic.database

import androidx.room.TypeConverter
import com.example.androidtask.logic.HabitType
import java.lang.IllegalArgumentException

class HabitTypeConverter {
    @TypeConverter
    fun fromHabitType(habitType: HabitType) : String
            = habitType.title

    @TypeConverter
    fun toHabitType(titleHabitType: String) : HabitType = HabitType.extract(titleHabitType)
        ?: throw IllegalArgumentException("Illegal title HabitType: $titleHabitType")
}