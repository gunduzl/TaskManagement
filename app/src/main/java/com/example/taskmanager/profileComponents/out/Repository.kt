package com.example.taskmanager.profileComponents.out

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Repository {

    private val employeeList = mutableListOf<Employee>()
    private val taskList = mutableListOf<Task>()
    private val departmentList = mutableListOf<Department>()
    private val taskStaffCrossRefList = mutableListOf<TaskStaffCrossRef>()

    private val mutex = Mutex()

    init {
        // Initialize with some dummy data
        createInitialData()
    }

    private fun createInitialData() {
        // Create Departments
        departmentList.addAll(
            listOf(
                Department(1, "Department 1", "Description 1"),
                Department(2, "Department 2", "Description 2"),
                Department(3, "Department 3", "Description 3")
            )
        )

        // Create Employees
        employeeList.addAll(
            listOf(
                Staff(1, "Alper", "alper@gmail.com", "password", Role.STAFF, 1, 1),
                Staff(2, "Sadık", "sadık@gmail.com", "password", Role.STAFF, 1, 1),
                Staff(3, "Ayşegül", "aysegul@gmail.com", "password", Role.STAFF, 2, 2),
                Staff(4, "Eren", "eren@gmail.com", "password", Role.STAFF, 2, 2),
                Staff(5, "Mehmet", "mehmet@gmail.com", "password", Role.STAFF, 3, 3),
                Staff(6, "Oğuzhan", "oguzhan@gmail.com", "password", Role.STAFF, 3, 3),
                Manager(7, "Gunduz", "gunduz@gmail.com", "password", Role.MANAGER, 1),
                Manager(8, "Ali", "ali@gmail.com", "password", Role.MANAGER, 2),
                Manager(9, "Mert", "mert@gmail.com", "password", Role.MANAGER, 3),
                CTO(10, "Elif", "cto@example.com", "password", Role.CTO)
            )
        )

        // Create Tasks
        taskList.addAll(
            listOf(
                Task(1, "Task 1", "Description 1", TaskStatus.ACTIVE, TaskPriority.HIGH, HelpType.Default, "2023-11-30", 1),
                Task(2, "Task 2", "Description 2", TaskStatus.OPEN, TaskPriority.MEDIUM, HelpType.Default, "2023-11-30", 2),
            )
        )
    }

    suspend fun getEmployeeByEmailAndPassword(email: String, password: String): Employee? = mutex.withLock {
        return employeeList.find { it.email == email && it.password == password }
    }

    suspend fun getStaffById(staffId: Int): Staff? = mutex.withLock {
        return employeeList.filterIsInstance<Staff>().find { it.id == staffId }
    }

    suspend fun insertStaff(staff: Staff) = mutex.withLock {
        employeeList.add(staff)
    }

    suspend fun deleteAllStaff() = mutex.withLock {
        employeeList.removeAll { it is Staff }
    }

    suspend fun insertCTO(cto: CTO) = mutex.withLock {
        employeeList.add(cto)
    }

    suspend fun getCTOById(ctoId: Int): CTO? = mutex.withLock {
        return employeeList.filterIsInstance<CTO>().find { it.id == ctoId }
    }

    suspend fun getCTOWithDepartments(ctoId: Int): List<CTOWithDepartments> = mutex.withLock {
        val cto = employeeList.filterIsInstance<CTO>().find { it.id == ctoId } ?: return emptyList()
        val departments = departmentList
        return listOf(CTOWithDepartments(cto, departments))
    }

    suspend fun insertDepartment(department: Department) = mutex.withLock {
        departmentList.add(department)
    }

    suspend fun getDepartmentWithDetails(departmentId: Int): List<DepartmentWithDetails> = mutex.withLock {
        val department = departmentList.find { it.id == departmentId } ?: return emptyList()
        val manager = employeeList.filterIsInstance<Manager>().find { it.departmentId == departmentId }
        val staff = employeeList.filterIsInstance<Staff>().filter { it.departmentId == departmentId }
        return listOf(DepartmentWithDetails(department, manager, staff))
    }

    suspend fun insertManager(manager: Manager) = mutex.withLock {
        employeeList.add(manager)
    }

    suspend fun getManagerWithStaff(managerId: Int): List<ManagerWithStaff> = mutex.withLock {
        val manager = employeeList.filterIsInstance<Manager>().find { it.id == managerId } ?: return emptyList()
        val staff = employeeList.filterIsInstance<Staff>().filter { it.departmentManagerId == managerId }
        return listOf(ManagerWithStaff(manager, staff))
    }

    suspend fun getManagerById(managerId: Int): Manager? = mutex.withLock {
        return employeeList.filterIsInstance<Manager>().find { it.id == managerId }
    }

    suspend fun insertTask(task: Task) = mutex.withLock {
        taskList.add(task)
    }

    suspend fun insertTaskStaffCrossRef(crossRef: TaskStaffCrossRef) = mutex.withLock {
        taskStaffCrossRefList.add(crossRef)
    }

    suspend fun getTaskFromStaff(staffID: Int): List<TaskWithStaff> = mutex.withLock {
        val tasks = taskStaffCrossRefList.filter { it.staffId == staffID }.mapNotNull { crossRef ->
            taskList.find { it.id == crossRef.taskId }?.let { task ->
                TaskWithStaff(task, employeeList.filterIsInstance<Staff>().filter { it.id == crossRef.staffId })
            }
        }
        return tasks
    }

    suspend fun getStaffFromTask(taskID: Int): List<Staff> = mutex.withLock {
        val staffIds = taskStaffCrossRefList.filter { it.taskId == taskID }.map { it.staffId }
        return employeeList.filterIsInstance<Staff>().filter { it.id in staffIds }
    }

    suspend fun getActiveTasksFromStaff(staffID: Int): List<TaskWithStaff> = mutex.withLock {
        return getTaskFromStaff(staffID).filter { it.task.status == TaskStatus.ACTIVE }
    }

    suspend fun getOpenTasksFromStaff(staffID: Int): List<TaskWithStaff> = mutex.withLock {
        return getTaskFromStaff(staffID).filter { it.task.status == TaskStatus.OPEN }
    }

    suspend fun getTasksByStatus(status: TaskStatus): List<Task> = mutex.withLock {
        return taskList.filter { it.status == status }
    }

    suspend fun getTasksByStatusAndDepartment(status: TaskStatus, departmentId: Int): List<Task> = mutex.withLock {
        return taskList.filter { it.status == status && it.departmentId == departmentId }
    }

    suspend fun takeTask(staffID: Int, taskID: Int) = mutex.withLock {
        taskStaffCrossRefList.add(TaskStaffCrossRef(taskID, staffID))
    }

    suspend fun updateCTO(cto: CTO) = mutex.withLock {
        employeeList.replaceAll { if (it.id == cto.id) cto else it }
    }
}


