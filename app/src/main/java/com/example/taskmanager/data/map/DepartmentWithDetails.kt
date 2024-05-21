package com.example.taskmanager.data.map

import androidx.room.Embedded
import androidx.room.Relation


data class DepartmentWithDetails(
    @Embedded val department: Department,
    @Relation(
        parentColumn = "departmentId",
        entityColumn = "departmentId",
        entity = DepartmentManager::class
    )
    val manager: DepartmentManager,
    @Relation(
        parentColumn = "departmentId",
        entityColumn = "departmentId",
        entity = Staff::class
    )
    val staff: List<Staff>,
    @Relation(
        parentColumn = "departmentId",
        entityColumn = "departmentId",
        entity = Task::class
    )
    val tasks: List<Task>
)
