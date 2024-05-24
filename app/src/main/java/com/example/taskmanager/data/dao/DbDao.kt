package com.example.taskmanager.data.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskmanager.data.map.CTO
import com.example.taskmanager.data.map.CTOWithDepartments
import com.example.taskmanager.data.map.Department
import com.example.taskmanager.data.map.DepartmentManager
import com.example.taskmanager.data.map.DepartmentManagerWithStaff
import com.example.taskmanager.data.map.DepartmentWithDetails
import com.example.taskmanager.data.map.Notification
import com.example.taskmanager.data.map.Staff
import com.example.taskmanager.data.map.Task
import com.example.taskmanager.data.map.TaskStaffCrossRef
import com.example.taskmanager.data.map.TaskWithStaff

@Dao
interface DbDao {


    @Query("SELECT * FROM Notification WHERE employeeId = :employeeId AND employeeType = :employeeType")
    suspend fun getNotificationsByEmployeeIdAndUserType(employeeId: Int, employeeType: String): List<Notification>
    @Query("SELECT * FROM Staff WHERE staffId = :staffId")
    suspend fun getStaffById(staffId: Int): Staff

    @Insert
    suspend fun insertStaff(staff: Staff)

    @Query("DELETE FROM Staff")
    suspend fun deleteAllStaff()

    // CTO methods
    @Insert
    suspend fun insertCTO(cto: CTO)

    @Query("SELECT * FROM CTO WHERE ctoId = :ctoId")
    suspend fun getCTOById(ctoId: Int): CTO

    @Transaction
    @Query("SELECT * FROM CTO WHERE ctoId = :ctoId")
    suspend fun getCTOWithDepartments(ctoId: Int): List<CTOWithDepartments>

    // Department methods
    @Insert
    suspend fun insertDepartment(department: Department)

    @Transaction
    @Query("SELECT * FROM Department WHERE departmentId = :departmentId")
    suspend fun getDepartmentWithDetails(departmentId: Int): List<DepartmentWithDetails>

    // Department Manager methods
    @Insert
    suspend fun insertDepartmentManager(manager: DepartmentManager)

    @Transaction
    @Query("SELECT * FROM DepartmentManager WHERE departmentManagerId = :managerId")
    suspend fun getDepartmentManagerWithStaff(managerId: Int): List<DepartmentManagerWithStaff>

    // Staff methods


    @Query("SELECT * FROM DepartmentManager WHERE departmentManagerId = :managerId")
    suspend fun getDepartmentManagerById(managerId: Int): DepartmentManager



    @Query("SELECT * FROM Staff WHERE departmentManagerId = :managerId")
    fun getStaffByManager(managerId: Int): List<Staff>


    // Task methods
    @Insert
    suspend fun insertTask(task: Task)

    @Insert
    suspend fun insertTaskStaffCrossRef(crossRef: TaskStaffCrossRef)


    @Transaction
    @Query("SELECT Task.departmentId,Task.taskId,Task.deadline,Task.description,Task.priority,Task.status,Task.title FROM Task JOIN TaskStaffCrossRef ON  Task.taskId = TaskStaffCrossRef.taskId JOIN Staff ON Staff.staffId = TaskStaffCrossRef.staffId WHERE TaskStaffCrossRef.staffId = :staffId") // Get all tasks assigned to a staff
    suspend fun getTaskFromStaff(staffId: Int): List<TaskWithStaff>


    @Transaction
    @Query("SELECT * FROM Task WHERE status = :status AND departmentId = :departmentId")
    suspend fun getTasksByStatusAndDepartment(status: String, departmentId: Int): List<Task>

    @Transaction
    //@Query("SELECT * FROM TaskStaffCrossRef WHERE taskId = :taskId") // Get all staff assigned to a task
    @Query("SELECT Staff.staffId, Staff.departmentId,Staff.departmentManagerId,Staff.email,Staff.name,Staff.password,Staff.role FROM Staff JOIN TaskStaffCrossRef ON  Staff.staffId = TaskStaffCrossRef.staffId JOIN Task ON Task.taskId = TaskStaffCrossRef.taskId WHERE Task.taskId = :taskId")
    suspend fun getStaffFromTask(taskId: Int): List<Staff>


    @Query("SELECT * FROM Task WHERE status = :status")
    suspend fun getTasksByStatus(status: String): List<Task>

    // Update methods if needed
    @Update
    suspend fun updateCTO(cto: CTO)

    @Update
    suspend fun updateDepartment(department: Department)

    @Update
    suspend fun updateDepartmentManager(manager: DepartmentManager)

    @Update
    suspend fun updateStaff(staff: Staff)

    @Update
    suspend fun updateTask(task: Task)

    // authentication
    @Query("SELECT * FROM Staff WHERE email = :email AND password = :password")
    suspend fun authenticateStaff(email: String, password: String): Staff?

    @Query("SELECT * FROM DepartmentManager WHERE email = :email AND password = :password")
    suspend fun authenticateManager(email: String, password: String): DepartmentManager?

    @Query("SELECT * FROM CTO WHERE email = :email AND password = :password")
    suspend fun authenticateCTO(email: String, password: String): CTO?
}