package com.example.taskmanager.profileComponents


import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTeam_C() {
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
                    Staff("Gunduz", "Available", null, Color.Green),
                    Staff("Ali", "Busy", "Task A", Color.Red),
                    Staff("Alper", "Available", null, Color.Green),
                    Staff("Elif", "Busy", "Task C", Color.Red),
                    Staff("Ayşegül", "Busy", "Task A", Color.Red),
                    Staff("Sadık", "Available", null, Color.Green),
                    Staff("Eren", "Busy", "Task B", Color.Red)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(staffList) { staff ->
                        StaffffItem(staff)
                    }
                }
            }
        }
    }
}

data class Staff(val name: String, val status: String, val task: String?, var statusColor: Color)

@Composable
fun StaffffItem(staff: Staff) {
    val statusColor = if (staff.status == "Busy") Color.Red else Color.Green

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = statusColor.copy(alpha = 0.2f), shape = RoundedCornerShape(15.dp))
            .padding(10.dp),
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
                color = statusColor
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
}