package com.example.taskmanager.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.components.Leaderboard
import com.example.taskmanager.components.Staff
import com.example.taskmanager.profileComponents.out.Repository


@Composable
fun LeaderBoardScreen(repo: Repository,employeeId: Int) {
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { setShowNotification(true) }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Leaderboard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Display the NotificationScreen when showNotification is true
        if (showNotification) {
            NotificationScreen(onClose = { setShowNotification(false) },repo = repo, employeeId = employeeId)
        } else {

            val yourLeaderboardList: List<Staff> = listOf(
                Staff(name = "John Doe", score = 75),
                Staff(name = "Jane Smith", score = 90),
                Staff(name = "Alice Johnson", score = 85),
                Staff(name = "Bob Brown", score = 80)
                // Diğer girişler buraya eklenebilir
            )

            Leaderboard(yourLeaderboardList)

        }

    }

}