package com.example.taskmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DepartmentManager(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val departmentId: Int,  // Ensure this connects to a specific department
    val CTOId: Int          // Connect to CTO if needed
)


