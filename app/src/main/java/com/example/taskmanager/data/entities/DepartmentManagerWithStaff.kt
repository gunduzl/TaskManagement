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

// explain the code
// when we want to get a department manager with all staff members, we can use this class
// this returns a department manager and a list of staff members that are managed by the department manager


