package com.example.taskmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.CTO
import com.example.taskmanager.profileComponents.out.Department
import com.example.taskmanager.profileComponents.out.Manager
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Role
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.profileComponents.out.StaffStatus
import kotlinx.coroutines.launch


@Composable
fun SystemAdministratorScreen(repo: Repository,employeeId: Int) {



    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(800.dp)
                .padding(top = 30.dp, start = 20.dp, end = 10.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .background(SolidColor(Color.White), shape = RoundedCornerShape(15.dp))
            ) {
                item {
                    CreateEmployee(repo)
                    Spacer(modifier = Modifier.height(20.dp))
                    RemoveEmployee(repo)
                    Spacer(modifier = Modifier.height(20.dp))
                    ChangeEmployeeRole(repo)
                    Spacer(modifier = Modifier.height(20.dp))
                    AddDepartment(repo)
                    Spacer(modifier = Modifier.height(20.dp))
                    DeleteDepartment(repo)
                }
            }
        }
    }
}

@Composable
fun CreateEmployee(repo: Repository) {
    var createEmployee by remember { mutableStateOf(false) }
    var employeeName by remember { mutableStateOf("") }
    var employeeSurname by remember { mutableStateOf("") }
    var employeeRole by remember { mutableStateOf("") }
    var employeeDepartment by remember { mutableStateOf("") }
    var employeeType by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    suspend fun getLastEmployeeId(): Int {
        return repo.getAllEmployees().maxByOrNull { it.id }?.id ?: 0
    }

    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; createEmployee = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Create Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
    }

    if (createEmployee) {

        AlertDialog(
            onDismissRequest = { createEmployee = false },
            title = { Text("Add New Employee", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                ) {

                    TextField(
                        value = employeeName,
                        onValueChange = { employeeName = it },
                        label = { Text("Enter Employee Name:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                        modifier = Modifier.height(80.dp)
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { dropdownExpanded = true }
                    ) {
                        Text(
                            text = "Enter Employee Role:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = employeeType.takeIf { it.isNotBlank() } ?: "Select Employee Type",
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier
                    ) {
                        DropdownMenuItem(text = { Text("Staff") }, onClick = {
                            employeeType = "Staff"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Manager") }, onClick = {
                            employeeType = "Manager"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("CTO") }, onClick = {
                            employeeType = "CTO"
                            dropdownExpanded = false
                        })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val lastEmployeeId = getLastEmployeeId() + 1
                            val departmentId = repo.getDepartmentIdByName(employeeDepartment)
                            val managerId = repo.getManagerIdByDepartmentName(employeeDepartment)
                            if(departmentId != null){
                                when (employeeType) {
                                    "Staff" -> {
                                        if(managerId != null){
                                            val employee = Staff(
                                                lastEmployeeId, employeeName, employeeName + "@example.com",
                                                "password", Role.valueOf(employeeRole), 0,
                                                StaffStatus.AVAILABLE, departmentId, managerId
                                            )
                                            repo.insertStaff(employee)
                                        }


                                    }
                                    "Manager" -> {
                                        val employee = Manager(
                                            lastEmployeeId, employeeName, employeeName + "@example.com",
                                            "password", Role.valueOf(employeeRole), 0, departmentId)
                                        repo.insertManager(employee)
                                    }
                                    "CTO" -> {
                                        val employee = CTO(
                                            lastEmployeeId, employeeName, employeeName + "@example.com",
                                            "password", Role.valueOf(employeeRole)
                                        )
                                        repo.insertCTO(employee)
                                    }
                                }
                            }
                        }
                        createEmployee = false
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
fun RemoveEmployee(repo: Repository) {
    var removeEmployee by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; removeEmployee = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Remove Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
    }

    if (removeEmployee) {
        AlertDialog(
            onDismissRequest = { removeEmployee = false },
            title = { Text("Remove Employee", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
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
                        coroutineScope.launch {
                            repo.deleteEmployeeById(employeeId.toInt())
                        }
                        employeeId = ""
                        removeEmployee = false
                    },
                ) {
                    Text("Remove")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        employeeId = ""
                        removeEmployee = false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ChangeEmployeeRole(repo: Repository) {
    var changeEmployeeRole by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf("") }
    var employeeRole by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; changeEmployeeRole = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Change Employee Role",
            fontSize = 20.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
    }

    if (changeEmployeeRole) {
        AlertDialog(
            onDismissRequest = { changeEmployeeRole = false },
            title = { Text("Change Employee Role", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                ) {
                    TextField(
                        value = employeeId,
                        onValueChange = { employeeId = it },
                        label = { Text("Enter Employee ID:", fontWeight = FontWeight.Bold) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { dropdownExpanded = true }
                    ) {
                        Text(
                            text = "Enter Employee Role:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = if (employeeRole.isNotBlank()) employeeRole else "Select Employee Type",
                            color = if (employeeRole.isNotBlank()) Color.Black else Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier
                    ) {
                        DropdownMenuItem(text = { Text("Staff") }, onClick = {
                            employeeRole = "Staff"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("Manager") }, onClick = {
                            employeeRole = "Manager"
                            dropdownExpanded = false
                        })
                        DropdownMenuItem(text = { Text("CTO") }, onClick = {
                            employeeRole = "CTO"
                            dropdownExpanded = false
                        })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            when (employeeRole) {
                                "Staff" -> repo.updateEmployeeRole(employeeId.toInt(), Role.STAFF)
                                "Manager" -> repo.updateEmployeeRole(employeeId.toInt(), Role.MANAGER)
                                "CTO" -> repo.updateEmployeeRole(employeeId.toInt(), Role.CTO)
                            }
                        }
                        employeeId = ""
                        employeeRole = ""
                        changeEmployeeRole = false
                    },
                ) {
                    Text("Change")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        employeeId = ""
                        employeeRole = ""
                        changeEmployeeRole = false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}



@Composable
fun AddDepartment(repo: Repository) {
    var createDepartment by remember { mutableStateOf(false) }
    var departmentName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; createDepartment = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Add Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
    }

    if (createDepartment) {
        AlertDialog(
            onDismissRequest = { createDepartment = false },
            title = { Text("Add New Department", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                        .padding(5.dp, top = 10.dp, bottom = 10.dp)
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
                        coroutineScope.launch {
                            val depId = repo.getDepartmentIdByName(departmentName)
                            if (depId != null) {
                                repo.insertDepartment(Department(depId, departmentName))
                            }
                        }
                        departmentName = ""
                        createDepartment = false
                    },
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        departmentName = ""
                        createDepartment = false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DeleteDepartment(repo: Repository) {
    var deleteDepartment by remember { mutableStateOf(false) }
    var departmentName by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .background(Color(0x666790a4), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; deleteDepartment = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Delete Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
    }

    if (deleteDepartment) {
        AlertDialog(
            onDismissRequest = { deleteDepartment = false },
            title = { Text("Delete Department", fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier
                        .background(Color(0x336650a4), shape = RoundedCornerShape(20.dp))
                        .padding(5.dp, top = 10.dp, bottom = 10.dp)
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
                        coroutineScope.launch {
                            repo.deleteDepartmentByName(departmentName)
                        }
                        departmentName = ""
                        deleteDepartment = false
                    },
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        departmentName = ""
                        deleteDepartment = false
                    },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
