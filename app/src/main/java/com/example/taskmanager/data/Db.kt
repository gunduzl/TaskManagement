package com.example.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.data.dao.DbDao

import com.example.taskmanager.data.map.*
//import com.example.taskmanager.data.map.SarInfo
//import com.example.taskmanager.data.users.SupplyRequestsTable
//import com.example.taskmanager.data.users.UserTable

@Database(
    entities = [
        CTO::class,
        Department::class,
        DepartmentManager::class,
        Staff::class,
        Task::class,
        TaskStaffCrossRef::class,
        Notification::class
    ],
    version = 1
)

abstract class Db: RoomDatabase() {
    abstract fun mapDao():DbDao

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        fun getDatabase(context: Context): Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Db::class.java,
                    "db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}