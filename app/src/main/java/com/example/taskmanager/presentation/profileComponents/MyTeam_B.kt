package com.example.taskmanager.presentation.profileComponents


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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.presentation.ui.theme.customGreen
import com.example.taskmanager.presentation.ui.theme.customPurple

@Composable
fun MyTeam_B() {
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
                val staffList = listOf(
                    Staff("Gunduz", "Busy", "Task A", Color.Green),
                    Staff("Ali", "Available", "Task A", Color.Red),
                    Staff("Alper", "Busy", "Task B", Color.Green),
                    Staff("Elif", "Available", "Task C", Color.Red),
                    Staff("Ayşegül", "Available", "Task A", Color.Red),
                    Staff("Sadık", "Busy", "Task D", Color.Green),
                    Staff("Eren", "Available", "Task B", Color.Red)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(staffList) { staff ->
                        StafffItem(staff)
                    }
                }
            }
        }
    }
}

data class Stafff(val name: String, val status: String, val task: String?, val statusColor: Color)

@Composable
fun StafffItem(staff: Staff) {
    val statusColor = if (staff.status == "Busy") customPurple else customGreen

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
        if (staff.status == "Available") {
            Text(
                text = staff.status,
                fontStyle = FontStyle.Italic,
                color = Color.Green
            )
        } else {
            staff.task?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Staff Details", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(text = "Name: ${staff.name}")
                    Text(text = "Status: ${staff.status}")
                    staff.task?.let { Text(text = "Task: $it") }
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
