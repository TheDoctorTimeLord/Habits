package com.example.androidtask.front.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.logic.HabitType
import kotlinx.android.synthetic.main.fragment_habit_list.*
import java.io.Serializable

class HabitListFragment : Fragment() {

    private lateinit var callbackRecyclerView: RecyclerViewConfigure
    private lateinit var habitType: HabitType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            callbackRecyclerView = (it.getSerializable(RECYCLER_VIEW_CONFIGURE) as RecyclerViewConfigure)
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

        callbackRecyclerView.configure(context!!, recyclerView, habitType)
    }

    companion object {
        private const val HABIT_TYPE = "habits"
        private const val RECYCLER_VIEW_CONFIGURE = "recyclerViewConfigure"

        @JvmStatic
        fun newInstance(recyclerViewConfigure: RecyclerViewConfigure, habitType: HabitType) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(HABIT_TYPE, habitType)
                    putSerializable(RECYCLER_VIEW_CONFIGURE, recyclerViewConfigure)
                }
            }
    }
}

interface RecyclerViewConfigure : Serializable {
    fun configure(context: Context, recyclerView: RecyclerView, habitType: HabitType)
}
