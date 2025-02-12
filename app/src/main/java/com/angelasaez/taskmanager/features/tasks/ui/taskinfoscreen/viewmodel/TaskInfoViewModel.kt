package com.angelasaez.taskmanager.features.tasks.ui.taskinfoscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskInfoViewModel : ViewModel() {

    private val _saveConfirmation  = MutableLiveData<Boolean>()
    val saveConfirmation: LiveData<Boolean> = _saveConfirmation

}