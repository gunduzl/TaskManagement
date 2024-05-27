package com.example.taskmanager.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.R
import com.example.taskmanager.profileComponents.out.Department
import com.example.taskmanager.profileComponents.out.Notification
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.profileComponents.out.StaffStatus
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightgray
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CTOProfile(repo: Repository, ctoId: Int, navController: NavController) {
    val (selectedDepartmentId, setSelectedDepartmentId) = remember { mutableStateOf<Int?>(null) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val ctoName = remember { mutableStateOf("Loading...") }
    val departments = remember { mutableStateOf<List<Department>>(emptyList()) }
    val notifications = remember { mutableStateOf<List<Notification>>(emptyList()) }

    suspend fun refreshProfileScreen() {
        val ctoWithDepartments = repo.getCTOWithDepartments(ctoId)
        if (ctoWithDepartments.isNotEmpty()) {
            val ctoDepartments = ctoWithDepartments.first()
            ctoName.value = ctoDepartments.cto.name
            departments.value = ctoDepartments.departments
            if (departments.value.isNotEmpty()) {
                setSelectedDepartmentId(departments.value.first().id)
            }
            notifications.value = repo.getNotificationsById(ctoId)
        }
    }


    LaunchedEffect(ctoId) {
        withContext(Dispatchers.IO) {
            refreshProfileScreen()
        }
    }

    Row(modifier = Modifier.padding(top = 10.dp, start = 280.dp)) {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = darkBackground
        ), onClick = {
            // Navigate back to the login screen
            navController.navigate("/first_screen") {
                popUpTo("/app-navigation") { inclusive = true }
            }
        }) {
            Text("Logout", color = lightgray)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        CTOProfileHeader(ctoName.value, setShowNotification)
        DepartmentNavigationBar(departments.value, selectedDepartmentId, setSelectedDepartmentId)
        selectedDepartmentId?.let { departmentId ->
            MyTeam(repo, departmentId)
        }
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) }, repo = repo, employeeId = ctoId)
    }
}

@Composable
fun CTOProfileHeader(ctoName: String, setShowNotification: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp)
    ) {
        LazyColumn {
            item {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = darkBackground
                ),
                    onClick = { setShowNotification(true) }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.z),
                        contentDescription = "App Icon",
                        modifier = Modifier.height(100.dp)
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                    Column {
                        Text(
                            text = ctoName,
                            style = MaterialTheme.typography.headlineLarge,
                            color = darkBackground
                        )
                        Text(
                            text = "CTO",
                            style = MaterialTheme.typography.headlineMedium,
                            color = darkBackground
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileIcon(icon: ImageVector) {
    Icon(
        imageVector = icon,
        contentDescription = "Profile Icon",
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
    )
}

@Composable
fun MyTeam(repo: Repository, departmentId: Int) {
    val staffList = remember { mutableStateOf<List<Staff>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(departmentId) {
        coroutineScope.launch {
            val departmentWithDetails = repo.getDepartmentWithDetails(departmentId)
            staffList.value = departmentWithDetails.firstOrNull()?.staff ?: emptyList()
        }
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .background(lightpurple, shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Team", color = darkBackground,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp)) // Add spacer to create space before LazyColumn
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Occupy half of the available vertical space
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(staffList.value) { staff ->
                        StaffItem(staff)
                    }
                }
            }
        }
    }
}

@Composable
fun StaffItem(staff: Staff) {
    val statusColor = if (staff.staffStatus == StaffStatus.BUSY) darkBackground else Color.White

    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = statusColor, shape = RoundedCornerShape(15.dp))
            .padding(10.dp)
            .clickable { showDialog.value = true },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = staff.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (staff.staffStatus == StaffStatus.AVAILABLE) {
            Text(
                text = "Available",
                fontStyle = FontStyle.Italic,
                color = darkBackground
            )
        } else {
            Text(
                text = "Busy",
                fontStyle = FontStyle.Italic,
                color = lightpurple
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Staff Details", fontWeight = FontWeight.Bold, color = lightgray, fontStyle = FontStyle.Italic) },
            text = {
                Column {
                    Text(text = "Name: ${staff.name}", color = lightgray)
                    Text(text = "Email: ${staff.email}", color = lightgray)
                    Text(text = "Department ID: ${staff.departmentId}", color = lightgray)
                    Text(text = "Status: ${staff.staffStatus}", color = lightgray)
                }
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = { showDialog.value = false }) {
                    Text("Close", color = darkBackground)
                }
            }, containerColor = darkBackground
        )
    }
}
