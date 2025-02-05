package com.angelasaez.taskmanager.tasks.domain.model

data class Task(
    val id: Int = 0,
    val name: String,
    val isDone: Boolean = false,
)

