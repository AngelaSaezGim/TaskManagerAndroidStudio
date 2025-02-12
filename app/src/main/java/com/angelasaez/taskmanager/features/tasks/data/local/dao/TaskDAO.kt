package com.angelasaez.taskmanager.features.tasks.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.angelasaez.taskmanager.features.tasks.data.local.entities.TaskEntity

@Dao
interface TaskDAO {
    @Query("SELECT * FROM tasks ORDER BY name")
    fun getAllTasks(): LiveData<MutableList<TaskEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM tasks WHERE name = :name)")
    suspend fun taskExists(name: String): Boolean

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(item: TaskEntity): Long

    @Update
    suspend fun updateTask(item: TaskEntity): Int

    @Delete
    suspend fun deleteTask(item: TaskEntity): Int

    //BORRAR TODAS LAS TAREAS SIN PARAMETRO (ej opcional)
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks(): Int
}


