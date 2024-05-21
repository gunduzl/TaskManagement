package com.example.taskmanager.data.map


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val notificationId: Int? = null,
    val title: String,
    val description: String,
    val date: String,
    val employeeType: String,
    val employeeId: Int
)
