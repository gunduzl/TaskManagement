package com.example.taskmanager.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskmanager.components.pool.Pool

@Composable
fun HomeScreen() {

    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 30.dp, start = 20.dp, end = 10.dp)
    ) {
        // Button for Notifications
        Button(onClick = { setShowNotification(true) }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }

        // Pools
        Pool("Open", Color(0x666650a4), "Develop and admin panel")
        Pool("Active", Color(0x666790a4), "Develop and admin panel")
    }

    // Display the NotificationScreen when showNotification is true
    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) })
    }
}
