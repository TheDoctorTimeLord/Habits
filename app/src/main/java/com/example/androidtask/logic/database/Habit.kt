package com.example.androidtask.logic.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidtask.logic.HabitPriority
import com.example.androidtask.logic.HabitType
import java.io.Serializable

@Entity
@TypeConverters(HabitTypeConverter::class, HabitPriorityConverter::class)
class Habit (
    val title: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val progress: Int,
    val periodicity: Int,
    val color: String
) : Serializable {

    @PrimaryKey(autoGenerate = true) var id: Int? = null

    companion object {
        const val COLOR = "color"
        const val HABIT = "habit"
    }
}