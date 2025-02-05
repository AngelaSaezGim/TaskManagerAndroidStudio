package com.angelasaez.taskmanager.features.tasks.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var isDone: Boolean = false,
    var description: String = ""
)

