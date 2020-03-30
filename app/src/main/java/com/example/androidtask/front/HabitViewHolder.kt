package com.example.androidtask.front

import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.logic.database.Habit
import com.example.androidtask.logic.HabitPriority
import kotlinx.android.extensions.LayoutContainer

class HabitViewHolder(
    override val containerView: View,
    private val editEvent: (habit: Habit) -> Unit,
    private val deleteEvent: (habit: Habit) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val habitTitle: TextView = containerView.findViewById(R.id.habitTitleW)
    private val habitDescription: TextView = containerView.findViewById(R.id.habitDescription)
    private val priorityIndicator: View = containerView.findViewById(R.id.priorityIndicator)
    private val habitPeriodicity: TextView = containerView.findViewById(R.id.habitPeriodicity)
    private val editButton: ImageButton = containerView.findViewById(R.id.editButton)
    private val deleteButton: ImageButton = containerView.findViewById(R.id.deleteButton)

    fun bind(habit: Habit) {
        val periodicity = "${habit.progress} раз каждые ${habit.periodicity} ${getCorrectDayText(habit.periodicity)}"

        habitTitle.text = habit.title
        habitDescription.text = habit.description
        habitPeriodicity.text = periodicity
        priorityIndicator.setBackgroundColor(Color.parseColor(habit.priority.color))

        editButton.setOnClickListener {
            editEvent(habit)
        }

        deleteButton.setOnClickListener {
            deleteEvent(habit)
        }
    }

    private fun getCorrectDayText(periodicity: Int): String = when {
            periodicity % 10 == 1 && periodicity / 10 != 1 -> "день"
            periodicity % 10 < 5 && periodicity / 10 != 1 -> "дня"
            else -> "дней"
        }
}