
package com.example.taskmanager.data.map

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val deadline: String,
    val departmentId: Int  // assuming tasks are related to departments
)
