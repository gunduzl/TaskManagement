package com.example.taskmanager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Notification(val title: String, val message: String, val time: String)

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
            .border(2.dp, Color(0xFFD0BCFF), shape = RoundedCornerShape(10.dp))
            .clickable { },
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp) // Adjust inner padding here
        ){
            Text(
                text = notification.title,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = notification.message,
                fontSize = 16.sp,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = " ${notification.time}",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Right
                )
            }
        }

    }
}
