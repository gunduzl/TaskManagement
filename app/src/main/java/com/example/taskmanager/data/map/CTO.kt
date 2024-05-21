package com.example.taskmanager.data.map


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CTO(
    @PrimaryKey(autoGenerate = true)
    val ctoId: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: String
)