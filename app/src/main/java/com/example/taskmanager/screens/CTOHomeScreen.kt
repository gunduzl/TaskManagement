package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
<<<<<<< HEAD
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
=======
import androidx.compose.foundation.layout.*
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
<<<<<<< HEAD
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
=======
import androidx.compose.material3.*
import androidx.compose.runtime.*
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.components.pool.Pool
<<<<<<< HEAD
import com.example.taskmanager.profileComponents.out.Department
import com.example.taskmanager.profileComponents.out.HelpType
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskDifficulty
import com.example.taskmanager.profileComponents.out.TaskStatus
=======
import com.example.taskmanager.profileComponents.out.*
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
import com.example.taskmanager.systems.EvaluationSystem
import kotlinx.coroutines.launch

@Composable
fun CTOHomeScreen(repo: Repository, ctoId: Int) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var taskDifficulty by remember { mutableStateOf(TaskDifficulty.LOW) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val (selectedDepartmentId, setSelectedDepartmentId) = remember { mutableStateOf<Int?>(null) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var departments by remember { mutableStateOf(emptyList<Department>()) }
    var openTasks by remember { mutableStateOf(emptyList<Task>()) }
    var activeTasks by remember { mutableStateOf(emptyList<Task>()) }

<<<<<<< HEAD
    fun refreshTasks(departmentId: Int) {
        coroutineScope.launch {
            openTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, departmentId)
            activeTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE, departmentId)
        }
    }

    LaunchedEffect(ctoId) {
        coroutineScope.launch {
            val ctoWithDepartments = repo.getCTOWithDepartments(ctoId)
            if (ctoWithDepartments.isNotEmpty()) {
                departments = ctoWithDepartments.first().departments
                if (departments.isNotEmpty()) {
                    setSelectedDepartmentId(departments.first().id)
                }
            }
        }
=======
    var department1OpenTasks by remember { mutableStateOf(emptyList<Task>()) }
    var department2OpenTasks by remember { mutableStateOf(emptyList<Task>()) }
    var department3OpenTasks by remember { mutableStateOf(emptyList<Task>()) }

    var department1ActiveTasks by remember { mutableStateOf(emptyList<Task>()) }
    var department2ActiveTasks by remember { mutableStateOf(emptyList<Task>()) }
    var department3ActiveTasks by remember { mutableStateOf(emptyList<Task>()) }

    fun refreshTasks() {
        coroutineScope.launch {
            department1OpenTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, 1)
            department2OpenTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, 2)
            department3OpenTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, 3)

        department1ActiveTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE, 1)
            department2ActiveTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE, 2)
            department3ActiveTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE, 3)
        }
    }


    LaunchedEffect(Unit) {
        refreshTasks()
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
    }

    LaunchedEffect(selectedDepartmentId) {
        selectedDepartmentId?.let {
            refreshTasks(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { setShowNotification(true) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
            Button(
                onClick = { showAddDialog = true },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "My Departments",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Department Navigation Bar
        DepartmentNavigationBar(departments, selectedDepartmentId, setSelectedDepartmentId)

        Spacer(modifier = Modifier.height(20.dp))

        // Pools for open and active tasks
        selectedDepartmentId?.let { departmentId ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Pool(ctoId, "Open", Color(0x666650a4), openTasks, false, repo) { refreshTasks(departmentId) }
                Pool(ctoId, "Active", Color(0x666790a4), activeTasks, false, repo) { refreshTasks(departmentId) }
            }
        }
        Spacer(modifier = Modifier.width(50.dp))
        Button(onClick = {
            showAddDialog = true
            taskName = ""
            taskDescription = ""
            taskDueDate = ""
            dropdownExpanded = false
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                taskName = ""
                taskDescription = ""
                taskDueDate = ""
                taskDifficulty = TaskDifficulty.LOW
                dropdownExpanded = false
            },
            title = { Text("Add New Task", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                        .padding(16.dp)
                ) {
                    TextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text("Enter Task Name:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Enter Task Description:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                        modifier = Modifier.height(100.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = taskDueDate,
                        onValueChange = { taskDueDate = it },
                        label = { Text("Enter Due Date:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { dropdownExpanded = true }
                    ) {
                        Text(
                            text = "Select Difficulty:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = taskDifficulty.name
                        )
                    }
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier
                    ) {
                        DropdownMenuItem(text = { Text("Easy") }, onClick = {
                            taskDifficulty = TaskDifficulty.LOW
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Medium") }, onClick = {
                            taskDifficulty = TaskDifficulty.MEDIUM
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Hard") }, onClick = {
                            taskDifficulty = TaskDifficulty.HIGH
                            dropdownExpanded = false
                        })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
<<<<<<< HEAD
                        coroutineScope.launch {
                            val cto = repo.getCTOById(ctoId)
                            if (cto != null && selectedDepartmentId != null) {
                                val task = Task(
                                    id = 0, // Replace with your task ID generation logic
                                    title = taskName,
                                    description = taskDescription,
                                    status = TaskStatus.OPEN,
                                    difficulty = taskDifficulty,
                                    isHelp = HelpType.Default,
                                    deadline = taskDueDate,
                                    taskPoint = EvaluationSystem().evaluateTaskPoint(taskDifficulty),
                                    departmentId = selectedDepartmentId
                                )
                                repo.insertTask(task)
                                refreshTasks(selectedDepartmentId)
                            }
                        }
                        showAddDialog = false
=======
                        if (taskName.isEmpty() || taskDescription.isEmpty() || taskDueDate.isEmpty()) {
                            errorMessage = "Please fill in all fields."
                            showErrorDialog = true
                        } else {
                            coroutineScope.launch {
                                val cto = repo.getCTOById(ctoId)
                                if (cto != null) {
                                    val task = Task(
                                        id = 0, // Replace with your task ID generation logic
                                        title = taskName,
                                        description = taskDescription,
                                        status = TaskStatus.OPEN,
                                        difficulty = taskDifficulty,
                                        isHelp = HelpType.Default,
                                        deadline = taskDueDate,
                                        taskPoint = EvaluationSystem().evaluateTaskPoint(taskDifficulty),
                                        departmentId = when (selectedTeam) {
                                            CTODepartment.DEPARTMENT_1 -> 1
                                            CTODepartment.DEPARTMENT_2 -> 2
                                            CTODepartment.DEPARTMENT_3 -> 3
                                        }
                                    )
                                    repo.insertTask(task)
                                    refreshTasks()
                                }
                            }
                            showAddDialog = false
                        }
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
                    },
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showAddDialog = false
                        taskName = ""
                        taskDescription = ""
                        taskDueDate = ""
                        taskDifficulty = TaskDifficulty.LOW
                        dropdownExpanded = false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error", fontWeight = FontWeight.Bold) },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) }, repo = repo, employeeId = ctoId)
<<<<<<< HEAD
=======
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 65.dp)
    ) {
        TeamNavigationBar(selectedTeam, setSelectedTeam)
        when (selectedTeam) {
            CTODepartment.DEPARTMENT_1 -> DepartmentPools(department1OpenTasks, department1ActiveTasks, repo, ::refreshTasks)
            CTODepartment.DEPARTMENT_2 -> DepartmentPools(department2OpenTasks, department2ActiveTasks, repo, ::refreshTasks)
            CTODepartment.DEPARTMENT_3 -> DepartmentPools(department3OpenTasks, department3ActiveTasks, repo, ::refreshTasks)
        }
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
    }
}

@Composable
<<<<<<< HEAD
fun DepartmentNavigationBar(
    departments: List<Department>,
    selectedDepartmentId: Int?,
    onDepartmentSelected: (Int) -> Unit
=======
fun DepartmentPools(openTasks: List<Task>,activeTasks: List<Task>, repo: Repository, refreshTasks: () -> Unit) {
    Pool(0, "Open", Color(0x666650a4), openTasks, false, repo, refreshTasks)
    Pool(0, "Active", Color(0x666790a4), activeTasks, false, repo, refreshTasks)
}

@Composable
fun TeamNavigationBar(selectedTeam: CTODepartment, onTeamSelected: (CTODepartment) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeamNavigationButton(
            team = CTODepartment.DEPARTMENT_1,
            isSelected = selectedTeam == CTODepartment.DEPARTMENT_1,
            onTeamSelected = onTeamSelected
        )
        TeamNavigationButton(
            team = CTODepartment.DEPARTMENT_2,
            isSelected = selectedTeam == CTODepartment.DEPARTMENT_2,
            onTeamSelected = onTeamSelected
        )
        TeamNavigationButton(
            team = CTODepartment.DEPARTMENT_3,
            isSelected = selectedTeam == CTODepartment.DEPARTMENT_3,
            onTeamSelected = onTeamSelected
        )
    }
}

@Composable
fun TeamNavigationButton(
    team: CTODepartment,
    isSelected: Boolean,
    onTeamSelected: (CTODepartment) -> Unit
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
<<<<<<< HEAD
        items(departments) { department ->
            DepartmentNavigationButton(
                department = department,
                isSelected = selectedDepartmentId == department.id,
                onDepartmentSelected = onDepartmentSelected
=======
        Button(
            onClick = { onTeamSelected(team) },
            modifier = Modifier.padding(top = 15.dp, start = 5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(
                text = when (team) {
                    CTODepartment.DEPARTMENT_1 -> "Depr 1"
                    CTODepartment.DEPARTMENT_2 -> "Depr 2"
                    CTODepartment.DEPARTMENT_3 -> "Depr 3"
                }
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
            )
        }
    }
}
<<<<<<< HEAD

@Composable
fun DepartmentNavigationButton(
    department: Department,
    isSelected: Boolean,
    onDepartmentSelected: (Int) -> Unit
) {
    Button(
        onClick = {
            onDepartmentSelected(department.id)
        },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(text = department.name)
    }
}
=======
>>>>>>> 19e37bacbc888d18c3c1189f6c22446676797680
