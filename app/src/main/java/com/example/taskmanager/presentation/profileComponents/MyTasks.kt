package com.example.taskmanager.presentation.profileComponents

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.data.entities.Task
import com.example.taskmanager.presentation.ui.theme.customGreen
import com.example.taskmanager.presentation.ui.theme.customPurple
import com.example.taskmanager.presentation.viewmodel.MyTasksViewModel

@Composable
fun MyTasks(userId: Int, userRole: String) {
    val viewModel: MyTasksViewModel = viewModel()
    val tasks by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTasksForUser(userId, userRole)
    }

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
                items(tasks) { task ->
                    TaskItem(task)
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    val statusColor = when (task.status) {
        "Done" -> customGreen
        "Processing" -> customPurple
        else -> Color.Gray
    }

    val icon = when (task.status) {
        "Done" -> Icons.Default.Check
        "Processing" -> Icons.Default.Info
        else -> null
    }

    val showDialog = remember { mutableStateOf(false) }

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
            fontSize = 20.sp
        )
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "Icon",
                tint = if (task.status == "Done") Color.Green else Color.Blue,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        if (task.status == "Done") {
            Text(
                text = task.status,
                fontStyle = FontStyle.Italic,
                color = Color.Green,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }

    // Alert Dialog
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Task Details", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(text = "Task Name: ${task.title}")
                    Text(text = "Description: ${task.description}")
                    Text(text = "Status: ${task.status}")
                }
            },
            confirmButton = {
                if (task.status == "Processing") {
                    Button(onClick = { /* Perform action for asking help */ }) {
                        Text("Ask Help")
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
