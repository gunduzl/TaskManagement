package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskmanager.components.pool.Pool
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskStatus
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.light
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(repo: Repository, staffId: Int) {
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var openTasks by remember { mutableStateOf(emptyList<Task>()) }
    var activeTasks by remember { mutableStateOf(emptyList<Task>()) }

    fun refreshTasks() {
        coroutineScope.launch {
            val department = repo.getDepartmentsFromEmployeeID(staffId)
            var depId = 0
            if(department != null){
                 depId = department.id
            }

            openTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN,depId)
            activeTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE,depId)
        }
    }

    LaunchedEffect(staffId) {
        refreshTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 10.dp)
            .background(light)

    ) {
        // Button for Notifications
        Button( colors = ButtonDefaults.buttonColors(containerColor = darkBackground),
            onClick = { setShowNotification(true) }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }

        // Pools
        Pool(staffId, "Open", Color.White, openTasks, true, repo, ::refreshTasks)
        Pool(staffId, "Active", darkBackground, activeTasks, true, repo, ::refreshTasks)
    }

    // Display the NotificationScreen when showNotification is true
    if (showNotification) {
        //NotificationScreen(onClose = { setShowNotification(false) },repo = repo, employeeId = staffId)
    }
}
