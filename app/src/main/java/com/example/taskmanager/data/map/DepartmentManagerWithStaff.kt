package com.example.taskmanager.data.map

import androidx.room.Embedded
import androidx.room.Relation

data class DepartmentManagerWithStaff(
    @Embedded val departmentManager: DepartmentManager,
    @Relation(
        parentColumn = "departmentManagerId",
        entityColumn = "staffId"
    )
    val staff: List<Staff>
)

