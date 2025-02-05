package com.angelasaez.taskmanager.features.tasks.ui.maintasksscreen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.angelasaez.taskmanager.features.tasks.data.local.dao.TaskDAO
import com.angelasaez.taskmanager.features.tasks.data.local.database.TasksDatabase
import com.angelasaez.taskmanager.features.tasks.domain.model.Task
import com.angelasaez.taskmanager.features.tasks.data.repository.TaskRepository
import com.angelasaez.taskmanager.features.tasks.domain.usecase.TaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    // ESTO DENTRO DE UN METODO EN EL QUE ANTES SAQUEMOS EL USERNAME (cada uusario tiene sus tareas)
    //getInstance(context,"NombreUsuario") - CARGO INSTANCIA DE LA BASE DE DATOS
    private val taskDAO: TaskDAO = TasksDatabase.getInstance(application).taskDAO()
    private val repository = TaskRepository(taskDAO)
    private val taskUseCase = TaskUseCase(repository)

    // Se crea un LiveData para la lista de tareas
    var taskList: LiveData<MutableList<Task>> = MutableLiveData()

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

    // Función que actualiza una tarea con SU DESCRIPCION
    fun updateTask(task: Task, isDone: Boolean, description: String){
        viewModelScope.launch(Dispatchers.IO) {
            taskUseCase.updateTask(task.copy(isDone = isDone, description = description))
        }
    }
}


