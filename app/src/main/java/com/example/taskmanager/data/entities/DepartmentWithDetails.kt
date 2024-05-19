package com.example.taskmanager.data.entities

import androidx.room.Embedded
import androidx.room.Relation


data class DepartmentWithDetails(
    @Embedded val department: Department,
    @Relation(
        parentColumn = "id",
        entityColumn = "departmentId",
        entity = DepartmentManager::class
    )
    val manager: DepartmentManager,
    @Relation(
        parentColumn = "id",
        entityColumn = "departmentId",
        entity = Staff::class
    )
    val staff: List<Staff>,
    @Relation(
        parentColumn = "id",
        entityColumn = "departmentId",
        entity = Task::class
    )
    val tasks: List<Task>
)
