package com.angelasaez.taskmanager.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Splash

    @Serializable
    object OnBoarding

    @Serializable
    object MainTask

    @Serializable
    object InfoTask
}



