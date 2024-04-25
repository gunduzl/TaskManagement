package com.example.taskmanager.profileComponents

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTasks() {
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
                val tasks = listOf(
                    Task("Task A", "Done", null),
                    Task("Task B", "Processing", null),
                    Task("Task C", "Processing", null)
                ).sortedBy { it.status != "Processing" }
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
}

data class Task(val name: String, val status: String, val staffName: String?)

@Composable
fun TaskItem(task: Task) {
    val statusColor = when (task.status) {
        "Done" -> Color.Green
        "Processing" -> Color.Blue
        else -> Color.Gray
    }

    val icon = when (task.status) {
        "Done" -> Icons.Default.Check
        "Processing" -> Icons.Default.Refresh
        else -> null
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = statusColor.copy(alpha = 0.2f), shape = RoundedCornerShape(15.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (task.status == "Done") {
            Text(
                text = task.status,
                fontStyle = FontStyle.Italic,
                color = statusColor,
                modifier = Modifier.padding(end = 8.dp)
            )
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Done",
                    tint = statusColor
                )
            }
        } else {
            if (task.staffName != null) {
                Text(
                    text = "Assigned to ${task.staffName}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 10.dp, end = 8.dp)
                )
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Processing",
                        tint = statusColor
                    )
                }
            }
        }
    }
}