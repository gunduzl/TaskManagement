package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.Notification
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightgray
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.launch

@Composable
fun NotificationScreen(repo: Repository, employeeId: Int, onClose: () -> Unit) {
    val alertDialogState = remember { mutableStateOf(true) }
    val notificationsList = remember { mutableStateOf<List<Notification>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(alertDialogState.value) {
        if (alertDialogState.value) {
            coroutineScope.launch {
                val notifications = repo.getNotificationsById(employeeId)
                notificationsList.value = notifications
            }
        }
    }

    if (alertDialogState.value) {
        AlertDialog(
            onDismissRequest = { alertDialogState.value = false },
            title = {
                Text(
                    text = "Notifications",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = lightgray
                )
            },
            text = {
                Notifications(notificationsList.value)
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lightpurple
                    ),
                    onClick = {
                        alertDialogState.value = false
                        onClose()
                    }
                ) {
                    Text("Close", color = darkBackground)
                }
            }
            , containerColor = darkBackground // Custom background color
        )
    }
}

@Composable
fun Notifications(notifications: List<Notification>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(notifications) { notification ->
            NotificationItem(notification)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 8.dp)
            .border(2.dp, lightgray, shape = RoundedCornerShape(10.dp))
            .background(lightpurple,shape = RoundedCornerShape(10.dp))
            .clickable { },
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ){
            Text(
                text = notification.title,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = notification.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = " ${notification.date}",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}