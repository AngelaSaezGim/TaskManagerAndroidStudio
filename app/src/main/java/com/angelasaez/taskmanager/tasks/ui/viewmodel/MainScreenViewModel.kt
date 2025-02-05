package com.angelasaez.taskmanager.tasks.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    private val _taskName = MutableLiveData<String>()
    val taskName: LiveData<String> = _taskName

    fun onTaskNameChange(taskName: String) {
        _taskName.value = taskName
    }

    fun onTaskNameDelete() {
//        _taskName.value = ""
        // Se debe usar postValue porque siempre se va a hacer uso de esta función
        //  desde una corrutina en TaskViewModel
        _taskName.postValue("")
    }
}


