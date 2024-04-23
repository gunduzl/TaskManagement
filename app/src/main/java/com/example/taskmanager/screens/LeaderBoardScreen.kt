package com.example.taskmanager.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskmanager.components.Leaderboard
import com.example.taskmanager.components.LeaderboardEntry
import com.example.taskmanager.components.Staff


@Composable
fun LeaderBoardScreen() {
        /*
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Leaderboard Screen",
                fontFamily = FontFamily.Serif,
                fontSize = 22.sp
            )
        }
        */
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.9f)
            .padding(top = 10.dp, start = 20.dp, end = 10.dp)
    ) {
        Button(onClick = {  }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null )
        }

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

