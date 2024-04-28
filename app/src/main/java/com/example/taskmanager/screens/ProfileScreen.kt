package com.example.taskmanager.screens

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.taskmanager.profileComponents.MyTasks


@Composable
fun ProfileScreen()

    { val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        LazyColumn {
            item {
                Button(onClick = {setShowNotification(true) }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Icon
                    ProfileIconn(icon = Icons.Default.Person)

                    Spacer(modifier = Modifier.width(25.dp))

                    // Manager Details
                    Column {
                        Text(
                            text = "Staff Name",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Department Name",
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
                        .fillMaxWidth() // Fill the maximum width available
                        .wrapContentHeight() // Wrap the height based on content
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Add padding for spacing
                        .offset(x = 65.dp, y = 30.dp) // Adjust the position on the screen
                ) {
                    // Manager Points
                    Surface(
                        modifier = Modifier
                            .size(180.dp, 50.dp),
                        color = MaterialTheme.colorScheme.primary, // Set your desired background color here
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star, // Example icon, replace with appropriate icon
                                contentDescription = "Points Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "Points:9",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                modifier = Modifier
                                    .offset(x = 8.dp , y = 0.dp) // Occupy maximum available width

                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Icon(
                                imageVector = Icons.Default.Star, // Example icon, replace with appropriate icon
                                contentDescription = "Points Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            item {
                //

                Spacer(modifier = Modifier.height(35.dp))
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MyTasks()
        }
    }

        // Display the NotificationScreen when showNotification is true
        if (showNotification) {
            NotificationScreen(onClose = { setShowNotification(false) })
        }
}

@Composable
fun ProfileIconn(icon: ImageVector) {
    Icon(
        imageVector = icon,
        contentDescription = "Profile Icon",
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
    )
}