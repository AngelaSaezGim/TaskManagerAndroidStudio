package com.angelasaez.taskmanager.features.tasks.domain.model

import androidx.collection.IntList

data class Task(
    val id: Int = 0,
    val name: String,
    val isDone: Boolean = false,
    val description : String = "",
)

