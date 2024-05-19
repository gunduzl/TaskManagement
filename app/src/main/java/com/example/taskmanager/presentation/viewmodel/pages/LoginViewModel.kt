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
    private val _userRoleWithId = MutableLiveData<Pair<String, Int>>()
    val userRoleWithId: LiveData<Pair<String, Int>> get() = _userRoleWithId

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch {
            val staff = repository.authenticateStaff(email, password)
            if (staff != null) {
                _userRoleWithId.postValue(Pair("Staff", staff.id))
                return@launch
            }

            val manager = repository.authenticateManager(email, password)
            if (manager != null) {
                _userRoleWithId.postValue(Pair("Manager", manager.id))
                return@launch
            }

            val cto = repository.authenticateCTO(email, password)
            if (cto != null) {
                _userRoleWithId.postValue(Pair("CTO", cto.id)) // return CTO role and id
                return@launch
            }

            _userRoleWithId.postValue(Pair("", 0)) // no user found
        }
    }
}
