package com.example.taskmanager.screens

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.profileComponents.out.Department
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.profileComponents.out.StaffStatus
import com.example.taskmanager.ui.theme.customGreen
import com.example.taskmanager.ui.theme.customPurple
import kotlinx.coroutines.launch

@Composable
fun CTOProfile(repo: Repository, ctoId: Int, navController: NavController) {
    val (selectedDepartmentId, setSelectedDepartmentId) = remember { mutableStateOf<Int?>(null) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val ctoName = remember { mutableStateOf("Loading...") }
    val departments = remember { mutableStateOf<List<Department>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(ctoId) {
        coroutineScope.launch {
            val ctoWithDepartments = repo.getCTOWithDepartments(ctoId)
            if (ctoWithDepartments.isNotEmpty()) {
                val ctoDepartments = ctoWithDepartments.first()
                ctoName.value = ctoDepartments.cto.name
                departments.value = ctoDepartments.departments
                if (departments.value.isNotEmpty()) {
                    setSelectedDepartmentId(departments.value.first().id)
                }
            }
        }
    }

    Row(modifier = Modifier.padding(top = 10.dp, start = 280.dp)) {
        Button(onClick = {
            // Navigate back to the login screen
            navController.navigate("/first_screen") {
                popUpTo("/app-navigation") { inclusive = true }
            }
        }) {
            Text("Logout")
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
                Button(onClick = { setShowNotification(true) }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProfileIcon(icon = Icons.Default.Person)
                    Spacer(modifier = Modifier.width(25.dp))
                    Column {
                        Text(
                            text = ctoName,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "CTO",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Team",
                fontSize = 25.sp,
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
    val statusColor = if (staff.staffStatus == StaffStatus.BUSY) customPurple else customGreen

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
                color = Color.Green
            )
        } else {
            Text(
                text = "Busy",
                fontStyle = FontStyle.Italic,
                color = Color.Red
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Staff Details", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(text = "Name: ${staff.name}")
                    Text(text = "Email: ${staff.email}")
                    Text(text = "Department ID: ${staff.departmentId}")
                    Text(text = "Status: ${staff.staffStatus}")
                }
            },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Close")
                }
            }
        )
    }
}
