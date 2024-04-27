package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import com.example.taskmanager.components.pool.Pool




@Composable
fun ManagerHomeScreen(){


    var showAddDialog by remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }
    var taskDifficulty by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }





    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 10.dp, start = 20.dp, end = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(180.dp)
        ){
            Button(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null )
            }
            Button(onClick = {
            }) {
                Text("Logout")
            }
        }

        Row(
            modifier = Modifier.padding(top=20.dp)
        ) {
            Text(text = "My Department",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end= 20.dp, start= 40.dp)
            )

            Button(onClick = { showAddDialog = true  }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null )
            }
        }

        Pool("Open", Color(0x666650a4),"Develop and admin panel") //0xFFF0F8FF
        Pool("Active", Color(0x666790a4),"Develop and admin panel") //0xFFFFADB0

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
    }

}