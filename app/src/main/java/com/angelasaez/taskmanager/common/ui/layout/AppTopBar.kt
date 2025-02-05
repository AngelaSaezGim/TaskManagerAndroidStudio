package com.angelasaez.taskmanager.ui.screens.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Componente propio para la TopAppBar del Scaffold usado en la APP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    showBackArrow: Boolean = false,
    onClickBlackArrow: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Gestor de tareas",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(imageVector = Icons.Default.TaskAlt, contentDescription = null)

            }
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(
//                    onClick = { onClickBlackArrow() }
                    onClick = onClickBlackArrow
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


