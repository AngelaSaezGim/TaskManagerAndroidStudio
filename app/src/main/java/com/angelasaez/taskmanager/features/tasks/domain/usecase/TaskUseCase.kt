package com.angelasaez.taskmanager.features.tasks.domain.usecase

import androidx.lifecycle.LiveData
import com.angelasaez.taskmanager.features.tasks.data.repository.TaskRepository
import com.angelasaez.taskmanager.features.tasks.domain.model.Task

class TaskUseCase(private val repository: TaskRepository) {
    fun getAllTasks(): LiveData<MutableList<Task>> {
        return repository.tasks
    }

    suspend fun addTask(task: String) {
        repository.addTask(Task(name= task))
    }

    suspend fun taskExists(name: String): Boolean {
        return repository.taskExists(name)
    }

    suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        repository.updateTask(task)
    }
}

