package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.components.pool.Pool
import com.example.taskmanager.profileComponents.out.HelpType
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskDifficulty
import com.example.taskmanager.profileComponents.out.TaskStatus
import kotlinx.coroutines.launch

enum class CTODepartment {
    DEPARTMENT_1,
    DEPARTMENT_2,
    DEPARTMENT_3
}

@Composable
fun CTOHomeScreen(repo: Repository) {
    var showAddDialog by remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var taskDifficulty by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val (selectedTeam, setSelectedTeam) = remember { mutableStateOf(CTODepartment.DEPARTMENT_1) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var department1Tasks by remember { mutableStateOf(emptyList<Task>()) }
    var department2Tasks by remember { mutableStateOf(emptyList<Task>()) }
    var department3Tasks by remember { mutableStateOf(emptyList<Task>()) }

    fun refreshTasks() {
        coroutineScope.launch {
            department1Tasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, 1)
            department2Tasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, 2)
            department3Tasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, 3)
        }
    }

    LaunchedEffect(Unit) {
        refreshTasks()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ) {
        Button(onClick = { setShowNotification(true) }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Task", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                ) {
                    TextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text("Enter Task Name:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    TextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Enter Task Description:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                        modifier = Modifier.height(100.dp)
                    )
                    TextField(
                        value = taskDueDate,
                        onValueChange = { taskDueDate = it },
                        label = { Text("Enter Due Date:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
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
                            text = taskDifficulty.takeIf { it.isNotBlank() } ?: "Select",
                        )
                    }
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier
                    ) {
                        DropdownMenuItem(text = { Text("Easy") }, onClick = {
                            taskDifficulty = "Easy"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Medium") }, onClick = {
                            taskDifficulty = "Medium"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Hard") }, onClick = {
                            taskDifficulty = "Hard"
                            dropdownExpanded = false
                        })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val task = Task(
                                id = 0, // Replace with your task ID generation logic
                                title = taskName,
                                description = taskDescription,
                                status = TaskStatus.OPEN,
                                difficulty = TaskDifficulty.valueOf(taskDifficulty.uppercase()),
                                isHelp = HelpType.Default,
                                deadline = taskDueDate,
                                departmentId = when (selectedTeam) {
                                    CTODepartment.DEPARTMENT_1 -> 1
                                    CTODepartment.DEPARTMENT_2 -> 2
                                    CTODepartment.DEPARTMENT_3 -> 3
                                }
                            )
                            repo.insertTask(task)
                            refreshTasks()
                        }
                        showAddDialog = false
                        taskName = ""
                        taskDescription = ""
                        taskDueDate = ""
                        taskDifficulty = ""
                        dropdownExpanded = false
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
                        taskDifficulty = ""
                        dropdownExpanded = false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 65.dp)
    ) {
        // Navigation bar
        TeamNavigationBar(selectedTeam, setSelectedTeam)
        // Display team details based on the selected team
        when (selectedTeam) {
            CTODepartment.DEPARTMENT_1 -> Department_1(department1Tasks, repo, ::refreshTasks)
            CTODepartment.DEPARTMENT_2 -> Department_2(department2Tasks, repo, ::refreshTasks)
            CTODepartment.DEPARTMENT_3 -> Department_3(department3Tasks, repo, ::refreshTasks)
        }
    }
}

@Composable
fun Department_1(tasks: List<Task>, repo: Repository, refreshTasks: () -> Unit) {
    Pool(0, "Open", Color(0x666650a4), tasks, false, repo, refreshTasks)
    Pool(0, "Active", Color(0x666790a4), tasks, false, repo, refreshTasks)
}

@Composable
fun Department_2(tasks: List<Task>, repo: Repository, refreshTasks: () -> Unit) {
    Pool(0, "Open", Color(0x666650a4), tasks, false, repo, refreshTasks)
    Pool(0, "Active", Color(0x666790a4), tasks, false, repo, refreshTasks)
}

@Composable
fun Department_3(tasks: List<Task>, repo: Repository, refreshTasks: () -> Unit) {
    Pool(0, "Open", Color(0x666650a4), tasks, false, repo, refreshTasks)
    Pool(0, "Active", Color(0x666790a4), tasks, false, repo, refreshTasks)
}

@Composable
fun TeamNavigationBar(selectedTeam: CTODepartment, onTeamSelected: (CTODepartment) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Navigation buttons for each team
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
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Button(
            onClick = { onTeamSelected(team) },
            modifier = Modifier.padding(top = 15.dp, start = 5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        ) {
            // Display team name as button text
            Text(
                text = when (team) {
                    CTODepartment.DEPARTMENT_1 -> "Depr 1"
                    CTODepartment.DEPARTMENT_2 -> "Depr 2"
                    CTODepartment.DEPARTMENT_3 -> "Depr 3"
                }
            )
        }
    }
}
