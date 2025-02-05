package com.angelasaez.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelasaez.taskmanager.features.onboarding.ui.OnBoardingScreen
import com.angelasaez.taskmanager.features.onboarding.ui.viewmodel.OnBoardingScreenViewModel
import com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen.MainTasksScreen
import com.angelasaez.taskmanager.features.splash.ui.SplashScreen
import com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen.viewmodel.TaskViewModel


@Composable
fun Navigation(taskViewModel: TaskViewModel, onBoardingScreenViewModel: OnBoardingScreenViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable<Routes.Splash> {
            SplashScreen(navController, taskViewModel)
        }

        composable<Routes.OnBoarding> {
            OnBoardingScreen(onBoardingScreenViewModel)
        }

        composable<Routes.MainTask> {
            MainTasksScreen(taskViewModel)
        }

        //Falta InfoTasksScreen
    }
}

