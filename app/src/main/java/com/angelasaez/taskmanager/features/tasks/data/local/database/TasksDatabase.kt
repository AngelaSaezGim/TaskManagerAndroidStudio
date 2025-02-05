package com.angelasaez.taskmanager.features.tasks.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.angelasaez.taskmanager.features.tasks.data.local.dao.TaskDAO
import com.angelasaez.taskmanager.features.tasks.data.local.entities.TaskEntity

//@Database(entities = arrayOf(TaskEntity::class), version = 1)
@Database(entities = [TaskEntity::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDAO(): TaskDAO

    companion object {  // Patrón Singleton
        private var instance: TasksDatabase? = null

        fun getInstance(context: Context): TasksDatabase {
            // el método databaseBuilder devuelve una referencia a la base de datos
//            return instance ?: Room.databaseBuilder(context, TasksDatabase::class.java, "tasks-db")
//                .build()
//                .also { instance = it}

            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "task_database"
                ).build()
                instance
            }

        }
    }
}


//                .fallbackToDestructiveMigration()

//        private var instance: TaskDAO? = null
//
//        fun getInstance(context: Context): TaskDAO {
//            // el método databaseBuilder devuelve una referencia a la base de datos
//            return instance ?: Room.databaseBuilder(context, TasksDatabase::class.java, "tasks-db")
//                .build().taskDAO().also { instance = it }
//        }
