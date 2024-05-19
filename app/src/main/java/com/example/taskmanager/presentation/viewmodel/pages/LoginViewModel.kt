package com.example.taskmanager.presentation.viewmodel.pages

import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import kotlinx.coroutines.launch



class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val _userRoleWithIdAndDept = MutableLiveData<Triple<String, Int, Int?>>()
    val userRoleWithIdAndDept: LiveData<Triple<String, Int, Int?>> get() = _userRoleWithIdAndDept

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch {
            val staff = repository.authenticateStaff(email, password)
            if (staff != null) {
                _userRoleWithIdAndDept.postValue(Triple("Staff", staff.id, null))
                return@launch
            }

            val manager = repository.authenticateManager(email, password)
            if (manager != null) {
                _userRoleWithIdAndDept.postValue(Triple("Manager", manager.id, manager.departmentId))
                return@launch
            }

            val cto = repository.authenticateCTO(email, password)
            if (cto != null) {
                _userRoleWithIdAndDept.postValue(Triple("CTO", cto.id, null))
                return@launch
            }

            _userRoleWithIdAndDept.postValue(Triple("", 0, null)) // no user found
        }
    }
}
