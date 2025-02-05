package com.angelasaez.taskmanager.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

object AppPreferences {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="preferences")
    private val USERNAME = stringPreferencesKey("USERNAME")

    fun loadUsername(context: Context) = context.dataStore.data.map {
        preferences ->
        preferences[USERNAME] ?: ""
    }

    suspend fun saveUsername(context: Context, name: String){
        context.dataStore.edit {
            preferences ->
            preferences[USERNAME] = name
        }
    }

    suspend fun removeUsername(context: Context){
        context.dataStore.edit {
            preferences ->
            preferences.remove(USERNAME)
        }
    }


}