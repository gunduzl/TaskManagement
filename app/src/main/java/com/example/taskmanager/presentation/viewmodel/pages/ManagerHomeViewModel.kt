package com.example.taskmanager.presentation.viewmodel.pages


import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManagerHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    private val _openTasks = MutableStateFlow<List<Task>>(emptyList())
    val openTasks: StateFlow<List<Task>> = _openTasks

    private val _activeTasks = MutableStateFlow<List<Task>>(emptyList())
    val activeTasks: StateFlow<List<Task>> = _activeTasks

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadTasksForDepartment(departmentId: Int) {
        viewModelScope.launch {
            val departmentDetails = repository.getDepartmentWithDetails(departmentId).firstOrNull()
            _openTasks.value = departmentDetails?.tasks?.filter { it.status == "Open" } ?: emptyList()
            _activeTasks.value = departmentDetails?.tasks?.filter { it.status == "Active" } ?: emptyList()
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
            loadTasksForDepartment(task.departmentId)  // Refresh the tasks
        }
    }
}
