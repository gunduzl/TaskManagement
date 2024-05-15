package com.example.taskmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val departmentManagerId: Int,  // Foreign key linking to DepartmentManager
    val CTOId: Int                // Foreign key linking to CTO
)