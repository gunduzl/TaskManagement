package com.example.taskmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.R
import com.example.taskmanager.profileComponents.MyTasks
import com.example.taskmanager.profileComponents.MyTeam
import com.example.taskmanager.profileComponents.out.Department
import com.example.taskmanager.profileComponents.out.Employee
import com.example.taskmanager.profileComponents.out.Notification
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Role
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen(repo: Repository, employeeId: Int, navController: NavController) {
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    var employee by remember { mutableStateOf<Employee?>(null) }
    var employeeRole by remember { mutableStateOf<Role?>(null) }
    var employeeDepartment by remember { mutableStateOf<Department?>(null) }
    var employeePoint by remember { mutableStateOf<Int?>(null) }
    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }

    suspend fun refreshProfileScreen() {
        employee = repo.getEmployeeById(employeeId)
        employeeRole = employee?.role
        employeeDepartment = repo.getDepartmentsFromEmployeeID(employeeId)
        employeePoint = repo.getPointFromEmployeeID(employeeId)
        notifications = repo.getNotificationsById(employeeId)
    }

    suspend fun refreshPagePeriodically() {
        while (true) {
            refreshProfileScreen()
            delay(1000)
        }
    }

    LaunchedEffect(employeeId) {
        withContext(Dispatchers.IO) {
            refreshPagePeriodically()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        LazyColumn {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(175.dp)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = darkBackground
                        ),
                        onClick = { setShowNotification(true) }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = darkBackground
                        ),
                        onClick = {
                            // Navigate back to the login screen
                            navController.navigate("/first_screen") {
                                popUpTo("/app-navigation") { inclusive = true }
                            }
                        }) {
                        Text("Logout")
                    }
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {

                    Image(
                        painter = painterResource(id = R.drawable.man),
                        contentDescription = "App Icon",
                        modifier = Modifier.height(100.dp)
                    )

                    Spacer(modifier = Modifier.width(25.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = employee?.name ?: "Loading...",
                            style = MaterialTheme.typography.headlineLarge,
                            color = darkBackground,
                            fontStyle = FontStyle.Italic,
                            fontSize = 35.sp
                        )
                        Text(
                            text = employeeDepartment?.name ?: "Loading...",
                            style = MaterialTheme.typography.headlineMedium,
                            color = darkBackground,
                            fontStyle = FontStyle.Italic,
                            fontSize = 23.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
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
                    Surface(
                        modifier = Modifier
                            .size(180.dp, 50.dp)
                            .offset(y = -20.dp),
                        color = lightpurple,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Points Icon",
                                modifier = Modifier.size(21.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                text = "Points: ${employeePoint ?: "Loading..."}",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.DarkGray,
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
                .background(lightpurple, shape = RoundedCornerShape(16.dp))
        ) {
            val role = employeeRole
            if (role != null) {
                when (role) {
                    Role.MANAGER -> MyTeam(repo = repo, managerId = employeeId)
                    Role.STAFF -> MyTasks(repo = repo, staffId = employeeId)
                    else -> Unit
                }
            }
        }
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) }, repo = repo, employeeId = employeeId)
    }
}
