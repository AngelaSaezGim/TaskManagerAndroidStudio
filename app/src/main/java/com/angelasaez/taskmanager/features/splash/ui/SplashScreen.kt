package com.angelasaez.taskmanager.features.splash.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.angelasaez.taskmanager.R
import com.angelasaez.taskmanager.common.ui.utils.CustomSpacer
import com.angelasaez.taskmanager.features.splash.ui.viewmodel.SplashScreenViewModel
import com.angelasaez.taskmanager.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val splashScreenViewModel: SplashScreenViewModel = viewModel()

    val username by splashScreenViewModel.username.observeAsState(initial = "")

    LaunchedEffect(key1 = true) {
        delay(5000)

        // Si nombre de usuario existe, navega directamente a la pantalla principal
        if (username.isNotEmpty()) {
            navController.popBackStack()
            navController.navigate(Routes.MainTask)
        }
        //si no, onbaording
        else {
            navController.popBackStack()
            navController.navigate(Routes.OnBoarding)
        }
    }
    Splash()
}

@Composable
fun Splash() {
    var animateAlpha by rememberSaveable { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if(animateAlpha) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        ),
        label = "alpha animation"
    )
    var greetingVisible by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        animateAlpha = true
        delay(2000)
        greetingVisible = true
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Gestor de tareas",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        CustomSpacer(height=10)
        Image(
            painter = painterResource(id= R.drawable.foto_perfil),
            contentDescription = "foto",
            modifier = Modifier
                .size(100.dp, 100.dp)
                .alpha(alpha)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        CustomSpacer(height=10)
        AnimatedVisibility(visible = greetingVisible) {
            Text(
                text ="Ángela",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}




