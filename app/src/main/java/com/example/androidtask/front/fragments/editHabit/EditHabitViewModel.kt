package com.example.androidtask.front.fragments.editHabit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditHabitViewModel : ViewModel() {
    val actionType = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val priority = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val progress = MutableLiveData<Int>()
    val periodicity = MutableLiveData<Int>()
    val id = MutableLiveData<Int>()
}
