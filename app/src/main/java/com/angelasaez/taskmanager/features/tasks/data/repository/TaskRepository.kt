package com.angelasaez.taskmanager.features.tasks.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.angelasaez.taskmanager.features.tasks.data.local.dao.TaskDAO
import com.angelasaez.taskmanager.features.tasks.data.local.entities.TaskEntity
import com.angelasaez.taskmanager.features.tasks.domain.model.Task

class TaskRepository(private val taskDAO: TaskDAO) {
    val tasks: LiveData<MutableList<Task>> = taskDAO.getAllTasks().map { items ->
        items.map { taskEntity->
            Task(
                id = taskEntity.id,
                name = taskEntity.name,
                isDone = taskEntity.isDone
            )
        }.toMutableList()
    }

    suspend fun taskExists(name: String): Boolean = taskDAO.taskExists(name)

    suspend fun addTask(task: Task) {
        taskDAO.addTask(TaskEntity(name = task.name))
    }

    suspend fun deleteTask(task: Task) = taskDAO.deleteTask(TaskEntity(id = task.id, name = task.name))

    suspend fun updateTask(task: Task) = taskDAO.updateTask(TaskEntity(id = task.id, name = task.name, isDone = task.isDone, description = task.description))

    suspend fun getTaskById(id: Long): Task? {
        return taskDAO.getTaskById(id)?.let { taskEntity ->
            Task(
                id = taskEntity.id,
                name = taskEntity.name,
                isDone = taskEntity.isDone
            )
        }
    }

    //ej opcional
    suspend fun deleteAllTasks() {
        taskDAO.deleteAllTasks()
    }
}

