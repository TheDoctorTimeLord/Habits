package com.example.androidtask.front.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.androidtask.MainActivity
import com.example.androidtask.R
import com.example.androidtask.front.FloatingActionButtonManager
import com.example.androidtask.front.HabitViewHolderAdapter
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitContainer
import com.example.androidtask.logic.HabitType
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        floatingActionButton.setOnClickListener {
            val bundle = Bundle()
                .apply {
                    putString(EditHabitFragment.ACTION_TYPE, EditHabitFragment.ActionTypes.ADD.name)
                }
            navController.navigate(R.id.action_nav_home_to_editHabitFragment, bundle)
        }

        val habits: HabitContainer = if (activity is MainActivity) {
            (activity as MainActivity).habits
        } else {
            HabitContainer()
        }

        habitListsViewPager.adapter = HabitListsAdapter(childFragmentManager,
            object : RecyclerViewConfigure {
                override fun configure(
                    context: Context,
                    recyclerView: RecyclerView,
                    habitType: HabitType
                ) {
                    val viewHabits = habits.habits.filter { habit -> habit.type == habitType }

                    recyclerView.adapter = HabitViewHolderAdapter(viewHabits) { position: Int, habit ->
                        val bundle = Bundle()
                            .apply {
                                putString(Habit.TITLE, habit.title)
                                putString(Habit.DESCRIPTION, habit.description)
                                putString(Habit.PRIORITY, habit.priority)
                                putString(Habit.TYPE, habit.type.title)
                                putInt(Habit.PROGRESS, habit.progress)
                                putInt(Habit.PERIODICITY, habit.periodicity)
                                putInt(MainActivity.POSITION, position)
                                putString(EditHabitFragment.ACTION_TYPE, EditHabitFragment.ActionTypes.EDIT.name)
                            }
                        findNavController().navigate(R.id.action_nav_home_to_editHabitFragment, bundle)
                    }
                    recyclerView.setOnScrollListener(FloatingActionButtonManager(floatingActionButton))

                    val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                    recyclerView.addItemDecoration(dividerItemDecoration)
                }
            })
        habitListsTabLayout.setupWithViewPager(habitListsViewPager)
    }
}
