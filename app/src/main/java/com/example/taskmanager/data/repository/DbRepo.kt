package com.example.taskmanager.data.repository

import android.content.Context
import com.example.taskmanager.data.Db
import com.example.taskmanager.data.map.*

class DbRepo(context: Context) {
    private val _db = Db.getDatabase(context)


    suspend fun getStaffById(staffId: Int): Staff {
        return _db.mapDao().getStaffById(staffId)
    }

    suspend fun insertStaff(staff: Staff){
        _db.mapDao().insertStaff(staff)

    }

    suspend fun deleteAllStaff() {
        _db.mapDao().deleteAllStaff()
    }

    // CTO Operations
    suspend fun insertCTO(cto: CTO) {
        _db.mapDao().insertCTO(cto)
    }

    suspend fun getCTOById(ctoId: Int): CTO {
        return _db.mapDao().getCTOById(ctoId)
    }

    suspend fun getCTOWithDepartments(ctoId: Int): List<CTOWithDepartments> {
        return _db.mapDao().getCTOWithDepartments(ctoId)
    }

    // Department Operations
    suspend fun insertDepartment(department: Department) {
        _db.mapDao().insertDepartment(department)
    }

    suspend fun getDepartmentWithDetails(departmentId: Int): List<DepartmentWithDetails> {
        return _db.mapDao().getDepartmentWithDetails(departmentId)
    }

    // Department Manager Operations
    suspend fun insertDepartmentManager(manager: DepartmentManager) {
        _db.mapDao().insertDepartmentManager(manager)
    }

    suspend fun getDepartmentManagerWithStaff(managerId: Int): List<DepartmentManagerWithStaff> {
        return _db.mapDao().getDepartmentManagerWithStaff(managerId)
    }

    suspend fun getDepartmentManagerById(managerId: Int): DepartmentManager {
        return _db.mapDao().getDepartmentManagerById(managerId)
    }



    // Task Operations
    suspend fun insertTask(task: Task) {
        _db.mapDao().insertTask(task)
    }

    suspend fun insertTaskStaffCrossRef(crossRef: TaskStaffCrossRef) {
        _db.mapDao().insertTaskStaffCrossRef(crossRef)
    }

    suspend fun getTaskFromStaff(staffID: Int): List<TaskWithStaff> {
        return _db.mapDao().getTaskFromStaff(staffID)
    }

    suspend fun getStaffFromTask(taskID: Int): List<Staff> {
        return _db.mapDao().getStaffFromTask(taskID)
    }

    suspend fun getActiveTasksFromStaff(staffID: Int): List<TaskWithStaff> {
        return _db.mapDao().getTaskFromStaff(staffID).filter { it.task.status == "Active" }
    }

    suspend fun getOpenTasksFromStaff(staffID: Int): List<TaskWithStaff> {
        return _db.mapDao().getTaskFromStaff(staffID).filter { it.task.status == "Open" }
    }

    suspend fun getTasksByStatus(status: String): List<Task> {
        return _db.mapDao().getTasksByStatus(status)
    }

    suspend fun getTasksByStatusAndDepartment(status: String, departmentId: Int): List<Task> {
        return _db.mapDao().getTasksByStatusAndDepartment(status, departmentId)
    }

    suspend fun takeTask(staffID: Int, taskID: Int) {
        _db.mapDao().insertTaskStaffCrossRef(TaskStaffCrossRef(taskID, staffID))
    }


    // Update operations if needed
    suspend fun updateCTO(cto: CTO) {
        _db.mapDao().updateCTO(cto)
    }

    suspend fun createStaff(){
        _db.mapDao().insertStaff(
            Staff(
                name= "Alper",
                email =  "alper@gmail.com",
                password = "1234",
                role= "staff",
                departmentId = 1,
                departmentManagerId = 1
            )
        )
        _db.mapDao().insertStaff(
            Staff(
                name= "Sadık",
                email =  "sadık@gmail.com",
                password = "1234",
                role= "staff",
                departmentId = 1,
                departmentManagerId = 1
            )
        )
    }


}
