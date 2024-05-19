package com.example.taskmanager.presentation.viewmodel.components


import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import com.example.taskmanager.data.entities.Task
import kotlinx.coroutines.launch

class PoolViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadTasksByStatus(status: String) {
        viewModelScope.launch {
            _tasks.value = repository.getTasksByStatus(status)
        }
    }
}
