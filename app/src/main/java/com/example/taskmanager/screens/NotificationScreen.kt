package com.example.taskmanager.screens


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.taskmanager.components.Notification
import com.example.taskmanager.components.Notifications

@Composable
fun NotificationScreen(onClose: () -> Unit) {
    val alertDialogState = remember { mutableStateOf(true) } // Set alertDialogState to true by default


    AlertDialog(
        onDismissRequest = { alertDialogState.value = false },
        title = {
            Text(
                text = "Notifications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
                text = {

        val yourNotificationList: List<Notification> = listOf(
            Notification(title = "New Message", message = "You have a new message from Jane", time = "10:30 AM"),
            Notification(title = "Reminder", message = "Don't forget your meeting at 2 PM", time = "Yesterday"),
            Notification(title = "Task Assigned", message = "You have a new task assigned", time = "2 days ago"),
            Notification(title = "Weekly Report", message = "Submit your weekly report by Friday 5:00 PM", time = "Monday"),
            Notification(title = "Monthly Meeting", message = "Monthly team meeting at 10:00 AM", time = "Last Week"),
            Notification(title = "Reminder", message = "Don't forget your meeting at 2 PM", time = "Last Week"),
        // Add more notifications here
        )

        Notifications(yourNotificationList)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            alertDialogState.value = false
                            onClose()
                        }
                    ) {
                        Text("Close")
                    }
                }
            )
        }