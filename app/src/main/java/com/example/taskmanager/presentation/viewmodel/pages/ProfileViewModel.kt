package com.example.taskmanager.presentation.viewmodel.pages

import AppDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.dbRepo.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class UserProfile(
    val name: String,
    val departmentName: String,
    val points: Int
)
class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository

    private val _userProfile = MutableStateFlow(UserProfile("", "", 0))
    val userProfile: StateFlow<UserProfile> = _userProfile

    init {
        val dbDAO = AppDatabase.getDatabase(application).dbDAO()
        repository = Repository(dbDAO)
    }

    fun loadUserProfile(userRole: String, userId: Int) {
        viewModelScope.launch {
            val profile = when (userRole.lowercase()) {
                "staff" -> {
                    val staff = repository.getStaffById(userId)
                    val departmentName = repository.getDepartmentWithDetails(staff.departmentId).firstOrNull()?.department?.name ?: ""
                    UserProfile(staff.name, departmentName, staff.staffPoint)
                }
                "manager" -> {
                    val manager = repository.getDepartmentManagerById(userId)
                    val departmentName = repository.getDepartmentWithDetails(manager.departmentId).firstOrNull()?.department?.name ?: ""
                    UserProfile(manager.name, departmentName, manager.managerPoint)
                }
                "cto" -> {
                    val cto = repository.getCTOById(userId)
                    UserProfile(cto.name, "", cto.id)
                }
                else -> UserProfile("", "", 0)
            }
            _userProfile.value = profile
        }
    }
}