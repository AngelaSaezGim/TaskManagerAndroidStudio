package com.angelasaez.taskmanager.features.tasks.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.angelasaez.taskmanager.features.tasks.data.local.database.TasksDatabase
import com.angelasaez.taskmanager.features.tasks.domain.model.Task
import com.angelasaez.taskmanager.features.tasks.data.repository.TaskRepository
import com.angelasaez.taskmanager.features.tasks.domain.usecase.TaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    val context = application
    lateinit private var taskUseCase : TaskUseCase

    // Se crea un LiveData para la lista de tareas
    var taskList: LiveData<MutableList<Task>> = MutableLiveData()

    //LiveData para tarea especifica (getById para info)
    private val _task = MutableLiveData<Task?>()
    val task: LiveData<Task?> get() = _task

    private val _username = MutableLiveData("")
    val username: LiveData<String> get() = _username

    //Este para el main
    fun initDatabase(username:String){
        val taskDAO = TasksDatabase.getInstance(context, username).taskDAO()
        val repository = TaskRepository(taskDAO)
        taskUseCase = TaskUseCase(repository)
    }

    //Este para taskinfo (aunque podria usar el otro no quiero cargar TODAS las tareas)
    fun getTask(username:String, taskId: String? = null){
        val taskDAO = TasksDatabase.getInstance(context, username).taskDAO()
        val repository = TaskRepository(taskDAO)
        taskUseCase = TaskUseCase(repository)

        taskId?.let { getTaskById(it) }
    }

    // Función que inicializa la lista de tareas desde la BBDD
    fun getAllTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            taskList = taskUseCase.getAllTasks()
        }
    }

    // Función que añade una tarea a la base de datos
    fun addTask(task: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!taskUseCase.taskExists(task)) {
                taskUseCase.addTask(task)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    // Función que elimina una tarea de la base de datos
    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskUseCase.deleteTask(task)
        }
    }

    // Función que actualiza una tarea de la base de datos.
    fun updateTask(task: Task, isDone: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            taskUseCase.updateTask(task.copy(isDone = isDone))
        }
    }

    // Función que uso para ACTUALIZAR LA DESCRIPCION
    fun updateTask(task: Task, description: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var updatedTask = task.copy(description = description)
            taskUseCase.updateTask(updatedTask)
            _task.postValue(updatedTask)
            onResult(true)
        }
    }

    fun getTaskById(taskId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val foundTask = taskUseCase.getTaskById(taskId.toLong())  // Convertir String a Long
            _task.postValue(foundTask)
        }
    }
}


