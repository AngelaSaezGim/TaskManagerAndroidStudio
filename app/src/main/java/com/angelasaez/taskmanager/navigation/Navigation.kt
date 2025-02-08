package com.angelasaez.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angelasaez.taskmanager.features.onboarding.ui.OnBoardingScreen
import com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen.MainTasksScreen
import com.angelasaez.taskmanager.features.splash.ui.SplashScreen
import com.angelasaez.taskmanager.features.tasks.ui.viewModel.TaskViewModel
import com.angelasaez.taskmanager.features.tasks.ui.taskinfoscreen.InfoTasksScreen


@Composable
fun Navigation(taskViewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable<Routes.Splash> {
            SplashScreen(navController)
        }

        composable<Routes.OnBoarding> {
            OnBoardingScreen(navController)
        }

        composable<Routes.MainTask> {
            MainTasksScreen(navController, taskViewModel)
        }

        composable("taskDetailScreen/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            taskId?.let {
                InfoTasksScreen(navController, taskViewModel, it)
            }
        }
    }
}

