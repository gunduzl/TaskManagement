package com.example.taskmanager.presentation.viewmodel.staff

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StaffViewModel(application: Application) : AndroidViewModel(application) {
    private val staffDao = DatabaseBuilder.getDatabase(application).staffDao()

    private val _allStaff = MutableLiveData<List<Employee>>()
    val allStaff: LiveData<List<Employee>> get() = _allStaff

    init {
        getAllStaff()
    }

    private fun getAllStaff() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _allStaff.postValue(staffDao.getAll())
        }
    }

    fun insertStaff(staff: Employee) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            staffDao.insertAll(staff)
        }
    }
}