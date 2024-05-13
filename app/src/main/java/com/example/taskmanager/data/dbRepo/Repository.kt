package com.example.taskmanager.data.dbRepo

import AppDatabase
import android.content.Context

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

class Repository(context: Context) {

    private val DB = AppDatabase.getDatabase(context)


    // CTO Operations
    suspend fun insertCTO(cto: CTO) {
        DB.dbDAO().insertCTO(cto)
    }

    suspend fun getCTOById(ctoId: Int): CTO{
        return DB.dbDAO().getCTOById(ctoId);
    }

    suspend fun getCTOWithDepartments(ctoId: Int): List<CTOWithDepartments> {
        return DB.dbDAO().getCTOWithDepartments(ctoId);
    }

    // Department Operations
    suspend fun insertDepartment(department: Department) {
        DB.dbDAO().insertDepartment(department)
    }

    suspend fun getDepartmentWithDetails(departmentId: Int): List<DepartmentWithDetails> {
        return DB.dbDAO().getDepartmentWithDetails(departmentId);
    }

    // Department Manager Operations
    suspend fun insertDepartmentManager(manager: DepartmentManager) {
        DB.dbDAO().insertDepartmentManager(manager)
    }

    suspend fun getDepartmentManagerWithStaff(managerId: Int): List<DepartmentManagerWithStaff> {
        return DB.dbDAO().getDepartmentManagerWithStaff(managerId)
    }

    // Staff Operations
    suspend fun insertStaff(staff: Staff){
        DB.dbDAO().insertStaff(staff)
    }

    suspend fun getStaffById(staffId: Int): Staff {
        return DB.dbDAO().getStaffById(staffId)
    }

    // Task Operations
    suspend fun insertTask(task: Task) {
        DB.dbDAO().insertTask(task)
    }

    suspend fun insertTaskStaffCrossRef(crossRef: TaskStaffCrossRef) {
        DB.dbDAO().insertTaskStaffCrossRef(crossRef) // Inserting the cross reference between Task and Staff
    }

    suspend fun getTaskFromStaff(staffID: Int): List<TaskWithStaff>{ //
        return DB.dbDAO().getTaskFromStaff(staffID)
    }


    suspend fun getStaffFromTask(taskID: Int): List<TaskWithStaff>{
        return DB.dbDAO().getStaffFromTask(taskID)
    }


    suspend fun getActiveTasksFromStaff(staffID: Int): List<TaskWithStaff>{
        return DB.dbDAO().getTaskFromStaff(staffID).filter { it.task.status == "Active" }
    }

    suspend fun getOpenTasksFromStaff(staffID: Int): List<TaskWithStaff>{
        return DB.dbDAO().getTaskFromStaff(staffID).filter { it.task.status == "Open" }
    }

    suspend fun takeTask(staffID: Int, taskID: Int){
        DB.dbDAO().insertTaskStaffCrossRef(TaskStaffCrossRef(taskID, staffID))
    }

    suspend fun getCompleteTask(staffID: Int): List<TaskWithStaff>{
        DB.dbDAO().getTaskFromStaff(staffID).forEach {
            if(it.task.isFinished == true){
                return listOf(it) // return the task if it is completed
            }
        }
        return listOf() // return empty list if no task is completed
    }


    suspend fun getIncompleteTask(staffID: Int): List<TaskWithStaff>{
        DB.dbDAO().getTaskFromStaff(staffID).forEach {
            if(it.task.isFinished == false){
                return listOf(it) // return the task if it is not completed
            }
        }
        return listOf() // return empty list if no task is incomplete
    }

    



    // Update operations if needed
    suspend fun updateCTO(cto: CTO) {
        DB.dbDAO().updateCTO(cto)
    }

    suspend fun updateDepartment(department: Department) = withContext(Dispatchers.IO) {
        DB.dbDAO().updateDepartment(department)
    }

    suspend fun updateDepartmentManager(manager: DepartmentManager) = withContext(Dispatchers.IO) {
        DB.dbDAO().updateDepartmentManager(manager)
    }

    suspend fun updateStaff(staff: Staff) = withContext(Dispatchers.IO) {
        DB.dbDAO().updateStaff(staff)
    }

    suspend fun updateTask(task: Task) = withContext(Dispatchers.IO) {
        DB.dbDAO().updateTask(task)
    }



}
