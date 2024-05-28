package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontStyle
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
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightgray
import com.example.taskmanager.ui.theme.lightpurple
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
    var showHelpAbout by remember { mutableStateOf(false) }

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

        ) {
            Button(  colors = ButtonDefaults.buttonColors(
                containerColor = darkBackground
            ),
                onClick = { setShowNotification(true) }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
            Spacer(Modifier.width(10.dp))
            Button( colors = ButtonDefaults.buttonColors(containerColor = darkBackground),
                onClick = { showHelpAbout = true }) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
            }
        }

        Row(
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "My Department",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 20.dp, start = 40.dp),
                fontStyle = FontStyle.Italic
            )

            Button( colors = ButtonDefaults.buttonColors(
                containerColor = darkBackground
            ),
                onClick = {
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
        Pool(managerId, "Open", Color.White , openTasks, false, repo, ::refreshTasks)  // open task 3ün çerçevesi
        Pool(managerId, "Active", darkBackground, activeTasks, false, repo, ::refreshTasks)

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add New Task", fontWeight = FontWeight.Bold,color= lightgray) },
                text = {
                    Column(
                        modifier = Modifier
                            .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                    ) {
                        TextField(
                            value = taskName,
                            onValueChange = { taskName = it },
                            label = { Text("Enter Task Name:", fontWeight = FontWeight.Bold,color= lightgray) },
                            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                        )

                        TextField(
                            value = taskDescription,
                            onValueChange = { taskDescription = it },
                            label = {
                                Text(
                                    "Enter Task Description:",
                                    fontWeight = FontWeight.Bold,
                                    color= lightgray
                                )
                            },
                            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                            modifier = Modifier.height(100.dp)
                        )

                        TextField(
                            value = taskDueDate,
                            onValueChange = { taskDueDate = it },
                            label = { Text("Enter Due Date:", fontWeight = FontWeight.Bold,color= lightgray) },
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
                                modifier = Modifier.padding(end = 8.dp),
                                color= lightgray
                            )
                            Text(
                                text = taskDifficulty.name,
                                color= lightgray
                            )
                        }

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier .background(lightpurple)
                        ) {
                            DropdownMenuItem(text = { Text("Easy") }, onClick = {
                                taskDifficulty = TaskDifficulty.LOW
                                dropdownExpanded = false
                            })
                            DropdownMenuItem(text = { Text("Medium") }
                                , onClick = {
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
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = lightpurple
                    ),
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
                                            creationTime = System.currentTimeMillis(),
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
                        Text("Add" ,color= darkBackground)
                    }
                },
                dismissButton = {
                    Button(colors = ButtonDefaults.buttonColors(
                        containerColor = lightpurple
                    ),
                        onClick = {
                            showAddDialog = false
                            taskName = ""
                            taskDescription = ""
                            taskDueDate = ""
                            dropdownExpanded = false
                        }
                    ) {
                        Text("Cancel",color= darkBackground)
                    }
                }
                , containerColor = darkBackground // Custom background color
            )
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error", fontWeight = FontWeight.Bold,color= darkBackground) },
                text = { Text("Please fill out all fields before adding the task.",color= darkBackground) },
                confirmButton = {
                    Button( colors = ButtonDefaults.buttonColors(
                        containerColor = darkBackground),
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("OK",color= lightgray)
                    }
                }
                , containerColor = lightpurple
            )
        }
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) }, repo = repo, employeeId = managerId)
    }

    if (showHelpAbout) {
        AlertDialog(
            onDismissRequest = { showHelpAbout = false },
            title = { Row{
                Text("How to use Task Manager", color = lightgray ,fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle(10))
                Spacer(modifier = Modifier.width(30.dp))
            } },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row { Text("- + (Add) button for creating new task.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Click on tasks in order to see the detailed information about it.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Press take task to take the task and its status becomes ACTIVE.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Notifications can be viewed by clicking its button.",color= lightpurple) } // This should be dynamically calculated
                    Row { Text("- For each task staff submit they earned point and leaderboard is updated.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- In each month all of the staff points are set to 0", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Recommendations are send to the managers when month ends.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- From staffs profile staff can ask help and managers can confirm or reject them from their homepage's active task pool.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- For further questions reach out taskmanager@gmail.com.", fontWeight = FontWeight.Bold,color= lightpurple) }
                }
            },
            confirmButton = {


            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = { showHelpAbout = false }) { Text("Close" ,color= darkBackground) }
            },  containerColor = darkBackground
        )
    }

}