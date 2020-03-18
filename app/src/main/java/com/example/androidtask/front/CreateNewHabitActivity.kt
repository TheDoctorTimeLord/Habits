package com.example.androidtask.front

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtask.MainActivity
import com.example.androidtask.R
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitPriority
import com.example.androidtask.logic.HabitType
import kotlinx.android.synthetic.main.fragment_edit_habit.*

class CreateNewHabitActivity : AppCompatActivity() {
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_edit_habit)

        setDefaultFieldValue(intent)

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
        }

        addNewHabbit.setOnClickListener(this::validateAndAddHabitData)
    }

    private fun setDefaultFieldValue(settingValue: Intent?) {
        if (settingValue == null) {
            return
        }

        val title = settingValue.getStringExtra(Habit.TITLE) ?: ""
        val description = settingValue.getStringExtra(Habit.DESCRIPTION) ?: ""
        val priority = settingValue.getStringExtra(Habit.PRIORITY) ?: ""
        val type = settingValue.getStringExtra(Habit.TYPE) ?: ""
        val progress = settingValue.getIntExtra(Habit.PROGRESS, Int.MIN_VALUE)
        val periodicity = settingValue.getIntExtra(Habit.PERIODICITY, Int.MIN_VALUE)
        position = settingValue.getIntExtra(MainActivity.POSITION, -1)

        titleHabit.setText(title)
        habitDescription.setText(description)
        spinnerPriority.setSelection(if (priority == "") 0 else HabitPriority.valueOf(priority).ordinal)
        when (type) {
            HabitType.GOOD.title -> goodType.isChecked = true
            HabitType.BAD.title -> badType.isChecked = true
        }
        countProgress.setText(if (progress == Int.MIN_VALUE) "" else progress.toString())
        ePeriodicity.setText(if (periodicity == Int.MIN_VALUE) "" else periodicity.toString())
    }

    private fun validateAndAddHabitData(view: View) {
        val title = if (titleHabit.text.toString() != "")
            titleHabit.text.toString()
        else {
            Toast.makeText(applicationContext, "Не заполнено название привычки", Toast.LENGTH_LONG).show()
            return
        }

        val description = if (habitDescription.text.toString() != "")
            habitDescription.text.toString()
        else {
            Toast.makeText(applicationContext, "Не заполнено описание привычки", Toast.LENGTH_LONG).show()
            return
        }

        val priority = if (spinnerPriority.selectedItem.toString() != "")
            spinnerPriority.selectedItem.toString()
        else {
            Toast.makeText(applicationContext, "Не заполнен приоритет", Toast.LENGTH_LONG).show()
            return
        }

        val type = if (radioGroup.checkedRadioButtonId != -1) {
            val butText = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
            try {
                HabitType.extract(butText)
                butText
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Выбранный тип не поддерживается", Toast.LENGTH_LONG).show()
                return
            }
        }
        else {
            Toast.makeText(applicationContext, "Не выбран тип привычки", Toast.LENGTH_LONG).show()
            return
        }

        val progress = if (countProgress.text.toString() != "")
            countProgress.text.toString()
        else {
            Toast.makeText(applicationContext, "Неверный формат количества выполения", Toast.LENGTH_LONG).show()
            return
        }

        val periodicity = if (ePeriodicity.text.toString() != "")
            ePeriodicity.text.toString()
        else {
            Toast.makeText(applicationContext, "Неверный формат периодичности", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent().apply {
            putExtra(Habit.TITLE, title)
            putExtra(Habit.DESCRIPTION, description)
            putExtra(Habit.PRIORITY, priority)
            putExtra(Habit.PROGRESS, progress)
            putExtra(Habit.PERIODICITY, periodicity)
            putExtra(Habit.TYPE, type)
            putExtra(Habit.COLOR, Habit.COLOR)
            if (position != -1) {
                putExtra(MainActivity.POSITION, position.toString())
            }

        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
