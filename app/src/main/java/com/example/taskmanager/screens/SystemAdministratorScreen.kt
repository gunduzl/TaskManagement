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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.taskmanager.profileComponents.out.CTO
import com.example.taskmanager.profileComponents.out.Department
import com.example.taskmanager.profileComponents.out.Employee
import com.example.taskmanager.profileComponents.out.Manager
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.profileComponents.out.Role
import com.example.taskmanager.profileComponents.out.Staff
import com.example.taskmanager.profileComponents.out.StaffStatus
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.launch


@Composable
fun SystemAdministratorScreen(repo: Repository,employeeId: Int, navController: NavController) {
    MaterialTheme {
        Row(modifier = Modifier.padding(top = 10.dp, start = 280.dp, bottom = 200.dp)) {
            Button( colors = ButtonDefaults.buttonColors(
                containerColor = darkBackground),
                onClick = {
                    // Navigate back to the login screen
                    navController.navigate("/first_screen") {
                        popUpTo("/app-navigation") { inclusive = true }
                    }
                }) {
                Text("Logout",color= lightpurple)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(800.dp)
                .padding(top = 70.dp, start = 20.dp, end = 10.dp),
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
    var departments by remember { mutableStateOf<List<Department>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var selectedDepartment by remember { mutableStateOf<Department?>(null) }
    var expanded by remember { mutableStateOf(false) }

    suspend fun getLastEmployeeId(): Int {
        return repo.getAllEmployees().maxByOrNull { it.id }?.id ?: 0
    }
    LaunchedEffect(Unit) {
        departments = repo.getAllDepartments()
    }

    Row(
        modifier = Modifier
            .background(lightpurple, shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; createEmployee = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Create Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold ,color= darkBackground)
    }

    if (createEmployee) {

        AlertDialog(
            onDismissRequest = { createEmployee = false },
            title = { Text("Add New Employee", fontWeight = FontWeight.Bold,color= lightpurple) },
            text = {
                Column(
                    modifier = Modifier
                        .background(lightpurple, shape = RoundedCornerShape(20.dp))
                ) {

                    TextField(
                        value = employeeName,
                        onValueChange = { employeeName = it },
                        label = { Text("Enter Employee Name:", fontWeight = FontWeight.Bold,color= darkBackground) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                        modifier = Modifier.height(80.dp)
                    )

                    TextField(
                        value = employeeSurname,
                        onValueChange = { employeeSurname = it },
                        label = { Text("Enter Employee Surname:", fontWeight = FontWeight.Bold,color= darkBackground) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { expanded = true }
                    ) {
                        Text(
                            text = "Enter Department:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
                        )
                        Text(
                            text = employeeDepartment.takeIf { it.isNotBlank() } ?: "Select Department",
                        )

                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(lightpurple)
                    ) {
                        departments.forEach { department ->
                            DropdownMenuItem(text = { Text(department.name) },
                                onClick = {
                                    selectedDepartment = department
                                    employeeDepartment = department.name
                                    expanded = false
                                }
                            )
                        }
                    }

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
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
                        )
                        Text(
                            text = employeeType.takeIf { it.isNotBlank() } ?: "Select Employee Type",
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier .fillMaxWidth()
                            .background(lightpurple),

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
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        coroutineScope.launch {
                            val lastEmployeeId = getLastEmployeeId() + 1
                            val departmentId = selectedDepartment?.id
                            val managerId = repo.getManagerIdByDepartmentName(employeeDepartment)
                            if(departmentId != null){
                                when (employeeType) {
                                    "Staff" -> {
                                        if(managerId != null){
                                            val employee = Staff(
                                                lastEmployeeId, employeeName,
                                                "$employeeName@gmail.com",
                                                "password", Role.valueOf(employeeRole), 0,
                                                StaffStatus.AVAILABLE, departmentId, managerId
                                            )
                                            repo.insertStaff(employee)
                                        }


                                    }
                                    "Manager" -> {
                                        val employee = Manager(
                                            lastEmployeeId, employeeName,
                                            "$employeeName@gmail.com",
                                            "password", Role.valueOf(employeeRole), 0, departmentId)
                                        repo.insertManager(employee)
                                    }
                                    "CTO" -> {
                                        val employee = CTO(
                                            lastEmployeeId, employeeName,
                                            "$employeeName@gmail.com",
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
                    Text("Add",color= darkBackground)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
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
                    Text("Cancel",color= darkBackground)
                }
            } , containerColor = darkBackground
        )
    }
}

@Composable
fun RemoveEmployee(repo: Repository) {
    var removeEmployee by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var employeeDepartment by remember { mutableStateOf("") }
    var employeeName by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }
    var departments by remember { mutableStateOf<List<Department>>(emptyList()) }
    var employees by remember { mutableStateOf<List<Employee>>(emptyList()) }
    var selectedDepartment by remember { mutableStateOf<Department?>(null) }
    var selectedEmployee by remember { mutableStateOf<Employee?>(null) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        departments = repo.getAllDepartments()
        employees = repo.getAllEmployees()
    }

    Row(
        modifier = Modifier
            .background(lightpurple, shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; removeEmployee = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Remove Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold,color= darkBackground)
    }

    if (removeEmployee) {
        AlertDialog(
            onDismissRequest = { removeEmployee = false },
            title = { Text("Remove Employee", fontWeight = FontWeight.Bold,color= lightpurple) },
            text = {
                Column(
                    modifier = Modifier
                        .background(lightpurple, shape = RoundedCornerShape(20.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { expanded = true }
                    ) {
                        Text(
                            text = "Enter Department:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
                        )
                        Text(
                            text = employeeDepartment.takeIf { it.isNotBlank() } ?: "Select Department",
                        )

                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(lightpurple)
                    ) {
                        departments.forEach { department ->
                            DropdownMenuItem(text = { Text(department.name) },
                                onClick = {
                                    selectedDepartment = department
                                    employeeDepartment = department.name
                                    expanded = false
                                }
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { dropdownExpanded = true }
                    ) {
                        Text(
                            text = "Enter Employee:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
                        )
                        Text(
                            text = employeeName.takeIf { it.isNotBlank() } ?: "Select Employee",
                        )

                    }
                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(lightpurple)
                    ) {
                        employees.forEach { employee ->
                            DropdownMenuItem(text = { Text(employee.name) },
                                onClick = {
                                    selectedEmployee = employee
                                    employeeName = employee.name
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        val id = employeeId.toIntOrNull()
                        if (id != null) {
                            coroutineScope.launch {
                                repo.deleteEmployeeById(id)
                            }
                            employeeId = ""
                            removeEmployee = false
                        }
                        removeEmployee = false
                    },
                ) {
                    Text("Remove", color=darkBackground)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        employeeId = ""
                        removeEmployee = false
                    },
                ) {
                    Text("Cancel", color=darkBackground)
                }
            }
            , containerColor = darkBackground
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
    var employeeDepartment by remember { mutableStateOf("") }
    var employeeName by remember { mutableStateOf("") }
    var departments by remember { mutableStateOf<List<Department>>(emptyList()) }
    var employees by remember { mutableStateOf<List<Employee>>(emptyList()) }
    var selectedDepartment by remember { mutableStateOf<Department?>(null) }
    var selectedEmployee by remember { mutableStateOf<Employee?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var dropdownExpandedEmp by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        departments = repo.getAllDepartments()
        employees = repo.getAllEmployees()
    }

    Row(
        modifier = Modifier
            .background(lightpurple, shape = RoundedCornerShape(15.dp))
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
            fontWeight = FontWeight.Bold,
            color= darkBackground
        )
    }

    if (changeEmployeeRole) {
        AlertDialog(
            onDismissRequest = { changeEmployeeRole = false },
            title = { Text("Change Employee Role", fontWeight = FontWeight.Bold,color= lightpurple) },
            text = {
                Column(
                    modifier = Modifier
                        .background(lightpurple, shape = RoundedCornerShape(20.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { expanded = true }
                    ) {
                        Text(
                            text = "Enter Department:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
                        )
                        Text(
                            text = employeeDepartment.takeIf { it.isNotBlank() } ?: "Select Department",
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(lightpurple)
                    ) {
                        departments.forEach { department ->
                            DropdownMenuItem(text = { Text(department.name) },
                                onClick = {
                                    selectedDepartment = department
                                    employeeDepartment = department.name
                                    expanded = false
                                }
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { dropdownExpandedEmp = true }
                    ) {
                        Text(
                            text = "Enter Employee:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
                        )
                        Text(
                            text = employeeName.takeIf { it.isNotBlank() } ?: "Select Employee",
                        )

                    }
                    DropdownMenu(
                        expanded = dropdownExpandedEmp,
                        onDismissRequest = { dropdownExpandedEmp = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(lightpurple)
                    ) {
                        employees.forEach { employee ->
                            //filteredEmployees.forEach { employee ->
                            DropdownMenuItem(text = { Text(employee.name) },
                                onClick = {
                                    selectedEmployee = employee
                                    employeeName = employee.name
                                    dropdownExpandedEmp = false
                                }
                            )
                        }
                    }
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
                            modifier = Modifier.padding(end = 8.dp),
                            color= darkBackground
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
                            .fillMaxWidth()
                            .background(lightpurple)
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
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
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
                    Text("Change",color= darkBackground)
                }
            },
            dismissButton = {
                Button( colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        employeeId = ""
                        employeeRole = ""
                        changeEmployeeRole = false
                    },
                ) {
                    Text("Cancel",color= darkBackground)
                }
            }
            , containerColor = darkBackground
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
            .background(lightpurple, shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; createDepartment = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Add Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold,color=darkBackground)
    }

    if (createDepartment) {
        AlertDialog(
            onDismissRequest = { createDepartment = false },
            title = { Text("Add New Department", fontWeight = FontWeight.Bold,color=lightpurple) },
            text = {
                Column(
                    modifier = Modifier
                        .background(lightpurple, shape = RoundedCornerShape(20.dp))
                        .padding(5.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    TextField(
                        value = departmentName,
                        onValueChange = { departmentName = it },
                        label = { Text("Enter Department Name:", fontWeight = FontWeight.Bold,color= darkBackground) },
                        colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
                    )
                }
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
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
                    Text("Add",color= darkBackground)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        departmentName = ""
                        createDepartment = false
                    },
                ) {
                    Text("Cancel",color= darkBackground)
                }
            }
            , containerColor = darkBackground
        )
    }
}

@Composable
fun DeleteDepartment(repo: Repository) {
    var deleteDepartment by remember { mutableStateOf(false) }
    var selectedDepartment by remember { mutableStateOf<Department?>(null) }
    var departmentName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var departments by remember { mutableStateOf<List<Department>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        departments = repo.getAllDepartments()
    }

    Row(
        modifier = Modifier
            .background(lightpurple, shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true; deleteDepartment = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Delete Department",
            fontSize = 20.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color= darkBackground
        )
    }

    if (deleteDepartment) {
        AlertDialog(
            onDismissRequest = { deleteDepartment = false },
            title = { Text("Delete Department", fontWeight = FontWeight.Bold,color= lightpurple) },
            text = {
                Column(
                    modifier = Modifier
                        .background(lightpurple, shape = RoundedCornerShape(20.dp))
                        .padding(5.dp, top = 10.dp, bottom = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 15.dp, bottom = 20.dp, top = 20.dp)
                            .clickable { expanded = true }
                    ) {
                        Text(
                            text = "Enter Department:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp),color= darkBackground
                        )
                        Text(
                            text = departmentName.takeIf { it.isNotBlank() } ?: "Select Department",
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(lightpurple)
                    ) {
                        departments.forEach { department ->
                            DropdownMenuItem(text = { Text(department.name) },
                                onClick = {
                                    selectedDepartment = department
                                    departmentName = department.name
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        coroutineScope.launch {
                            repo.deleteDepartmentById(selectedDepartment!!.id)
                        }
                        selectedDepartment = null
                        deleteDepartment = false
                    }
                ) {
                    Text("Delete",color= darkBackground)
                }
            },
            dismissButton = {
                Button(colors = ButtonDefaults.buttonColors(
                    containerColor = lightpurple
                ),
                    onClick = {
                        selectedDepartment = null
                        deleteDepartment = false
                    }
                ) {
                    Text("Cancel",color= darkBackground)
                }
            }
            , containerColor = darkBackground
        )
    }
}