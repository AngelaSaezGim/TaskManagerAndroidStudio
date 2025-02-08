package com.angelasaez.taskmanager.features.tasks.ui.taskinfoscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.TaskViewModel
import com.angelasaez.taskmanager.features.tasks.ui.taskinfoscreen.viewmodel.InfoTaskScreenViewModel
import com.angelasaez.taskmanager.ui.screens.layout.AppScaffold

@Composable
fun InfoTasksScreen(navController: NavHostController, taskViewModel: TaskViewModel, taskId: String) {
        AppScaffold(navController = navController, // Pasa el navController primero
                    content = {
                        val infoTaskScreenViewModel = remember { InfoTaskScreenViewModel() }

                        //val task = taskViewModel.getTaskById(taskId)

                        Box {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Aquí puedes colocar tu contenido
                                Text("En proceso")
                            }
                        }
         })
    }