package com.example.taskmanager.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.presentation.profileComponents.MyTasks
import com.example.taskmanager.presentation.profileComponents.MyTeam
import com.example.taskmanager.presentation.viewmodel.pages.ProfileViewModel


@Composable
fun ProfileScreen(userRole: String, userId: Int) {
    val profileViewModel: ProfileViewModel = viewModel()
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile(userRole, userId)
    }

    val userProfile by profileViewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        LazyColumn {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(187.dp)) {
                    Button(onClick = { setShowNotification(true) }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    Button(onClick = {
                        // Add your logout logic here
                    }) {
                        Text("Logout")
                    }
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Icon
                    ProfileIcon(icon = Icons.Default.Person)

                    Spacer(modifier = Modifier.width(25.dp))

                    // User Details
                    Column {
                        Text(
                            text = userProfile.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = userProfile.departmentName,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .offset(x = 65.dp, y = 30.dp)
                ) {
                    // Points
                    Surface(
                        modifier = Modifier.size(180.dp, 50.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Points Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "Points: ${userProfile.points}",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                modifier = Modifier.offset(x = 8.dp, y = 0.dp)
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Points Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(35.dp))
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (userRole.lowercase() == "staff") {
                MyTasks(userId, userRole)
            } else if (userRole.lowercase() == "manager") {
                MyTeam(userId)
            }
        }
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) })
    }
}
