package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmanager.components.pool.Pool
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskStatus
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.light
import com.example.taskmanager.ui.theme.lightgray
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(repo: Repository, staffId: Int) {
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var openTasks by remember { mutableStateOf(emptyList<Task>()) }
    var activeTasks by remember { mutableStateOf(emptyList<Task>()) }
    var showHelpAbout by remember { mutableStateOf(false) }

    fun refreshTasks() {
        coroutineScope.launch {
            val department = repo.getDepartmentsFromEmployeeID(staffId)
            var depId = 0
            if(department != null){
                 depId = department.id
            }

            openTasks = repo.getTasksByStatusAndDepartment(TaskStatus.OPEN,depId)
            activeTasks = repo.getTasksByStatusAndDepartment(TaskStatus.ACTIVE,depId)
        }
    }

    LaunchedEffect(staffId) {
        refreshTasks()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 10.dp)
            .background(light)

    ) {
        Row(){
            Button( colors = ButtonDefaults.buttonColors(containerColor = darkBackground),
                onClick = { setShowNotification(true) }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
            Spacer(Modifier.width(20.dp))
            Button( colors = ButtonDefaults.buttonColors(containerColor = darkBackground),
                onClick = { showHelpAbout = true }) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
            }
        }// Button for Notifications

        // Pools
        Pool(staffId, "Open", Color.White, openTasks, true, repo, ::refreshTasks)
        Pool(staffId, "Active", darkBackground, activeTasks, true, repo, ::refreshTasks)
    }

    // Display the NotificationScreen when showNotification is true
    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) },repo = repo, employeeId = staffId)
    }

    if (showHelpAbout) {
        AlertDialog(
            onDismissRequest = { showHelpAbout = false },
            title = { Row{
                Text("How to use Task Manager", color = lightgray ,fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle(10))
                Spacer(modifier = Modifier.width(30.dp))
            } },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row { Text("- + (Add) button for creating new task.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Click on tasks in order to see the detailed information about it.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Press take task to take the task and its status becomes ACTIVE.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Notifications can be viewed by clicking its button.",color= lightpurple) } // This should be dynamically calculated
                    Row { Text("- For each task staff submit they earned point and leaderboard is updated.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- In each month all of the staff points are set to 0", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- Recommendations are send to the managers when month ends.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- From staffs profile staff can ask help and managers can confirm or reject them from their homepage's active task pool.", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("- For further questions reach out taskmanager@gmail.com.", fontWeight = FontWeight.Bold,color= lightpurple) }
                }
            },
            confirmButton = {


            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = { showHelpAbout = false }) { Text("Close" ,color= darkBackground) }
            },  containerColor = darkBackground
        )
    }
}
