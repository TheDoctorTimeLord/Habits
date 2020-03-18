package com.example.androidtask.front.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.MainActivity
import com.example.androidtask.R
import com.example.androidtask.front.CreateNewHabitActivity
import com.example.androidtask.front.HabitViewHolderAdapter
import com.example.androidtask.logic.Habit
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_editHabitFragment)
        }

        recyclerView.adapter = HabitViewHolderAdapter(emptyList()) { position: Int, habit ->
            val startSecondActivity = Intent(
                context,
                CreateNewHabitActivity::class.java
            ).apply {
                putExtra(Habit.TITLE, habit.title)
                putExtra(Habit.DESCRIPTION, habit.description)
                putExtra(Habit.PROGRESS, habit.progress)
                putExtra(Habit.PRIORITY, habit.priority)
                putExtra(Habit.PERIODICITY, habit.periodicity)
                putExtra(Habit.TYPE, habit.type.title)
                putExtra(MainActivity.POSITION, position)
            }
            this.startActivityForResult(startSecondActivity, MainActivity.EDIT_HABIT_REQUEST_CODE)
        }

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}
