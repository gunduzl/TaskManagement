package com.example.taskmanager.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.presentation.components.pool.Pool
import com.example.taskmanager.presentation.viewmodel.pages.HomeViewModel

@Composable
fun HomeScreen(staffId: Int) {
    val viewModel: HomeViewModel = viewModel()
    val openTasks by viewModel.openTasks.collectAsState()
    val activeTasks by viewModel.activeTasks.collectAsState()
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }

    LaunchedEffect(staffId) {
        viewModel.loadTasksForStaff(staffId)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 10.dp)
    ) {
        Button(onClick = { setShowNotification(true) }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }
        Pool("Open", Color(0x666650a4), isStaff = true)
        Pool("Active", Color(0x666790a4), isStaff = true)    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) })
    }
}
