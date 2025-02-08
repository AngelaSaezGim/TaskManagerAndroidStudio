package com.angelasaez.taskmanager.features.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.angelasaez.taskmanager.common.ui.utils.CustomSpacer
import com.angelasaez.taskmanager.features.onboarding.ui.viewmodel.OnBoardingScreenViewModel
import com.angelasaez.taskmanager.navigation.Routes

@Composable
fun OnBoardingScreen(navController: NavHostController) {

    val onBoardingViewModel: OnBoardingScreenViewModel = viewModel()

    var username by remember { mutableStateOf(TextFieldValue("")) }
    val isButtonEnabled = username.text.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido a Task Manager", style = MaterialTheme.typography.headlineLarge)

        CustomSpacer(height = 16)

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Ingrese su nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        CustomSpacer(height = 16)

        // Botón de continuar, se habilita cuando rellenamos nombre
        Button(
            onClick = {
                // GUARDAR NOMBRE USUARIO - DESDE EL VIEW MODEL
                onBoardingViewModel.saveUsername(username.text)
                // Navegar MAIN
                navController.navigate(Routes.MainTask) {
                    popUpTo(Routes.OnBoarding) { inclusive = true } // Evita que se vuelva
                }
            },
            enabled = isButtonEnabled
        ) {
            Text("Continuar")
        }
    }
}