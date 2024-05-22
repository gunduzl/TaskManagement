package com.example.taskmanager.presentation.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.datastore.SessionTokenDataStore
import com.example.taskmanager.data.repository.DbRepo
import kotlinx.coroutines.launch



class LoginScreenViewModel: ViewModel() {

    var email = MutableLiveData("")
    var password = MutableLiveData("")
    var errorMessage = MutableLiveData("")
    var wrongLogin = MutableLiveData(false)

    fun signIn(repo: DbRepo, action: () -> Unit, tokenStore: SessionTokenDataStore) {
        viewModelScope.launch {
            val user = repo.searchForUser(mail = email.value!!, password = password.value!!)
            if (user == null) {
                wrongLogin.value = true
            } else {
                tokenStore.storeSessionToken(user.userId)
                SessionToken.initToken(user)
                action()
            }
        }
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

}