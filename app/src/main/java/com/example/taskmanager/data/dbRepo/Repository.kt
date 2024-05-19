package com.example.taskmanager.data.dbRepo

import com.example.taskmanager.data.dao.DbDAO
import com.example.taskmanager.data.entities.CTO
import com.example.taskmanager.data.entities.CTOWithDepartments
import com.example.taskmanager.data.entities.Department
import com.example.taskmanager.data.entities.DepartmentManager
import com.example.taskmanager.data.entities.DepartmentManagerWithStaff
import com.example.taskmanager.data.entities.DepartmentWithDetails
import com.example.taskmanager.data.entities.Staff
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.data.entities.TaskStaffCrossRef
import com.example.taskmanager.data.entities.TaskWithStaff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(private val dbDAO: DbDAO) {

    // CTO Operations
    suspend fun insertCTO(cto: CTO) {
        dbDAO.insertCTO(cto)
    }

    suspend fun getCTOById(ctoId: Int): CTO {
        return dbDAO.getCTOById(ctoId)
    }

    suspend fun getCTOWithDepartments(ctoId: Int): List<CTOWithDepartments> {
        return dbDAO.getCTOWithDepartments(ctoId)
    }

    // Department Operations
    suspend fun insertDepartment(department: Department) {
        dbDAO.insertDepartment(department)
    }

    suspend fun getDepartmentWithDetails(departmentId: Int): List<DepartmentWithDetails> {
        return dbDAO.getDepartmentWithDetails(departmentId)
    }

    // Department Manager Operations
    suspend fun insertDepartmentManager(manager: DepartmentManager) {
        dbDAO.insertDepartmentManager(manager)
    }

    suspend fun getDepartmentManagerWithStaff(managerId: Int): List<DepartmentManagerWithStaff> {
        return dbDAO.getDepartmentManagerWithStaff(managerId)
    }

    suspend fun getDepartmentManagerById(managerId: Int): DepartmentManager {
        return dbDAO.getDepartmentManagerById(managerId)
    }

    // Staff Operations
    suspend fun insertStaff(staff: Staff) {
        dbDAO.insertStaff(staff)
    }

    suspend fun getStaffById(staffId: Int): Staff {
        return dbDAO.getStaffById(staffId)
    }

    // Task Operations
    suspend fun insertTask(task: Task) {
        dbDAO.insertTask(task)
    }

    suspend fun insertTaskStaffCrossRef(crossRef: TaskStaffCrossRef) {
        dbDAO.insertTaskStaffCrossRef(crossRef)
    }

    suspend fun getTaskFromStaff(staffID: Int): List<TaskWithStaff> {
        return dbDAO.getTaskFromStaff(staffID)
    }

    suspend fun getStaffFromTask(taskID: Int): List<TaskWithStaff> {
        return dbDAO.getStaffFromTask(taskID)
    }

    suspend fun getActiveTasksFromStaff(staffID: Int): List<TaskWithStaff> {
        return dbDAO.getTaskFromStaff(staffID).filter { it.task.status == "Active" }
    }

    suspend fun getOpenTasksFromStaff(staffID: Int): List<TaskWithStaff> {
        return dbDAO.getTaskFromStaff(staffID).filter { it.task.status == "Open" }
    }

    suspend fun getTasksByStatus(status: String): List<Task> {
        return dbDAO.getTasksByStatus(status)
    }

    suspend fun getTasksByStatusAndDepartment(status: String, departmentId: Int): List<Task> {
        return dbDAO.getTasksByStatusAndDepartment(status, departmentId)
    }

    suspend fun takeTask(staffID: Int, taskID: Int) {
        dbDAO.insertTaskStaffCrossRef(TaskStaffCrossRef(taskID, staffID))
    }

    suspend fun getCompleteTask(staffID: Int): List<TaskWithStaff> {
        return dbDAO.getTaskFromStaff(staffID).filter { it.task.isFinished == true }
    }

    suspend fun getIncompleteTask(staffID: Int): List<TaskWithStaff> {
        return dbDAO.getTaskFromStaff(staffID).filter { it.task.isFinished == false }
    }

    // Update operations if needed
    suspend fun updateCTO(cto: CTO) {
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

    // Authentication
    suspend fun authenticateStaff(email: String, password: String): Staff? {
        return dbDAO.authenticateStaff(email, password)
    }

    suspend fun authenticateManager(email: String, password: String): DepartmentManager? {
        return dbDAO.authenticateManager(email, password)
    }

    suspend fun authenticateCTO(email: String, password: String): CTO? {
        return dbDAO.authenticateCTO(email, password)
    }
}
