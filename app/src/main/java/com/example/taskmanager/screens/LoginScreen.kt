package com.example.taskmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.Employee
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.ui.theme.darkBackground
import com.example.taskmanager.ui.theme.gray
import com.example.taskmanager.ui.theme.lightpurple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(repo: Repository, onLoginSuccess: (userRole: String, employeeId: Int) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
        .background(darkBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.file),
            contentDescription = "App Icon",
            modifier = Modifier.height(100.dp)
        )
        Text(
            text = "Welcome To Task Manager" ,color=Color(0xFFC0BCC9),
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Please Login to continue",color=Color(0xFFC0BCC9),
            fontStyle = FontStyle.Italic,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(70.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address", color =Color(0xFFC0BCC9) ) },
            textStyle = TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password", color =Color(0xFFC0BCC9) ) },
            textStyle = TextStyle(color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = lightpurple
            ),
            onClick = {
            // Use LaunchedEffect to handle the coroutine
            CoroutineScope(Dispatchers.IO).launch {
                val employee = authenticateUser(repo, email, password)
                if (employee != null) {
                    withContext(Dispatchers.Main) {
                        onLoginSuccess(employee.role.name.lowercase(), employee.id)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        errorMessage = "User not found or invalid credentials"
                    }
                }
            }
        }) {
            Text(text = "Login",color= gray)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(1.dp))

        TextButton(onClick = { }) {
            Text(text = "Forgot Password?", color = lightpurple)
        }
    }
}

suspend fun authenticateUser(repo: Repository, email: String, password: String): Employee? {
    return repo.getEmployeeByEmailAndPassword(email, password)
}
