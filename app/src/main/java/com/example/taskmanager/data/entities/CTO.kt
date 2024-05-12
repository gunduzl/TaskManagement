package com.example.taskmanager.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CTO(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: String
)

