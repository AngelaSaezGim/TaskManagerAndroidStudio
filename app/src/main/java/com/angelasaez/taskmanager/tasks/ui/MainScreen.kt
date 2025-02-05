package com.angelasaez.taskmanager.tasks.ui

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
import com.angelasaez.taskmanager.tasks.domain.model.Task
import com.angelasaez.taskmanager.ui.screens.layout.AppScaffold
import com.angelasaez.taskmanager.tasks.ui.viewmodel.MainScreenViewModel
import com.angelasaez.taskmanager.tasks.ui.viewmodel.TaskViewModel

@Composable
fun MainScreen(taskViewModel: TaskViewModel) {
    AppScaffold { // Uso del Scaffold personalizado
        val taskList by taskViewModel.taskList.observeAsState(emptyList())
        val mainScreenViewModel = remember { MainScreenViewModel() }
        val inputTaskName by mainScreenViewModel.taskName.observeAsState(initial = "")
        val showDeleteIcon = remember {
            derivedStateOf {
                inputTaskName.isNotEmpty()
            }
        }

        var showInsertTaskError by rememberSaveable { mutableStateOf(false) }
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
                    TextField(value = inputTaskName,
                        onValueChange = { mainScreenViewModel.onTaskNameChange(it) },
                        label = { Text(text = "Tarea a añadir") },
                        trailingIcon = {
                            if (showDeleteIcon.value) {
                                Icon(imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar tarea",
                                    modifier = Modifier.clickable { mainScreenViewModel.onTaskNameDelete() })
                            }
                        })

                    Button(
                        onClick = {
                            taskViewModel.addTask(task = inputTaskName,
                                onResult = { taskAdded ->
                                    showInsertTaskError = !taskAdded
                                    if (taskAdded) mainScreenViewModel.onTaskNameDelete()
                                }
                            )
                        },
                        enabled = showDeleteIcon.value
                    ) {
                        Text(text = "Añadir")
                    }
                }

                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    items(
                        items = taskList,
                        key = {
                            it.id
                        }
                    ) { task ->
                        TaskItem(task = task,
                            onUpdate = { isEnded ->
                                taskViewModel.updateTask(task, isEnded)
                            },
                            onDelete = {
                                taskViewModel.deleteTask(task)
                            }
                        )
                    }
                }
            }

            if (showInsertTaskError) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(enabled = false,
                            onClick = {}),
                    contentAlignment = Alignment.Center
                ) {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xccFFFFFF)))
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
    }
}

@Composable
fun TaskItem(
    task: Task,
    onUpdate: (Boolean) -> Unit,
    onDelete: () -> Unit,
) {
    var showDeleteIcon by rememberSaveable { mutableStateOf(false) }
    ListItem(
        headlineContent = {
            Text(
                text = task.name,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDeleteIcon = !showDeleteIcon
                    },
                    onPress = {
                        showDeleteIcon = false
                    }
                )
            }
        ,
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        leadingContent = {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { isEnded ->
                    onUpdate(isEnded)
                    showDeleteIcon = false
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        trailingContent = {
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
        }
    )
}
