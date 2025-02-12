package com.angelasaez.taskmanager.features.tasks.ui.taskinfoscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.angelasaez.taskmanager.common.ui.utils.CustomSpacer
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.TaskViewModel
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.UserViewModel
import com.angelasaez.taskmanager.ui.screens.layout.AppScaffold

@Composable
fun InfoTasksScreen(navController: NavHostController, taskId: String, taskViewModel: TaskViewModel, userViewModel: UserViewModel) {

    val username by userViewModel.username.observeAsState("")

    LaunchedEffect(username) {
        if (username.isNotBlank()) {
            taskViewModel.getTask(username, taskId)
        }
    }

    // Variable task de ViewModel
    val task by taskViewModel.task.observeAsState(null)
    val editedDescription = remember { mutableStateOf("") }
    val saveConfirmation = remember { mutableStateOf(false) }


    AppScaffold(
        showBackArrow = true,
        onBlackArrowClick = { navController.navigateUp()},
                content = {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            task?.let {
                                Text(text = "Nombre: ${it.name}")

                                CustomSpacer(height = 10)

                                // Campo de texto para la descripción de la tarea
                                TextField(
                                    value = editedDescription.value,
                                    onValueChange = { newDesc ->
                                        editedDescription.value = newDesc  // Actualizamos el estado local
                                    },
                                    label = { Text("Descripción") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } ?: Text(text = "Cargando...")

                            CustomSpacer(height = 10)

                            // Botón para guardar los cambios
                            Button(
                                onClick = {
                                    taskViewModel.updateTask(task!!, editedDescription.value) { success ->
                                        if(success){
                                            saveConfirmation.value =true
                                            println(task)
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,  // Color de fondo del botón
                                    contentColor = MaterialTheme.colorScheme.onTertiary // Color del texto dentro del botón
                                )

                            ) {
                                Text("Guardar")
                            }

                            // Mostrar el mensaje de confirmación si se guardó
                            if (saveConfirmation.value) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Tarea guardada con éxito.",
                                        color = Color.Green
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text("ID : ${task?.id} - Descripcion -> ${task?.description}")
                            }
                        }
                    }
                },
        navController = navController,
    )

}