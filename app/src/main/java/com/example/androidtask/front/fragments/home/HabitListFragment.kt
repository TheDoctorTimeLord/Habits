package com.example.androidtask.front.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.front.FloatingActionButtonManager
import com.example.androidtask.front.HabitViewHolderAdapter
import com.example.androidtask.front.fragments.editHabit.EditHabitFragment
import com.example.androidtask.logic.HabitContainer
import com.example.androidtask.logic.database.Habit
import com.example.androidtask.logic.HabitType
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitListFragment : Fragment() {

    private lateinit var habitType: HabitType
    private val viewModel: HabitListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitType = (it.getSerializable(HABIT_TYPE) as HabitType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter =
            HabitViewHolderAdapter({ habit ->
                val bundle = Bundle()
                    .apply {
                        putSerializable(Habit.HABIT, habit)
                        putString(
                            EditHabitFragment.ACTION_TYPE,
                            EditHabitFragment.ActionTypes.EDIT.name
                        )
                    }
                findNavController().navigate(
                    R.id.action_nav_home_to_editHabitFragment,
                    bundle
                )
            }, {habit ->
                HabitContainer.instance.delete(habit)
            })
        recyclerView.setOnScrollListener(
            FloatingActionButtonManager(
                activity!!.findViewById(R.id.floatingActionButton)
            )
        )

        viewModel.getList(habitType).observe(viewLifecycleOwner, Observer { habits ->
            (recyclerView.adapter as HabitViewHolderAdapter).habits = habits
            (recyclerView.adapter as HabitViewHolderAdapter).notifyDataSetChanged()
        })

        val dividerItemDecoration =
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    companion object {
        private const val HABIT_TYPE = "habits"

        @JvmStatic
        fun newInstance(habitType: HabitType) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HABIT_TYPE, habitType)
                }
            }
    }
}
