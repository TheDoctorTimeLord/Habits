package com.example.androidtask.front

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.logic.Habit

class HabitViewHolderAdapter(
    private val event: (position: Int, habit: Habit) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    var habits: List<Habit> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.element_list, parent, false), event)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }
}