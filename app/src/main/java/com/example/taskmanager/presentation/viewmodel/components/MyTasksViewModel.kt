package com.example.taskmanager.presentation.viewmodel

import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyTasksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadTasksForUser(userId: Int, userRole: String) {
        viewModelScope.launch {
            val tasks = when (userRole.lowercase()) {
                "staff" -> repository.getTaskFromStaff(userId)
            else -> emptyList()
            }
            _tasks.value = tasks.map { taskWithStaff -> taskWithStaff.task }
        }
    }
}
