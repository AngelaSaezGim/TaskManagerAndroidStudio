package com.angelasaez.taskmanager.features.onboarding.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.angelasaez.taskmanager.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnBoardingScreenViewModel (application : Application) : AndroidViewModel(application) {
    val context = application

    private val _usernameValue = MutableLiveData<String>()
    val usernameValue: LiveData<String> = _usernameValue

    fun saveUsername(usernameParam: String){
        viewModelScope.launch(Dispatchers.IO) {
            AppPreferences.saveUsername(context, usernameParam)
            _usernameValue.postValue(usernameParam)
        }
    }
}