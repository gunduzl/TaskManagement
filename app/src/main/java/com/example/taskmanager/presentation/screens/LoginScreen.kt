import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.presentation.viewmodel.factory.LoginViewModelFactory
import com.example.taskmanager.presentation.viewmodel.pages.LoginViewModel


@Composable
fun LoginScreen(onLoginSuccess: (userRole: String, userId: Int, departmentId: Int?) -> Unit) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context.applicationContext as Application))
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val userRoleWithIdAndDept by loginViewModel.userRoleWithIdAndDept.observeAsState()

    LaunchedEffect(userRoleWithIdAndDept) {
        userRoleWithIdAndDept?.let { (userRole, userId, departmentId) ->
            if (userRole.isNotEmpty()) {
                onLoginSuccess(userRole, userId, departmentId)
            } else {
                errorMessage = "User not found or invalid credentials"
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login to your account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email Address") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            loginViewModel.authenticateUser(email, password)
        }) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { /* Add forgot password logic */ }) {
            Text(text = "Forgot Password?")
        }
    }
}
