package com.example.androidtask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androidtask.logic.Habit
import com.example.androidtask.logic.HabitType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    private val habits = mutableListOf<Habit>()
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_about), navigationDrawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationDrawer.setupWithNavController(navController)

        Log.d(DEBUG_TAG, getString(R.string.created))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == ADD_HABIT_REQUEST_CODE || requestCode == EDIT_HABIT_REQUEST_CODE)
            && resultCode == Activity.RESULT_OK && data != null)
        {
            val title = data.getStringExtra(Habit.TITLE)!!
            val description = data.getStringExtra(Habit.DESCRIPTION)!!
            val priority = data.getStringExtra(Habit.PRIORITY)!!
            val type = data.getStringExtra(Habit.TYPE)
            val progress = data.getStringExtra(Habit.PROGRESS)!!.toInt()
            val periodicity = data.getStringExtra(Habit.PERIODICITY)!!.toInt()
            val color = data.getStringExtra(Habit.COLOR)!!

            val habit = Habit(
                title,
                description,
                priority,
                HabitType.extract(type),
                progress,
                periodicity,
                color
            )

            if (requestCode == EDIT_HABIT_REQUEST_CODE) {
                val position = data.getStringExtra(POSITION)!!.toInt()
                habits[position] = habit
                //recyclerView.adapter?.notifyItemChanged(position)
            } else if (requestCode == ADD_HABIT_REQUEST_CODE) {
                habits.add(habit)
                //recyclerView.adapter?.notifyItemChanged(habits.size - 1)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val DEBUG_TAG = "MainActivity"
        const val POSITION = "position"
        const val ADD_HABIT_REQUEST_CODE = 1
        const val EDIT_HABIT_REQUEST_CODE = 2
    }
}
