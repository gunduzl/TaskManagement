package com.example.taskmanager.data.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey(autoGenerate = true)
    val departmentId: Int? = null,
    val name: String,
    val description: String,
    val departmentManagerId: Int,  // Foreign key linking to DepartmentManager
    val ctoId: Int                // Foreign key linking to CTO
)

