package com.angelasaez.taskmanager.ui.screens.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.UserViewModel

// Componente propio para tener un Scaffold unificado en toda la aplicación

@Composable
fun AppScaffold(
    showBackArrow: Boolean = false,
    onBlackArrowClick: () -> Unit = {},
    content: @Composable () -> Unit,
    navController: NavController
) {
    //obtener datos usuario para mostrarlos abajo y hacer gestiones como la de cerrar sesion (vaciar)
    val userViewModel: UserViewModel = viewModel()
    val username by userViewModel.username.observeAsState("")

    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = showBackArrow,
                onClickBlackArrow = onBlackArrowClick,
                navController = navController
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxWidth()
            ) {
                content()
            }
            HorizontalDivider(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimary)
                    .height(2.dp)
            )
            AuthorInfo(modifier = Modifier
                .padding(vertical = 4.dp)
                .weight(1f), username)
        }
    }
}

