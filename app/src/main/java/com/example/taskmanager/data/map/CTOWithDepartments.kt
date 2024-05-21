package com.example.taskmanager.data.map

import androidx.room.Embedded
import androidx.room.Relation

data class CTOWithDepartments(
    @Embedded val cto: CTO,
    @Relation(
        parentColumn = "ctoId",
        entityColumn = "departmentId"
    )
    val departments: List<Department>
)

