package com.angelasaez.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.angelasaez.taskmanager.navigation.Navigation
import com.angelasaez.taskmanager.common.ui.theme.TaskManagerTheme
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.TaskViewModel
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                val taskViewModel by viewModels<TaskViewModel>()
                val userViewModel by viewModels<UserViewModel>()
                Navigation(taskViewModel, userViewModel)
            }
        }
    }
}

