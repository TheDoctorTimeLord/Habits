package com.example.androidtask.front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.androidtask.MainActivity
import com.example.androidtask.R
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitPriority
import com.example.androidtask.logic.HabitType
import kotlinx.android.synthetic.main.fragment_edit_habit.*
import java.lang.IllegalArgumentException

class EditHabitFragment : Fragment() {

    private lateinit var actionType: String
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var priority: String
    private lateinit var type: String
    private var progress: Int? = null
    private var periodicity: Int? = null

    companion object {
        const val ACTION_TYPE = "actionType"
    }

    enum class ActionTypes {
        ADD,
        EDIT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionType = it.getString(ACTION_TYPE, "")
            title = it.getString(Habit.TITLE, "")
            description = it.getString(Habit.DESCRIPTION, "")
            priority = it.getString(Habit.PRIORITY, "")
            type = it.getString(Habit.TYPE, "")
            progress = it.getInt(Habit.PROGRESS, Int.MIN_VALUE)
            periodicity = it.getInt(Habit.PERIODICITY, Int.MIN_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_habit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enumActionType = try {
            ActionTypes.valueOf(actionType)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, "Неизвестный тип действия $actionType", Toast.LENGTH_LONG)
                .show()
            findNavController().popBackStack()
            null
        }

        setDefaultFieldValue()

        ArrayAdapter.createFromResource(
            context!!,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
        }

        addNewHabbit.setOnClickListener {
            if (activity is MainActivity && enumActionType != null) {
                validateAndChangeHabits(enumActionType, activity as MainActivity)
            }
        }
    }

    private fun setDefaultFieldValue() {
        titleHabit.setText(title)
        habitDescription.setText(description)
        val spinnerPosition = if (priority == "") 0 else HabitPriority.valueOf(priority).ordinal
        spinnerPriority.setSelection(spinnerPosition)
        when (type) {
            HabitType.GOOD.title -> goodType.isChecked = true
            HabitType.BAD.title -> badType.isChecked = true
        }
        countProgress.setText(if (progress != null && progress == Int.MIN_VALUE) "" else progress.toString())
        ePeriodicity.setText(if (periodicity != null && periodicity == Int.MIN_VALUE) "" else periodicity.toString())
    }

    private fun validateAndChangeHabits(actionType: ActionTypes, activity: MainActivity) {
        val title = if (titleHabit.text.toString() != "")
            titleHabit.text.toString()
        else {
            Toast.makeText(context, "Не заполнено название привычки", Toast.LENGTH_LONG).show()
            return
        }

        val description = if (habitDescription.text.toString() != "")
            habitDescription.text.toString()
        else {
            Toast.makeText(context, "Не заполнено описание привычки", Toast.LENGTH_LONG).show()
            return
        }

        val priority = if (spinnerPriority.selectedItem.toString() != "")
            spinnerPriority.selectedItem.toString()
        else {
            Toast.makeText(context, "Не заполнен приоритет", Toast.LENGTH_LONG).show()
            return
        }

        val newType = when(radioGroup.checkedRadioButtonId) {
            goodType.id -> HabitType.GOOD
            badType.id -> HabitType.BAD
            else -> {
                Toast.makeText(context, "Не выбран тип привычки", Toast.LENGTH_LONG).show()
                return
            }
        }

        val progress = if (countProgress.text.toString() != "")
            countProgress.text.toString().toInt()
        else {
            Toast.makeText(context, "Неверный формат количества выполения", Toast.LENGTH_LONG).show()
            return
        }

        val periodicity = if (ePeriodicity.text.toString() != "")
            ePeriodicity.text.toString().toInt()
        else {
            Toast.makeText(context, "Неверный формат периодичности", Toast.LENGTH_LONG).show()
            return
        }

        when (actionType) {
            ActionTypes.ADD -> {
                activity.habits.add(
                    Habit(title, description, priority, newType, progress, periodicity, Habit.COLOR)
                )
            }
            ActionTypes.EDIT -> {
                val position = arguments?.getInt(MainActivity.POSITION, -1)
                if (position != null && position != -1) {
                    activity.habits.edit(
                        Habit(title, description, priority, newType, progress, periodicity, Habit.COLOR),
                        position,
                        HabitType.extract(type)
                    )
                }
            }
        }

        findNavController().popBackStack()
    }
}
