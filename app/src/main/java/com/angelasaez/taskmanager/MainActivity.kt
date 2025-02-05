package com.angelasaez.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.angelasaez.taskmanager.navigation.Navigation
import com.angelasaez.taskmanager.ui.theme.TaskManagerTheme
import com.angelasaez.taskmanager.tasks.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                val taskViewModel by viewModels<TaskViewModel>()
                Navigation(taskViewModel)
            }
        }
    }
}

