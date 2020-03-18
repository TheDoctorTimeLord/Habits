package com.example.androidtask.front.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidtask.logic.Habit

class HabitListsAdapter(
    activity: FragmentActivity,
    private val habits: List<Habit>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> HabitListFragment.newInstance()
        else -> HabitListFragment.newInstance()
    }

}
