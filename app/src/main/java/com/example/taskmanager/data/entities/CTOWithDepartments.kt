package com.example.taskmanager.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CTOWithDepartments(
    @Embedded val cto: CTO,
    @Relation(
        parentColumn = "id",
        entityColumn = "CTOId"
    )
    val departments: List<Department>
)
