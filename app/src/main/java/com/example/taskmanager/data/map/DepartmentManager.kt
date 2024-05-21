
package com.example.taskmanager.data.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DepartmentManager(
    @PrimaryKey(autoGenerate = true)
    val departmentManagerId: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val managerPoint: Int,
    val departmentId: Int,  // Ensure this connects to a specific department
    val ctoId: Int          // Connect to CTO if needed
)
