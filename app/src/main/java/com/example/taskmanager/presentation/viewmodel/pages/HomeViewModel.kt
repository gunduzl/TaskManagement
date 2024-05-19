package com.example.taskmanager.presentation.viewmodel.pages


import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import com.example.taskmanager.data.entities.TaskWithStaff
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    private val _openTasks = MutableStateFlow<List<TaskWithStaff>>(emptyList())
    val openTasks: StateFlow<List<TaskWithStaff>> = _openTasks

    private val _activeTasks = MutableStateFlow<List<TaskWithStaff>>(emptyList())
    val activeTasks: StateFlow<List<TaskWithStaff>> = _activeTasks

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadTasksForStaff(staffId: Int) {
        viewModelScope.launch {
            _openTasks.value = repository.getOpenTasksFromStaff(staffId)
            _activeTasks.value = repository.getActiveTasksFromStaff(staffId)
        }
    }
}
