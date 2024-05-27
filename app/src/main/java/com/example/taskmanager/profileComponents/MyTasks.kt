package com.example.taskmanager.profileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.HelpType
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskStatus
import com.example.taskmanager.profileComponents.out.TaskWithStaff
import com.example.taskmanager.systems.EvaluationSystem
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.gray
import com.example.taskmanager.ui.theme.lightgray
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.launch


@Composable
fun MyTasks(repo: Repository, staffId: Int) {
    val tasks = remember { mutableStateOf<List<TaskWithStaff>>(emptyList()) }

    LaunchedEffect(staffId) {
        tasks.value = repo.getTaskFromStaff(staffId)
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Tasks",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp)) // Add spacer to create space before LazyColumn
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Occupy half of the available vertical space
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tasks.value) { taskWithStaff ->
                        TaskItem(taskWithStaff.task, taskWithStaff.staff.map { it.name })
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, staffNames: List<String>) {
    val statusColor = when (task.status) {
        TaskStatus.CLOSED -> Color.White
        TaskStatus.ACTIVE -> darkBackground
        else -> Color.Gray
    }

    val icon = when (task.status) {
        TaskStatus.CLOSED -> Icons.Default.Check
        TaskStatus.ACTIVE -> Icons.Default.Info
        else -> null
    }

    val showDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = statusColor, shape = RoundedCornerShape(15.dp))
            .padding(10.dp)
            .clickable { showDialog.value = true }, // Show dialog when clicked
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color= lightgray
        )
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "Icon",
                tint = if (task.status == TaskStatus.CLOSED) darkBackground else lightgray,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        if (task.status == TaskStatus.CLOSED) {
            Text(
                text = task.status.toString(),
                fontStyle = FontStyle.Italic,
                color = darkBackground,
                modifier = Modifier.padding(end = 8.dp)
            )
        } else {
            if (staffNames.isNotEmpty()) {
                Row {
                    /*
                    Text(
                        text = "Assigned to ${staffNames.joinToString()}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp, end = 8.dp)
                    )
                    */

                    Button( colors = ButtonDefaults.buttonColors(containerColor = lightgray),
                        onClick = {
                            coroutineScope.launch {
                                task.status = TaskStatus.CLOSED
                                val staffs = task.owners
                                val managerId = staffs[0].departmentManagerId
                                for (staff in staffs) {
                                    EvaluationSystem().evaluatePointFromTask(staff, task)

                                }
                                val manager = Repository().getManagerById(managerId)
                                if(manager != null){
                                    EvaluationSystem().evaluateManagerPoint(manager)
                                }

                                showDialog.value= true
                            }
                        },
                    ) {
                        Text("Submit",color= darkBackground)
                    }

                }


            }
        }

    }

    // Alert Dialog
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Task Details", fontWeight = FontWeight.Bold,color= lightpurple) },
            text = {
                Column {
                    Text(text = "Task Name: ${task.title}",color= lightgray)
                    Text(text = "Status: ${task.status}",color= lightgray)
                    staffNames.forEach { staffName ->
                        Text(text = "Assigned to: $staffName",color= lightgray)
                    }
                }
            },
            confirmButton = {
                if (task.status == TaskStatus.ACTIVE) {
                    Button( colors = ButtonDefaults.buttonColors(
                        containerColor = lightpurple
                    ),
                        onClick = {
                            coroutineScope.launch {
                                task.isHelp = HelpType.Requested
                                showDialog.value = false
                            }

                        }
                    ) {
                        Text("Ask Help",color= gray)
                    }
                }
            },
            dismissButton = {
                Button( colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = { showDialog.value = false },
                ) {
                    Text("Cancel",color= gray)
                }
            }, containerColor = darkBackground
        )
    }
}