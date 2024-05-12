package com.example.taskmanager.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DepartmentManagerWithStaff(
    @Embedded val departmentManager: DepartmentManager,
    @Relation(
        parentColumn = "id",
        entityColumn = "departmentManagerId"
    )
    val staff: List<Staff>
)
