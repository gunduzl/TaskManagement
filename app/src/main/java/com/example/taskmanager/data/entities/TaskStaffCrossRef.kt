package com.example.taskmanager.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["taskId", "staffId"])
data class TaskStaffCrossRef(
    val taskId: Int,
    val staffId: Int
)

