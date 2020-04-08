package com.example.androidtask.front.fragments.editHabit

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.androidtask.R
import com.example.androidtask.logic.database.Habit
import com.example.androidtask.logic.HabitPriority
import com.example.androidtask.logic.HabitType
import kotlinx.android.synthetic.main.fragment_edit_habit.*
import java.lang.IllegalArgumentException

class EditHabitFragment : Fragment() {
    private val viewModel: EditHabitViewModel by viewModels()

    companion object {
        const val ACTION_TYPE = "actionType"
    }

    enum class ActionTypes {
        ADD,
        EDIT;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val habit: Habit = it.getSerializable(
                Habit.HABIT) as? Habit
                ?: Habit(
                    "",
                    "",
                    HabitPriority.Low,
                    HabitType.GOOD,
                    Int.MIN_VALUE,
                    Int.MIN_VALUE,
                    Habit.COLOR
                )
            viewModel.title.value = habit.title
            viewModel.description.value = habit.description
            viewModel.priority.value = habit.priority
            viewModel.type.value = habit.type
            viewModel.progress.value = habit.progress
            viewModel.periodicity.value = habit.periodicity
            viewModel.id.value = habit.id

            viewModel.actionType.value = try {
                ActionTypes.valueOf(it.getString(ACTION_TYPE, ""))
            } catch (e: IllegalArgumentException) {
                Toast.makeText(context, "Неизвестный тип действия ${viewModel.actionType.value}", Toast.LENGTH_LONG)
                    .show()
                findNavController().popBackStack()
                null
            }
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

        observeViewModel()

        ArrayAdapter.createFromResource(
            context!!,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
        }

        addNewHabbit.setOnClickListener {
            validateAndFillModel()
            viewModel.changeHabits()

            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.title.observe(viewLifecycleOwner, Observer { title -> titleHabit.setText(title) })
        viewModel.description.observe(viewLifecycleOwner, Observer { desc -> habitDescription.setText(desc) })
        viewModel.priority.observe(viewLifecycleOwner, Observer { priority ->
            val spinnerPosition = priority.ordinal
            spinnerPriority.setSelection(spinnerPosition)
        })
        viewModel.type.observe(viewLifecycleOwner, Observer { type -> when (type) {
            HabitType.GOOD -> goodType.isChecked = true
            HabitType.BAD -> badType.isChecked = true
            else -> {}
        }
        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {progress ->
            countProgress.setText(if (progress != null && progress == Int.MIN_VALUE) "" else progress.toString())
        })
        viewModel.periodicity.observe(viewLifecycleOwner, Observer { periodicity ->
            ePeriodicity.setText(if (periodicity != null && periodicity == Int.MIN_VALUE) "" else periodicity.toString())
        })
    }

    private fun validateAndFillModel() {
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

        val priority = when (spinnerPriority.selectedItem.toString()) {
            HabitPriority.Low.title -> HabitPriority.Low
            HabitPriority.Medium.title -> HabitPriority.Medium
            HabitPriority.High.title -> HabitPriority.High
            HabitPriority.Maximal.title -> HabitPriority.Maximal
            else -> {
                Toast.makeText(context, "Не заполнен приоритет", Toast.LENGTH_LONG).show()
                return
            }
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

        viewModel.title.value = title
        viewModel.description.value = description
        viewModel.priority.value = priority
        viewModel.progress.value = progress
        viewModel.periodicity.value = periodicity
        viewModel.type.value = newType
    }
}
