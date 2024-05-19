package com.example.taskmanager.presentation.viewmodel.pages

import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.launch

class CTOHomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val _openTasks = MutableLiveData<List<Task>>()
    private val _activeTasks = MutableLiveData<List<Task>>()

    val openTasks: LiveData<List<Task>> get() = _openTasks
    val activeTasks: LiveData<List<Task>> get() = _activeTasks

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadTasksForDepartment(departmentId: Int) {
        viewModelScope.launch {
            _openTasks.value = repository.getTasksByStatusAndDepartment("Open", departmentId)
            _activeTasks.value = repository.getTasksByStatusAndDepartment("Active", departmentId)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
            loadTasksForDepartment(task.departmentId)
        }
    }
}
