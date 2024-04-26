package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.SolidColor

@Composable
fun SystemAdministratorScreen(){

    MaterialTheme{
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(800.dp)
                .padding(top = 30.dp, start = 20.dp, end = 10.dp)
            ,
            horizontalAlignment = Alignment.Start,
        ){

            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .background(SolidColor(Color.White), shape = RoundedCornerShape(15.dp))

            ) {
                item{
                    CreateEmployee()
                    Spacer(modifier = Modifier.height(20.dp))
                    RemoveEmployee()
                    Spacer(modifier = Modifier.height(20.dp))
                    ChangeEmployeeRole()
                    Spacer(modifier = Modifier.height(20.dp))
                    AddDepartment()
                    Spacer(modifier = Modifier.height(20.dp))
                    DeleteDepartment()



                }
            }
        }
    }
}
//deneme
@Composable
fun CreateEmployee(){
    var createEmployee by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf("") }
    var employeeName by remember { mutableStateOf("") }
    var employeeSurname by remember { mutableStateOf("") }
    var employeeRole by remember { mutableStateOf("") }
    var employeeDepartment by remember { mutableStateOf("") }
    var employeeType by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true ; createEmployee= true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Create Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
    if (createEmployee) {
        AlertDialog(
            onDismissRequest = { createEmployee = false },
            title = { Text("Add New Employee", fontWeight = FontWeight.Bold) },
            text = {
                Column (
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))

                    //.border(BorderStroke(width = 4.dp, color = Color.Black))


                ) {
                    TextField(
                        value = employeeId,
                        onValueChange = { employeeId = it },
                        label = { Text("Enter Empolyee ID:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )

                    TextField(
                        value = employeeName,
                        onValueChange = { employeeName = it },
                        label = { Text("Enter Employee Name:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                        modifier = Modifier.height(100.dp)

                    )

                    TextField(
                        value = employeeSurname,
                        onValueChange = { employeeSurname = it },
                        label = { Text("Enter Employee Surname:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    TextField(
                        value = employeeDepartment,
                        onValueChange = { employeeDepartment = it },
                        label = { Text("Enter Employee Department:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    TextField(
                        value = employeeRole,
                        onValueChange = { employeeRole = it },
                        label = { Text("Enter Employee Role:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { dropdownExpanded = true }) {
                        Text(
                            text = "Select Employee Type:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = employeeType.takeIf { it.isNotBlank() } ?: "Select Employee Type",

                            )
                    }
                    DropdownMenu(expanded = dropdownExpanded,
                        onDismissRequest = { /*TODO*/ },
                        modifier = Modifier) {

                        DropdownMenuItem(text = { Text("Staff")}, onClick = {
                            employeeType = "Staff"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Manager")}, onClick = {
                            employeeType = "Manager"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("CTO")}, onClick = {
                            employeeType = "CTO"
                            dropdownExpanded = false
                        })
                    }

                }

            },
            confirmButton = {
                Button(
                    onClick = {
                        createEmployee = false
                        employeeId = ""
                        employeeName = ""
                        employeeSurname = ""
                        employeeDepartment = ""
                        employeeRole = ""
                        employeeType = ""
                        dropdownExpanded = false
                    },
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        createEmployee = false
                        employeeId = ""
                        employeeName = ""
                        employeeSurname = ""
                        employeeDepartment = ""
                        employeeRole = ""
                        employeeType = ""
                        dropdownExpanded = false

                    },
                ) {
                    Text("Cancel")
                }
            }

        )
    }
}

@Composable
fun RemoveEmployee(){
    var removeEmployee by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; removeEmployee= true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Remove Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
    if (removeEmployee ) {
        AlertDialog(
            onDismissRequest = { removeEmployee = false },
            title = { Text("Remove Employee", fontWeight = FontWeight.Bold) },
            text = {
                Column (
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))

                    //.border(BorderStroke(width = 4.dp, color = Color.Black))


                ) {
                    TextField(
                        value = employeeId,
                        onValueChange = { employeeId = it },
                        label = { Text("Enter Employee ID:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        employeeId = ""
                        removeEmployee=false
                    },
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        employeeId = ""
                        removeEmployee=false

                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ChangeEmployeeRole(){
    var changeEmployeeRole by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf("") }
    var employeeRole by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; changeEmployeeRole= true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Change Employee Role", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
    if (changeEmployeeRole ) {
        AlertDialog(
            onDismissRequest = { changeEmployeeRole = false },
            title = { Text("Change Employee Role", fontWeight = FontWeight.Bold) },
            text = {
                Column (
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))

                    //.border(BorderStroke(width = 4.dp, color = Color.Black))


                ) {
                    TextField(
                        value = employeeId,
                        onValueChange = { employeeId = it },
                        label = { Text("Enter Employee ID:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    TextField(
                        value = employeeRole,
                        onValueChange = { employeeRole = it },
                        label = { Text("Enter Employee Role:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        employeeId = ""
                        changeEmployeeRole=false
                    },
                ) {
                    Text("Change")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        employeeId = ""
                        changeEmployeeRole=false

                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Composable
fun AddDepartment(){
    var createDepartment by remember { mutableStateOf(false) }
    var departmentName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true ; createDepartment= true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Add Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
    if (createDepartment ) {
        AlertDialog(
            onDismissRequest = { createDepartment = false },
            title = { Text("Add New Department ", fontWeight = FontWeight.Bold) },
            text = {
                Column (
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                        .padding(5.dp, top =10.dp, bottom=10.dp)
                    //.border(BorderStroke(width = 4.dp, color = Color.Black))


                ) {
                    TextField(
                        value = departmentName,
                        onValueChange = { departmentName = it },
                        label = { Text("Enter Department Name: ", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        departmentName = ""
                        createDepartment=false
                    },
                ) {
                    Text("Change")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        departmentName = ""
                        createDepartment=false

                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DeleteDepartment(){
    var deleteDepartment by remember { mutableStateOf(false) }
    var departmentName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true ; deleteDepartment= true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Delete Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
    if (deleteDepartment ) {
        AlertDialog(
            onDismissRequest = { deleteDepartment = false },
            title = { Text("Delete Department", fontWeight = FontWeight.Bold) },
            text = {
                Column (
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                        .padding(5.dp, top =10.dp, bottom=10.dp)
                    //.border(BorderStroke(width = 4.dp, color = Color.Black))


                ) {
                    TextField(
                        value = departmentName,
                        onValueChange = { departmentName = it },
                        label = { Text("Enter Department Name:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        departmentName = ""
                        deleteDepartment=false
                    },
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        departmentName = ""
                        deleteDepartment=false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

