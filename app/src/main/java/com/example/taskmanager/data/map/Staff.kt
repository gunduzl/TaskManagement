package com.example.taskmanager.data.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Staff(
    @PrimaryKey(autoGenerate = true)
    val staffId: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val departmentId: Int,
    val departmentManagerId: Int
)

