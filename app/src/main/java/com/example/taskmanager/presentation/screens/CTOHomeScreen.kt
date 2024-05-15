package com.example.taskmanager.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.presentation.components.pool.Pool


enum class Department {
    DEPARTMENT_1,
    DEPARTMENT_2,
    DEPARTMENT_3
}

@Composable
fun CTOHomeScreen(){
    var showAddDialog by remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var taskDifficulty by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val (selectedTeam, setSelectedTeam) = remember { mutableStateOf(Department.DEPARTMENT_1) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }


    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(start=20.dp, end=20.dp,top=10.dp)) {

        Button(onClick = { setShowNotification(true) }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }

        Button(onClick = { showAddDialog = true  }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null )
        }
    }

    if (showAddDialog ) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add New Task", fontWeight = FontWeight.Bold) },
            text = {
                Column (
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))

                    //.border(BorderStroke(width = 4.dp, color = Color.Black))


                ) {
                    TextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text("Enter Task Name:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )

                    TextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Enter Task Description:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                        modifier = Modifier.height(100.dp)

                    )

                    TextField(
                        value = taskDueDate,
                        onValueChange = { taskDueDate = it },
                        label = { Text("Enter Due Date:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top=20.dp)
                            .clickable { dropdownExpanded = true }) {
                        Text(
                            text = "Select Difficulty:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = taskDifficulty.takeIf { it.isNotBlank() } ?: "Select",

                            )
                    }
                    DropdownMenu(expanded = dropdownExpanded,
                        onDismissRequest = { /*TODO*/ },
                        modifier = Modifier) {

                        DropdownMenuItem(text = { Text("Easy")}, onClick = {
                            taskDifficulty = "Easy"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Medium")}, onClick = {
                            taskDifficulty = "Medium"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Hard")}, onClick = {
                            taskDifficulty = "hard"
                            dropdownExpanded = false
                        })
                    }

                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        showAddDialog = false
                        taskName = ""
                        taskDescription = ""
                        taskDueDate = ""
                        taskDifficulty = ""
                        dropdownExpanded = false
                    },
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showAddDialog = false
                        taskName = ""
                        taskDescription = ""
                        taskDueDate = ""
                        taskDifficulty = ""
                        dropdownExpanded = false

                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) })
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp, end = 10.dp, top =65.dp)) {
        // Navigation bar
        TeamNavigationBar(selectedTeam, setSelectedTeam)
        // Display team details based on the selected team
        when (selectedTeam) {
            Department.DEPARTMENT_1 -> Department_1()
            Department.DEPARTMENT_2 -> Department_2()
            Department.DEPARTMENT_3 -> Department_3()
        }
    }

}

@Composable
fun Department_3() {

    Pool("Open", Color(0x666650a4), "Implement the profile page",false)
    Pool("Active",  Color(0x666790a4),"Create a leaderboard interface",false)

}

@Composable
fun Department_2() {

    Pool("Open", Color(0x666650a4),"Prepare presentation to customers",false)
    Pool("Active",Color(0x666790a4),"Product development plan",false)
}

@Composable
fun Department_1() {

    Pool("Open", Color(0x666650a4), "Develop and admin panel",false)
    Pool("Active", Color(0x666790a4), "Modify the database",false)
}


@Composable
fun TeamNavigationBar(selectedTeam: Department, onTeamSelected: (Department) -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Navigation buttons for each team
        TeamNavigationButton(
            team = Department.DEPARTMENT_1,
            isSelected = selectedTeam == Department.DEPARTMENT_1,
            onTeamSelected = onTeamSelected
        )
        TeamNavigationButton(
            team = Department.DEPARTMENT_2,
            isSelected = selectedTeam == Department.DEPARTMENT_2,
            onTeamSelected = onTeamSelected
        )
        TeamNavigationButton(
            team = Department.DEPARTMENT_3,
            isSelected = selectedTeam == Department.DEPARTMENT_3,
            onTeamSelected = onTeamSelected
        )
    }
}

@Composable
fun TeamNavigationButton(
    team: Department,
    isSelected: Boolean,
    onTeamSelected: (Department) -> Unit
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Button(
            onClick = { onTeamSelected(team) },
            modifier = Modifier.padding(top=15.dp, start=5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        ) {
            // Display team name as button text
            Text(text = when (team) {
                Department.DEPARTMENT_1 -> "Depr 1"
                Department.DEPARTMENT_2 -> "Depr 2"
                Department.DEPARTMENT_3 -> "Depr 3"
            })
        }
    }
}






