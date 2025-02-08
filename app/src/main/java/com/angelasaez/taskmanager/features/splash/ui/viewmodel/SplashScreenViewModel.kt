package com.angelasaez.taskmanager.features.splash.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.angelasaez.taskmanager.preferences.AppPreferences.loadUsername
import kotlinx.coroutines.launch

class SplashScreenViewModel(application : Application) : AndroidViewModel(application) {
    val context = application

    private val _username = MutableLiveData("")
    val username: LiveData<String> get() = _username

    init {
        viewModelScope.launch {
            loadUsername(context).collect { loadedUsername ->
                _username.postValue(loadedUsername)
            }
        }
    }
}