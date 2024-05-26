package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import com.example.taskmanager.systems.EvaluationSystem
import kotlinx.coroutines.launch

@Composable
fun ManagerHomeScreen(repo: Repository, managerId: Int) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var taskDifficulty by remember { mutableStateOf(TaskDifficulty.LOW) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var totalTask = remember { mutableStateOf(0) }

    var openTasks by remember { mutableStateOf(emptyList<Task>()) }
    var activeTasks by remember { mutableStateOf(emptyList<Task>()) }

    fun refreshTasks() {
        coroutineScope.launch {
            //val totalTask = repo.getTasksByStatus(TaskStatus.OPEN).size + repo.getTasksByStatus(TaskStatus.ACTIVE).size
            val manager = repo.getManagerById(managerId)
            if (manager != null) {
                openTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN, manager.departmentId)
                activeTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE, manager.departmentId)
            }
        }
    }

    LaunchedEffect(managerId) {
        refreshTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(180.dp)
        ) {
            Button(onClick = { setShowNotification(true) }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
        }

        Row(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "My Department",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 20.dp, start = 40.dp)
            )

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

        // Pools
        Pool(managerId, "Open", Color(0x666650a4), openTasks, false, repo, ::refreshTasks)
        Pool(managerId, "Active", Color(0x666790a4), activeTasks, false, repo, ::refreshTasks)

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
                            label = {
                                Text(
                                    "Enter Task Description:",
                                    fontWeight = FontWeight.Bold
                                )
                            },
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
                            if (taskName.isBlank() || taskDescription.isBlank() || taskDueDate.isBlank()) {
                                showErrorDialog = true
                            } else {
                                coroutineScope.launch {
                                    val manager = repo.getManagerById(managerId)
                                    if (manager != null) {
                                        val task = Task(
                                            //id = totalTask.value + 1, // Replace with your task ID generation logic
                                            id = repo.getTasksByStatus(TaskStatus.OPEN).size + repo.getTasksByStatus(TaskStatus.ACTIVE).size + 1,
                                            title = taskName,
                                            description = taskDescription,
                                            status = TaskStatus.OPEN,
                                            difficulty = taskDifficulty,
                                            isHelp = HelpType.Default,
                                            deadline = taskDueDate,
                                            taskPoint = EvaluationSystem().evaluateTaskPoint(taskDifficulty),
                                            departmentId = manager.departmentId
                                        )
                                        repo.insertTask(task)
                                        refreshTasks()
                                    }
                                }
                                showAddDialog = false
                            }
                        }
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
                            dropdownExpanded = false
                        }
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
                text = { Text("Please fill out all fields before adding the task.") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) }, repo = repo, employeeId = managerId)
    }
}