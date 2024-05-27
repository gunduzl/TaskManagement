package com.example.taskmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.R
import com.example.taskmanager.components.Leaderboard
import com.example.taskmanager.profileComponents.out.Notification
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun LeaderBoardScreen(repo: Repository, employeeId: Int) {
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var yourLeaderboardList by remember { mutableStateOf<List<Staff>>(emptyList()) }
    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }

    suspend fun refreshLeaderboardScreen() {
        yourLeaderboardList = repo.getStaffList()
        notifications = repo.getNotificationsById(employeeId)
    }

    suspend fun refreshPagePeriodically() {
        while (true) {
            refreshLeaderboardScreen()
            delay(30000)
        }
    }

    LaunchedEffect(employeeId) {
        withContext(Dispatchers.IO) {
            refreshPagePeriodically()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightpurple)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = darkBackground),
                onClick = { setShowNotification(true) }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(40.dp))

            Text(
                text = "Leaderboard",
                color = darkBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Image(
                painter = painterResource(id = R.drawable.zzzzz),
                contentDescription = "App Icon",
                modifier = Modifier.height(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (showNotification) {
            NotificationScreen(onClose = { setShowNotification(false) }, repo = repo, employeeId = employeeId)
        } else {
            Leaderboard(yourLeaderboardList)
        }
    }
}
