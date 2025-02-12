package com.angelasaez.taskmanager.ui.screens.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angelasaez.taskmanager.common.ui.utils.CustomSpacer
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.TaskViewModel
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.UserViewModel
import com.angelasaez.taskmanager.navigation.Routes

// Componente propio para la TopAppBar del Scaffold usado en la APP
// ES DE MAIN Y TASKINFO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController,
    showBackArrow: Boolean = false,
    onClickBlackArrow: () -> Unit,
) {
    val taskViewModel: TaskViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val username by userViewModel.username.observeAsState("")
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomSpacer(width = 16)
                Text(
                    text = "Tareas de $username",
                    fontSize = 25.sp
                )
                CustomSpacer(width = 16)
                Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) { //Expandir menu
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cerrar sesión") },
                    onClick = {
                        expanded = false
                        userViewModel.deleteUser()
                        navController.navigate(Routes.OnBoarding)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Eliminar cuenta (tareas)") },
                    onClick = {
                        expanded = false
                        userViewModel.deleteAllUserTasks(username) // Elimina todas las tareas asociadas
                        userViewModel.deleteUser() //Cierra sesión
                        navController.navigate(Routes.OnBoarding)
                    }
                )
            }
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { onClickBlackArrow() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}