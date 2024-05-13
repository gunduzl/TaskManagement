package com.example.taskmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val deadline: String,
    val departmentId: Int  // assuming tasks are related to departments
)

