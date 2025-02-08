package com.angelasaez.taskmanager.features.tasks.ui.layout.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.angelasaez.taskmanager.preferences.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ESTO ES PARA EL SCAFFOLD (lo compartiran MainTaskScreen y InfoTaskScreen
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    init {
        loadUsername()
    }

    // Cargar el nombre de usuario desde las preferencias
    private fun loadUsername() {
        viewModelScope.launch(Dispatchers.IO) {
            AppPreferences.loadUsername(getApplication()).collect { user ->
                _username.postValue(user)
            }
        }
    }

    // Cerrar sesión (se hará desde la topbar)
    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            AppPreferences.removeUsername(getApplication()) // getApplication() acceder al contexto
            _username.postValue("")
        }
    }

}