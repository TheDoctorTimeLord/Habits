package com.example.androidtask.front.fragments.home

import androidx.fragment.app.*
import com.example.androidtask.logic.HabitType

class HabitListsAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence? = when(position) {
        0 -> HabitType.GOOD.title
        else -> HabitType.BAD.title
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> HabitListFragment.newInstance(HabitType.GOOD)
        else -> HabitListFragment.newInstance(HabitType.BAD)
    }
}
