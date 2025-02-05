package com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    private val _taskName = MutableLiveData<String>()
    val taskName: LiveData<String> = _taskName

    private val _userame = MutableLiveData<String>()
    val userame: LiveData<String> = _userame

    fun onTaskNameChange(taskName: String) {
        _taskName.value = taskName
    }

    fun onTaskNameDelete() {
//        _taskName.value = ""
        // Se debe usar postValue porque siempre se va a hacer uso de esta función
        //  desde una corrutina en TaskViewModel
        _taskName.postValue("")
    }

    fun loadUsername() {
//        AppPreferences.loadUser
    }
}


