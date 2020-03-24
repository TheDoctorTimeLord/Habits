package com.example.androidtask.front

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitPriority
import kotlinx.android.extensions.LayoutContainer

class HabitViewHolder(
    override val containerView: View,
    private val event: (position: Int, habit: Habit) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val habitTitle: TextView = containerView.findViewById(R.id.habitTitleW)
    private val habitDescription: TextView = containerView.findViewById(R.id.habitDescription)
    private val habitPriority: TextView = containerView.findViewById(R.id.habitPriority)
    private val habitPeriodicity: TextView = containerView.findViewById(R.id.habitPeriodicity)
    private val editButton: ImageButton = containerView.findViewById(R.id.editButton)

    fun bind(habit: Habit) {
        val periodicity = "${habit.progress}/${habit.periodicity}"
        val priority = try {
            HabitPriority.valueOf(habit.priority).title
        } catch (e: IllegalArgumentException) {
            habit.priority
        }

        habitTitle.text = habit.title
        habitDescription.text = habit.description
        habitPriority.text = priority
        habitPeriodicity.text = periodicity

        editButton.setOnClickListener {
            event(adapterPosition, habit)
        }
    }
}