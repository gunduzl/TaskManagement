package com.example.taskmanager.profileComponents

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.profileComponents.out.StaffStatus
import com.example.taskmanager.ui.theme.customGreen
import com.example.taskmanager.ui.theme.customPurple
import kotlinx.coroutines.launch

@Composable
fun MyTeam(repo: Repository, managerId: Int) {
    val staffList = remember { mutableStateOf<List<Staff>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(managerId) {
        coroutineScope.launch {
            val managerWithStaff = repo.getManagerWithStaff(managerId)
            staffList.value = managerWithStaff.firstOrNull()?.staff ?: emptyList()
            println("Manager ID: $managerId, Staff List: ${staffList.value}")
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
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
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
