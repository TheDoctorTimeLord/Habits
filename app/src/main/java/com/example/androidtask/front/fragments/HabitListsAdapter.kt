package com.example.androidtask.front.fragments

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitContainer
import com.example.androidtask.logic.HabitType

class HabitListsAdapter(
    fragmentManager: FragmentManager,
    private val recyclerViewConfigure: RecyclerViewConfigure
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence? = when(position) {
        0 -> HabitType.GOOD.title
        else -> HabitType.BAD.title
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> HabitListFragment.newInstance(recyclerViewConfigure, HabitType.GOOD)
        else -> HabitListFragment.newInstance(recyclerViewConfigure, HabitType.BAD)
    }
}
