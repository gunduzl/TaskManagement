package com.example.taskmanager.data.dbRepo

import AppDatabase
import com.example.taskmanager.data.entities.CTO
import com.example.taskmanager.data.entities.Department
import com.example.taskmanager.data.entities.DepartmentManager
import com.example.taskmanager.data.entities.Staff
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.data.entities.TaskStaffCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val appDatabase: AppDatabase) {
    private val dbDAO = appDatabase.dbDAO()

    // CTO Operations
    suspend fun insertCTO(cto: CTO) = withContext(Dispatchers.IO) {
        dbDAO.insertCTO(cto)
    }

    suspend fun getCTOById(ctoId: Int) = withContext(Dispatchers.IO) {
        dbDAO.getCTOById(ctoId)
    }

    suspend fun getCTOWithDepartments(ctoId: Int) = withContext(Dispatchers.IO) {
        dbDAO.getCTOWithDepartments(ctoId)
    }

    // Department Operations
    suspend fun insertDepartment(department: Department) = withContext(Dispatchers.IO) {
        dbDAO.insertDepartment(department)
    }

    suspend fun getDepartmentWithDetails(departmentId: Int) = withContext(Dispatchers.IO) {
        dbDAO.getDepartmentWithDetails(departmentId)
    }

    // Department Manager Operations
    suspend fun insertDepartmentManager(manager: DepartmentManager) = withContext(Dispatchers.IO) {
        dbDAO.insertDepartmentManager(manager)
    }

    suspend fun getDepartmentManagerWithStaff(managerId: Int) = withContext(Dispatchers.IO) {
        dbDAO.getDepartmentManagerWithStaff(managerId)
    }

    // Staff Operations
    suspend fun insertStaff(staff: Staff) = withContext(Dispatchers.IO) {
        dbDAO.insertStaff(staff)
    }

    suspend fun getStaffById(staffId: Int) = withContext(Dispatchers.IO) {
        dbDAO.getStaffById(staffId)
    }

    // Task Operations
    suspend fun insertTask(task: Task) = withContext(Dispatchers.IO) {
        dbDAO.insertTask(task)
    }

    suspend fun insertTaskStaffCrossRef(crossRef: TaskStaffCrossRef) = withContext(Dispatchers.IO) {
        dbDAO.insertTaskStaffCrossRef(crossRef)
    }

    suspend fun getTaskWithStaff(taskId: Int) = withContext(Dispatchers.IO) {
        dbDAO.getTaskWithStaff(taskId)
    }

    // Update operations if needed
    suspend fun updateCTO(cto: CTO) = withContext(Dispatchers.IO) {
        dbDAO.updateCTO(cto)
    }

    suspend fun updateDepartment(department: Department) = withContext(Dispatchers.IO) {
        dbDAO.updateDepartment(department)
    }

    suspend fun updateDepartmentManager(manager: DepartmentManager) = withContext(Dispatchers.IO) {
        dbDAO.updateDepartmentManager(manager)
    }

    suspend fun updateStaff(staff: Staff) = withContext(Dispatchers.IO) {
        dbDAO.updateStaff(staff)
    }

    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) {
        dbDAO.updateTask(task)
    }
}
