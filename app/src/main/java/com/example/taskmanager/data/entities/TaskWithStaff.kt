package com.example.taskmanager.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TaskWithStaff(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "staffId",
        associateBy = Junction(TaskStaffCrossRef::class, parentColumn = "taskId", entityColumn = "staffId")
    )
    val staff: List<Staff>
)