open class Employee(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: Role
)

class Staff(
    id: Int,
    name: String,
    email: String,
    password: String,
    role: Role,
    val departmentId: Int,
    val departmentManagerId: Int
) : Employee(id, name, email, password, role)

class Manager(
    id: Int,
    name: String,
    email: String,
    password: String,
    role: Role,
    val departmentId: Int
) : Employee(id, name, email, password, role)

class CTO(
    id: Int,
    name: String,
    email: String,
    password: String,
    role: Role
) : Employee(id, name, email, password, role)

class Admin(
    id: Int,
    name: String,
    email: String,
    password: String,
    role: Role
) : Employee(id, name, email, password, role)


enum class TaskStatus {
    OPEN,
    ACTIVE,
    CLOSED
}

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}

enum class Role {
    STAFF,
    MANAGER,
    CTO,
    ADMIN
}

enum class HelpType{
    Default,
    Requested,
    Rejected,
    Confirmed
}

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val priority: TaskPriority,
    val isHelp: HelpType,
    val deadline: String,
    val departmentId: Int
)

data class TaskStaffCrossRef(
    val taskId: Int,
    val staffId: Int
)

data class TaskWithStaff(
    val task: Task,
    val staff: List<Staff>
)

data class CTOWithDepartments(
    val cto: CTO,
    val departments: List<Department>
)

data class DepartmentWithDetails(
    val department: Department,
    val manager: Manager?,
    val staff: List<Staff>
)

data class ManagerWithStaff(
    val manager: Manager,
    val staff: List<Staff>
)
data class Department(
    val id: Int,
    val name: String,
    val description: String
)

