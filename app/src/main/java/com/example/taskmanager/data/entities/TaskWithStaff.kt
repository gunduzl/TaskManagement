package com.example.taskmanager.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TaskWithStaff(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "staffId",
        associateBy = Junction(TaskStaffCrossRef::class)
    )
    val staff: List<Staff>
)