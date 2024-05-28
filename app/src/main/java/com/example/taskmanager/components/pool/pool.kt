package com.example.taskmanager.components.pool

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.Employee
import com.example.taskmanager.profileComponents.out.HelpType
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Role
import com.example.taskmanager.profileComponents.out.Task
import com.example.taskmanager.profileComponents.out.TaskStatus
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightgray
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.launch

@Composable
fun Pool(
    employeeId: Int,
    name: String,
    rowColor: Color,
    tasks: List<Task>,
    isStaff: Boolean,
    repo: Repository,
    refreshTasks: () -> Unit
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .size(width = 800.dp, height = 280.dp)
                .padding(top = 10.dp, start = 20.dp, end = 10.dp)
                .background(lightpurple, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(modifier = Modifier.padding(top = 8.dp) )
            Text(text = "   $name Tasks", color= darkBackground,fontSize = 25.sp,
                fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)


            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .background(SolidColor(lightpurple), shape = RoundedCornerShape(15.dp))
            ) {
                items(tasks.size) { index ->
                    val task = tasks[index]
                    TaskItem(employeeId, task, rowColor, name, isStaff, repo, HelpType.Default,refreshTasks)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    employeeId: Int,
    task: Task,
    rowColor: Color,
    openOrNot: String,
    isStaff: Boolean,
    repo: Repository,
    isHelp: HelpType,
    refreshTasks: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(true) }
    var employee by remember { mutableStateOf<Employee?>(null) }
    var employeeRole by remember { mutableStateOf<Role?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(employeeId) {
        employee = repo.getEmployeeById(employeeId)
        employeeRole = employee?.role
    }


    Row(
        modifier = Modifier
            .background(color = rowColor, shape = RoundedCornerShape(15.dp))
            .border(2.dp, color = rowColor, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(70.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            if(task.status == TaskStatus.OPEN){
                Text(text = task.title,color= darkBackground, fontSize = 20.sp,
                    textAlign = TextAlign.Left, fontWeight = FontWeight.Bold) //TASK İSİMLERİ
            }else{
                Text(text = task.title,color= lightgray, fontSize = 20.sp,
                    textAlign = TextAlign.Left, fontWeight = FontWeight.Bold) //TASK İSİMLERİ
            }

            Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing between buttons
            if(task.isHelp == HelpType.Requested && employeeRole == Role.MANAGER && showButtons){
                Row {


                    Button( colors = ButtonDefaults.buttonColors(
                            containerColor = lightpurple
                        ),
                        onClick = {
                        coroutineScope.launch {
                            task.isHelp = HelpType.Confirmed
                            task.status = TaskStatus.OPEN
                            refreshTasks()
                        }
                        showButtons = false
                    }
                    ) {
                        Text("Confirm", color= darkBackground)
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing between buttons
                    Button( colors = ButtonDefaults.buttonColors(
                        containerColor = lightpurple
                    ),
                        onClick = {
                            coroutineScope.launch {
                                task.isHelp = HelpType.Rejected
                                refreshTasks()
                            }
                            showButtons = false
                        }
                    ) {
                        Text("Reject", color= darkBackground)
                    }
                }
            }
        }
    }


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Row{
                Text(task.title, color = lightgray ,fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle(10))
                Spacer(modifier = Modifier.width(30.dp))
                Text("${task.taskPoint} Point",fontWeight = FontWeight.Medium, fontStyle = FontStyle(5 ), modifier = Modifier.alpha(0.5f))
            } },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if(task.isHelp == HelpType.Confirmed){
                        Text("HELP")
                    }
                    Row { Text("Description: ", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text(task.description ,color= lightgray) }
                    Row { Text("Due Date:  ", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text(task.deadline,color= lightgray) }
                    Row { Text("Time Left: ", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text("30 Seconds",color= lightgray) } // This should be dynamically calculated
                    Row { Text("Difficulty: ", fontWeight = FontWeight.Bold,color= lightpurple) }
                    Row { Text(task.difficulty.name,color= lightgray )}

                }
            },
            confirmButton = {

                if(task.status==TaskStatus.OPEN && isStaff){
                    Button(                        colors = ButtonDefaults.buttonColors(
                        containerColor = lightpurple
                    ),
                        onClick = {
                            coroutineScope.launch {
                                repo.takeTask(employeeId, task.id) // First take the task
                                //repo.updateTaskStatus(task.id, TaskStatus.ACTIVE) // Then update the status
                                task.status = TaskStatus.ACTIVE
                                refreshTasks()
                                showDialog = false
                            }
                        }
                    ) { Text("Take Task",color= darkBackground) }
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = { showDialog = false }) { Text("Cancel" ,color= darkBackground) }
            },  containerColor = darkBackground
        )
    }
}