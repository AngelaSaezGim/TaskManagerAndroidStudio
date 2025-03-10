package com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.angelasaez.taskmanager.common.ui.utils.CustomSpacer
import com.angelasaez.taskmanager.features.tasks.domain.model.Task
import com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen.viewmodel.MainScreenViewModel
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.TaskViewModel
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.UserViewModel
import com.angelasaez.taskmanager.ui.screens.layout.AppScaffold


@Composable
fun MainTasksScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    userViewModel: UserViewModel
) {
    AppScaffold(navController = navController,  // SINO NO PODIA PASARLE EL CONTROLLER AL SCAFFOLD
                content = {
                    //Cargar base de datos del nombre de usuario
                    val taskList by taskViewModel.taskList.observeAsState(emptyList())
                    val mainScreenViewModel = remember { MainScreenViewModel() }
                    val inputTaskName by mainScreenViewModel.taskName.observeAsState(initial = "")
                    val username by userViewModel.username.observeAsState("")

                    val showDeleteIcon = remember {
                        derivedStateOf {
                            inputTaskName.isNotEmpty()
                        }
                    }
                    //Intente ponerlo en el view model pero habia problemas con su manejo
                    var showInsertTaskError by rememberSaveable { mutableStateOf(false) }

                    LaunchedEffect(username) {
                        if (username.isNotBlank()) {
                            taskViewModel.initDatabase(username)
                            taskViewModel.getAllTasks()
                        }

                        navController.currentBackStackEntryFlow.collect() { backStackEntry ->
                            taskViewModel.getAllTasks()
                        }
                    }


                    Box {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                CustomSpacer(height = 50)
                                TextField(value = inputTaskName,
                                          onValueChange = { mainScreenViewModel.onTaskNameChange(it) },
                                          label = { Text(text = "Tarea a añadir") },
                                          trailingIcon = {
                                              if (showDeleteIcon.value) {
                                                  Icon(imageVector = Icons.Default.Delete,
                                                       contentDescription = "Eliminar tarea",
                                                       modifier = Modifier.clickable { mainScreenViewModel.onTaskNameDelete() })
                                              }
                                          },

                                )

                                Button(
                                    onClick = {
                                        taskViewModel.addTask(
                                            task = inputTaskName,
                                            onResult = { taskAdded ->
                                                showInsertTaskError = !taskAdded
                                                if (taskAdded) mainScreenViewModel.onTaskNameDelete()
                                            })
                                    },
                                    enabled = showDeleteIcon.value,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor= MaterialTheme.colorScheme.tertiary
                                    )

                                ) {
                                    Text(text = "Añadir")
                                }
                            }

                            LazyColumn(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp)
                            ) {
                                items(items = taskList, key = {
                                    it.id
                                }) { task ->
                                    TaskItem(task = task, onUpdate = { isEnded ->
                                        taskViewModel.updateTask(task, isEnded)
                                    }, onDelete = {
                                        taskViewModel.deleteTask(task)
                                    }, navController = navController
                                    )
                                }
                            }
                        }

                        if (showInsertTaskError) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(enabled = false, onClick = {}),
                                contentAlignment = Alignment.Center
                            ) {

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xccFFFFFF))
                                )
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(MaterialTheme.colorScheme.error)
                                        .padding(20.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = """La tarea "$inputTaskName" ya existe.""",
                                        color = MaterialTheme.colorScheme.onSecondary
                                    )
                                    Button(onClick = { showInsertTaskError = false }) {
                                        Text(text = "Aceptar")
                                    }
                                }
                            }
                        }
                    }
                })
}

@Composable
fun TaskItem(
    task: Task, onUpdate: (Boolean) -> Unit, onDelete: () -> Unit, navController: NavHostController
) {
    var showDeleteIcon by rememberSaveable { mutableStateOf(false) }
    ListItem(headlineContent = {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = task.name, color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            // MOSTRAR DESCRIPCION TAREA
                Text(
                    text = "ID : ${task.id} - Descripcion ; ${task.description}",
                )
        }
    }, modifier = Modifier
        .padding(4.dp)
        .clip(RoundedCornerShape(4.dp))
        .pointerInput(Unit) {
            detectTapGestures(onLongPress = {
                showDeleteIcon = !showDeleteIcon
            }, onPress = {
                showDeleteIcon = false
            }, onTap = {
                navController.navigate("taskDetailScreen/${task.id}")
            })
        }, colors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.2f),
    ), leadingContent = {
        Checkbox(
            checked = task.isDone, onCheckedChange = { isEnded ->
                onUpdate(isEnded)
                showDeleteIcon = false
            }, colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onTertiaryContainer ,
                uncheckedColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        )
    }, trailingContent = {
        if (showDeleteIcon) {
            IconButton(
                onClick = onDelete,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    })
}