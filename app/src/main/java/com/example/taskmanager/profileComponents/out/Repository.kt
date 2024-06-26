package com.example.taskmanager.profileComponents.out

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Timer
import java.util.TimerTask

class Repository {

    private val employeeList = mutableListOf<Employee>()
    private val taskList = mutableListOf<Task>()
    private val departmentList = mutableListOf<Department>()
    private val taskStaffCrossRefList = mutableListOf<TaskStaffCrossRef>()
    private val notificationList = mutableListOf<Notification>()

    private val mutex = Mutex()

    init {
        // Initialize with some dummy data
        createInitialData()
        // Schedule the monthly evaluation task
        scheduleMonthlyEvaluation()
    }

    private fun createInitialData() {
        // Create Departments
        departmentList.addAll(
            listOf(
                Department(1, "Department 1"),
                Department(2, "Department 2"),
                Department(3, "Department 3")
            )
        )

        // Create Employees
        employeeList.addAll(
            listOf(
                Staff(1, "Alper", "alper@gmail.com", "password", Role.STAFF, 5, StaffStatus.AVAILABLE, 1, 7),
                Staff(2, "Sadık", "sadik@gmail.com", "password", Role.STAFF, 6, StaffStatus.AVAILABLE, 1, 7),
                Staff(3, "Ayşegül", "aysegul@gmail.com", "password", Role.STAFF, 7, StaffStatus.AVAILABLE, 2, 8),
                Staff(4, "Eren", "eren@gmail.com", "password", Role.STAFF, 8, StaffStatus.AVAILABLE, 2, 8),
                Staff(5, "Mehmet", "mehmet@gmail.com", "password", Role.STAFF, 9, StaffStatus.AVAILABLE, 3, 9),
                Staff(6, "Oğuzhan", "oguzhan@gmail.com", "password", Role.STAFF, 10, StaffStatus.AVAILABLE, 3, 9),
                Manager(7, "Gunduz", "gunduz@gmail.com", "password", Role.MANAGER, 5, 1),
                Manager(8, "Ali", "ali@gmail.com", "password", Role.MANAGER, 6, 2),
                Manager(9, "Mert", "mert@gmail.com", "password", Role.MANAGER, 7, 3),
                CTO(10, "Elif", "cto@gmail.com", "password", Role.CTO),
                Admin(11, "Oguzhan", "admin@gmail.com", "password", Role.ADMIN)
            )
        )

        // Create Tasks
        taskList.addAll(
            listOf(
                // Existing Tasks
                Task(1, "Database Methods", "Database methods should be written.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2022-12-31 23:59:59", 1, 5),
                Task(2, "Handle Exceptions", "Exception Library should be written.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2022-12-31 23:59:59", 1, 6),
                Task(3, "Unit Test", "New Unit Tests Are Expected", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2022-12-31 23:59:59", 1, 7),
                Task(4, "Test Security", "Corner Cases Should Be Tested", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2022-12-31 23:59:59", 1, 8),
                Task(5, "Develop UI", "User interface should be developed.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2023-01-31 23:59:59", 1, 6),
                Task(6, "Optimize Performance", "Performance optimization tasks.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2023-02-28 23:59:59", 1, 7),
                Task(7, "Fix Bugs", "Existing bugs should be fixed.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2023-03-31 23:59:59", 1, 4),
                Task(8, "Code Review", "Code review for the project.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2023-04-30 23:59:59", 1, 5),
                Task(9, "Implement Authentication", "Authentication module implementation.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2023-05-31 23:59:59", 2, 8),
                Task(10, "Update Documentation", "Project documentation should be updated.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2023-06-30 23:59:59", 2, 3),

                // New Tasks
                Task(11, "Integrate API", "Integrate third-party API for data.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2023-07-31 23:59:59", 2, 6),
                Task(12, "Improve UX", "Enhance the user experience on the platform.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2023-08-31 23:59:59", 2, 7),
                Task(13, "Write Unit Tests", "Create unit tests for new features.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2023-09-30 23:59:59", 2, 4),
                Task(14, "Set Up CI/CD", "Continuous integration and delivery setup.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2023-10-31 23:59:59", 2, 5),
                Task(15, "Design Database Schema", "Design the schema for the new database.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2023-11-30 23:59:59", 2, 8),
                Task(16, "Implement Caching", "Add caching to improve performance.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2023-12-31 23:59:59", 2, 3),
                Task(17, "Perform Load Testing", "Test system performance under load.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2024-01-31 23:59:59", 3, 6),
                Task(18, "Develop Mobile App", "Create a mobile application.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2024-02-29 23:59:59", 3, 7),
                Task(19, "Optimize Queries", "Optimize SQL queries for performance.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2024-03-31 23:59:59", 3, 4),
                Task(20, "Implement Logging", "Add logging for monitoring purposes.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2024-04-30 23:59:59", 3, 5),
                Task(21, "Design Landing Page", "Create a landing page for the website.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2024-05-31 23:59:59", 3, 8),
                Task(22, "Create API Documentation", "Document the APIs.", TaskStatus.OPEN, TaskDifficulty.LOW, HelpType.Default, System.currentTimeMillis(), "2024-06-30 23:59:59", 3, 3),
                Task(23, "Implement Security Patches", "Apply security patches to the system.", TaskStatus.OPEN, TaskDifficulty.MEDIUM, HelpType.Default, System.currentTimeMillis(), "2024-07-31 23:59:59", 3, 6),
                Task(24, "Improve Accessibility", "Ensure accessibility standards are met.", TaskStatus.OPEN, TaskDifficulty.HIGH, HelpType.Default, System.currentTimeMillis(), "2024-08-31 23:59:59", 3, 7)
            )
        )

        notificationList.addAll(
            listOf(
                Notification(1, "New Message", "You have a new message from Jane", "10:30 AM", Role.STAFF, 1),
                Notification(2, "Reminder", "Don't forget your meeting at 2 PM", "Yesterday", Role.STAFF, 1),
                Notification(3, "New Message", "You have a new message from Jane", "10:30 AM", Role.STAFF, 3),
                Notification(4, "New Message", "You have a new message from Jane", "10:30 AM", Role.STAFF, 4),
                Notification(5, "New Message", "You have a new message from Jane", "10:30 AM", Role.STAFF, 5),
                Notification(6, "New Message", "You have a new message from Jane", "10:30 AM", Role.MANAGER, 8),
                Notification(7, "New Message", "You have a new message from Jane", "10:30 AM", Role.MANAGER, 9),
                Notification(8, "New Message", "You have a new message from Jane", "10:30 AM", Role.CTO, 10)
            )
        )
    }

    // Method to perform monthly evaluation
    suspend fun performMonthlyEvaluation() = mutex.withLock {


        // 2. Send notifications for top 3 performers to each department manager
        departmentList.forEach { department ->
            val staffInDepartment = employeeList.filterIsInstance<Staff>().filter { it.departmentId == department.id }
            val topPerformers = staffInDepartment.sortedByDescending { it.staffPoint }.take(3)
            topPerformers.forEach { staff ->
                notificationList.add(Notification(
                    id = generateNotificationId(),
                    title = "Top Performer",
                    description = "${staff.name} is one of the top 3 performers this month.",
                    date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                    employeeType = Role.MANAGER,
                    employeeId = staff.departmentManagerId
                ))
            }
        }

        // 3. Send notifications for bottom 3 performers to each department manager
        departmentList.forEach { department ->
            val staffInDepartment = employeeList.filterIsInstance<Staff>().filter { it.departmentId == department.id }
            val bottomPerformers = staffInDepartment.sortedBy { it.staffPoint }.take(3)
            bottomPerformers.forEach { staff ->
                notificationList.add(Notification(
                    id = generateNotificationId(),
                    title = "Low Performer",
                    description = "${staff.name} is one of the bottom 3 performers this month.",
                    date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                    employeeType = Role.MANAGER,
                    employeeId = staff.departmentManagerId
                ))
            }
        }

        // 4. Send notification for best department to CTO
        val bestDepartment = departmentList.maxByOrNull { department ->
            employeeList.filterIsInstance<Staff>().filter { it.departmentId == department.id }.sumOf { it.staffPoint }
        }
        bestDepartment?.let {
            notificationList.add(Notification(
                id = generateNotificationId(),
                title = "Best Department",
                description = "${it.name} is the best department this month.",
                date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                employeeType = Role.CTO,
                employeeId = 10 // Assuming CTO ID is 10
            ))
        }

        // send notification to CTO for best 3 staff among all departments
        val bestStaffOverall = employeeList.filterIsInstance<Staff>().sortedByDescending { it.staffPoint }.take(3)
        bestStaffOverall.forEach { staff ->
            notificationList.add(Notification(
                id = generateNotificationId(),
                title = "Top Performer",
                description = "${staff.name} is one of the top 3 performers this month.",
                date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                employeeType = Role.CTO,
                employeeId = 10
            ))
        }


        // 5. Send notifications for bonus and potential raise to managers
        employeeList.filterIsInstance<Staff>().forEach { staff ->
            if (staff.staffPoint > 20) {
                notificationList.add(Notification(
                    id = generateNotificationId(),
                    title = "Bonus and Raise",
                    description = "${staff.name} qualifies for a 5-10% bonus.",
                    date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                    employeeType = Role.MANAGER,
                    employeeId = staff.departmentManagerId
                ))
            } else if (staff.staffPoint > 10) {
                notificationList.add(Notification(
                    id = generateNotificationId(),
                    title = "Bonus",
                    description = "${staff.name} qualifies for a 3-5% bonus.",
                    date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                    employeeType = Role.MANAGER,
                    employeeId = staff.departmentManagerId
                ))
            }
        }

        // 6. Send notification for low performance to managers
        val bottomPerformersOverall = employeeList.filterIsInstance<Staff>().sortedBy { it.staffPoint }.take(2)
        bottomPerformersOverall.forEach { staff ->
            notificationList.add(Notification(
                id = generateNotificationId(),
                title = "Performance Improvement",
                description = "${staff.name} needs improvement. Consider a one-on-one meeting or course assignment.",
                date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                employeeType = Role.MANAGER,
                employeeId = staff.departmentManagerId
            ))
        }


        // Reset all staff scores to zero
        employeeList.filterIsInstance<Staff>().forEach { it.staffPoint = 0 }
    }

    private fun generateNotificationId(): Int {
        return (notificationList.maxOfOrNull { it.id } ?: 0) + 1
    }

    private fun scheduleMonthlyEvaluation() {
        val delay = 3 * 60 * 1000L // 3 minutes in milliseconds
        Timer().schedule(object : TimerTask() {
            override fun run() {
                runBlocking {
                    performMonthlyEvaluation()
                }
            }
        }, delay, delay) // Schedule the task to run every 3 minutes
    }



    // Existing repository methods...

    suspend fun getEmployeeByEmailAndPassword(email: String, password: String): Employee? = mutex.withLock {
        return employeeList.find { it.email == email && it.password == password }
    }

    suspend fun getStaffById(staffId: Int): Staff? = mutex.withLock {
        return employeeList.filterIsInstance<Staff>().find { it.id == staffId }
    }

    fun getStaffList(): List<Staff> {
        return employeeList.filterIsInstance<Staff>()
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

    suspend fun getDepartmentById(departmentId: Int): Department? = mutex.withLock {
        return departmentList.find { it.id == departmentId }
    }

    suspend fun getDepartmentsFromEmployeeID(employeeID: Int): Department? = mutex.withLock {
        val employee = employeeList.find { it.id == employeeID }
        val department = when (employee?.role) {
            Role.STAFF -> employeeList.filterIsInstance<Staff>().find { it.id == employeeID }?.let { staff ->
                departmentList.find { it.id == staff.departmentId }
            }
            Role.MANAGER -> employeeList.filterIsInstance<Manager>().find { it.id == employeeID }?.let { manager ->
                departmentList.find { it.id == manager.departmentId }
            }
            else -> null
        }
        println("getDepartmentsFromEmployeeID: employeeID=$employeeID, department=$department")
        return department
    }

    suspend fun getPointFromEmployeeID(employeeID: Int): Int? = mutex.withLock {
        val employee = employeeList.find { it.id == employeeID }
        return when (employee) {
            is Staff -> employee.staffPoint
            is Manager -> employee.managerPoint
            else -> null
        }
    }

    suspend fun getEmployeeById(employeeId: Int): Employee? = mutex.withLock {
        return employeeList.filterIsInstance<Employee>().find { it.id == employeeId }
    }

    suspend fun submitTask(staffID: Int, taskID: Int) = mutex.withLock {
        val staff = employeeList.filterIsInstance<Staff>().find { it.id == staffID } ?: return
        val taskIndex = taskList.indexOfFirst { it.id == taskID }
        if (taskIndex != -1) {
            val task = taskList[taskIndex]
            if (task.owners.any { it.id == staffID }) {
                task.status = TaskStatus.CLOSED
                taskList[taskIndex] = task

                // Check if all tasks for this staff are closed
                val staffTasks = taskList.filter { it.owners.any { owner -> owner.id == staffID } }
                if (staffTasks.all { it.status == TaskStatus.CLOSED }) {
                    staff.staffStatus = StaffStatus.AVAILABLE
                    val staffIndex = employeeList.indexOfFirst { it.id == staffID }
                    if (staffIndex != -1) {
                        employeeList[staffIndex] = staff
                    }
                }
            }
        }
    }

    suspend fun deleteTask(taskID: Int) = mutex.withLock {
        val taskIndex = taskList.indexOfFirst { it.id == taskID }
        if (taskIndex != -1) {
            taskList.removeAt(taskIndex)
        }
    }



    suspend fun insertTask(task: Task) = mutex.withLock {
        val creationTime = System.currentTimeMillis()
        val newTask = task.copy(creationTime = creationTime)
        taskList.add(newTask)
        scheduleTaskDeadlineNotification(newTask)
    }

    private fun scheduleTaskDeadlineNotification(task: Task) {
        val delay: Long = 30 * 1000L // 30 seconds in milliseconds
        Timer().schedule(object : TimerTask() {
            override fun run() {
                sendTaskDeadlineNotification(task)
            }
        }, delay)
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

    suspend fun getStaffsOfManager(managerId: Int): ManagerWithStaff? = mutex.withLock {
        val manager = employeeList.filterIsInstance<Manager>().find { it.id == managerId } ?: return null
        val staff = employeeList.filterIsInstance<Staff>().filter { it.departmentManagerId == managerId }
        return ManagerWithStaff(manager, staff)
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
        val staff = employeeList.filterIsInstance<Staff>().find { it.id == staffID } ?: return
        taskStaffCrossRefList.add(TaskStaffCrossRef(taskID, staffID))
        val taskIndex = taskList.indexOfFirst { it.id == taskID }
        if (taskIndex != -1) {
            val task = taskList[taskIndex]
            val updatedOwners = task.owners.toMutableList().apply { add(staff) }
            val updatedTask = task.copy(status = TaskStatus.ACTIVE, owners = updatedOwners)
            taskList[taskIndex] = updatedTask
            // Update staff status to BUSY
            staff.staffStatus = StaffStatus.BUSY
            // Update the staff in the employee list
            val staffIndex = employeeList.indexOfFirst { it.id == staffID }
            if (staffIndex != -1) {
                employeeList[staffIndex] = staff
            }
            scheduleTaskDeadlineNotification(updatedTask)
        }
    }

    suspend fun updateCTO(cto: CTO) = mutex.withLock {
        employeeList.replaceAll { if (it.id == cto.id) cto else it }
    }

    suspend fun updateStaff(staff: Staff) = mutex.withLock {
        employeeList.replaceAll { if (it.id == staff.id) staff else it }
    }

    suspend fun updateTaskStatus(taskId: Int, newStatus: TaskStatus) = mutex.withLock {
        val taskIndex = taskList.indexOfFirst { it.id == taskId }
        if (taskIndex != -1) {
            val task = taskList[taskIndex]
            taskList[taskIndex] = task.copy(status = newStatus)
        }
    }

    private fun sendTaskDeadlineNotification(task: Task) {
        if (task.status==TaskStatus.OPEN) {
            val relatedStaff = task.owners
            relatedStaff.forEach { staff ->
                val notification = Notification(
                    id = generateNotificationId(),
                    title = "Task Deadline Reached",
                    description = "The deadline for task '${task.title}' has been reached.",
                    date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date()),
                    employeeType = Role.STAFF,
                    employeeId = staff.id
                )
                runBlocking {
                    insertNotification(notification)
                }
            }
        }
    }

    suspend fun deleteEmployeeById(employeeId: Int) = mutex.withLock {
        val employeeToDelete = employeeList.find { it.id == employeeId }
        if (employeeToDelete != null) {
            employeeList.remove(employeeToDelete)
        }
    }

    suspend fun updateEmployeeRole(employeeId: Int, newRole: Role) = mutex.withLock {
        val employeeIndex = employeeList.indexOfFirst { it.id == employeeId }
        if (employeeIndex != -1) {
            val existingEmployee = employeeList[employeeIndex]
            val previousDepartmentId: Int? = when (existingEmployee) {
                is Staff -> existingEmployee.departmentId
                is Manager -> existingEmployee.departmentId
                else -> null
            }
            val previousDepartmentManagerId: Int? = when (existingEmployee) {
                is Staff -> existingEmployee.departmentManagerId
                else -> null
            }

            val departmentId = previousDepartmentId ?: 0
            val departmentManagerId = previousDepartmentManagerId ?: 0

            val newEmployee = when (newRole) {
                Role.STAFF -> {
                    val staff = existingEmployee as? Staff
                    Staff(
                        existingEmployee.id,
                        existingEmployee.name,
                        existingEmployee.email,
                        existingEmployee.password,
                        newRole,
                        staff?.staffPoint ?: 0,
                        staff?.staffStatus ?: StaffStatus.AVAILABLE,
                        departmentId,
                        departmentManagerId
                    )
                }
                Role.MANAGER -> {
                    val manager = existingEmployee as? Manager
                    Manager(
                        existingEmployee.id,
                        existingEmployee.name,
                        existingEmployee.email,
                        existingEmployee.password,
                        newRole,
                        manager?.managerPoint ?: 0,
                        departmentId
                    )
                }
                Role.CTO -> {
                    CTO(
                        existingEmployee.id,
                        existingEmployee.name,
                        existingEmployee.email,
                        existingEmployee.password,
                        newRole
                    )
                }
                else -> existingEmployee // For ADMIN or other cases, keep the existing employee
            }
            // Replace the existing employee with the new one
            employeeList[employeeIndex] = newEmployee
        }
    }

    suspend fun getManagerIdByDepartmentName(departmentName: String): Int? = mutex.withLock {
        val departmentId = departmentList.find { it.name == departmentName }?.id
        return employeeList.filterIsInstance<Manager>().find { it.departmentId == departmentId }?.id
    }

    suspend fun getDepartmentIdByName(departmentName: String): Int? = mutex.withLock {
        return departmentList.find { it.name == departmentName }?.id
    }

    suspend fun getAllEmployees(): List<Employee> = mutex.withLock {
        return employeeList.toList()
    }

    suspend fun getNotificationsById(employeeId: Int): List<Notification> = mutex.withLock {
        return notificationList.filter { it.employeeId == employeeId }
    }

    suspend fun insertNotification(notification: Notification) {
        mutex.withLock {
            notificationList.add(notification)
        }
    }

    suspend fun deleteNotification(notification: Notification) = mutex.withLock {
        notificationList.removeIf { it.id == notification.id }
    }


    suspend fun deleteDepartmentByName(departmentName: String) = mutex.withLock {
        val departmentToDelete = departmentList.find { it.name == departmentName }
        if (departmentToDelete != null) {
            // Remove employees associated with the department
            val employeesToRemove = mutableListOf<Employee>()
            employeesToRemove.addAll(employeeList.filter { (it is Staff) && it.departmentId == departmentToDelete.id })
            employeesToRemove.addAll(employeeList.filter { (it is Manager) && it.departmentId == departmentToDelete.id })
            employeeList.removeAll(employeesToRemove)

            // Remove the department
            departmentList.remove(departmentToDelete)
        }
    }

    suspend fun deleteDepartmentById(departmentId: Int) = mutex.withLock {
        val departmentToDelete = departmentList.find { it.id == departmentId }
        if (departmentToDelete != null) {
            // Remove employees associated with the department
            val employeesToRemove = mutableListOf<Employee>()
            employeesToRemove.addAll(employeeList.filter { (it is Staff) && it.departmentId == departmentToDelete.id })
            employeesToRemove.addAll(employeeList.filter { (it is Manager) && it.departmentId == departmentToDelete.id })
            employeeList.removeAll(employeesToRemove)

            // Remove the department
            departmentList.remove(departmentToDelete)
        }
    }

    suspend fun getAllDepartments(): List<Department> = mutex.withLock {
        return departmentList.toList()
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
    // Alper
    var staffPoint: Int,
    var staffStatus: StaffStatus,
    val departmentId: Int,
    val departmentManagerId: Int,
    val pointsList: MutableList<Int> = mutableListOf()
) : Employee(id, name, email, password, role)

class Manager(
    id: Int,
    name: String,
    email: String,
    password: String,
    role: Role,
    var managerPoint: Int,
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

enum class StaffStatus {
    AVAILABLE,
    BUSY
}

enum class TaskStatus {
    OPEN,
    ACTIVE,
    CLOSED
}

enum class TaskDifficulty {
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

enum class HelpType {
    Default,
    Requested,
    Rejected,
    Confirmed
}

data class Task(
    val id: Int,
    var title: String,
    val description: String,
    var status: TaskStatus,
    val difficulty: TaskDifficulty,
    var isHelp: HelpType,
    val creationTime: Long, // new field
    val deadline: String, // new field
    val departmentId: Int,
    val taskPoint: Int,
    val owners: List<Staff> = emptyList()
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
    val name: String
)

data class Notification(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val employeeType: Role,
    val employeeId: Int
)