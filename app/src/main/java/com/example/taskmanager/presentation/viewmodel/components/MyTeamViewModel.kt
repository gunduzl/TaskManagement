package com.example.taskmanager.presentation.viewmodel

import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import com.example.taskmanager.data.entities.Staff
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyTeamViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    private val _staffList = MutableStateFlow<List<Staff>>(emptyList())
    val staffList: StateFlow<List<Staff>> = _staffList

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadStaffForManager(managerId: Int) {
        viewModelScope.launch {
            val managerWithStaff = repository.getDepartmentManagerWithStaff(managerId).firstOrNull()
            _staffList.value = managerWithStaff?.staff ?: emptyList()
        }
    }
}
