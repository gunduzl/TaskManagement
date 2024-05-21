package com.example.taskmanager.data.map

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["taskId", "staffId"],
    indices = [Index(value = ["taskId"]), Index(value = ["staffId"])]
)
data class TaskStaffCrossRef(
    val taskId: Int,
    val staffId: Int
)
