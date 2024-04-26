package com.example.taskmanager.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.components.Notification
import com.example.taskmanager.components.Notifications

@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {

            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null )
            }
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Notifications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

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
    }
}
