package com.example.taskmanager



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.components.navigation.AppNavigation
import com.example.taskmanager.profileComponents.out.RepositoryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val repoViewModel: RepositoryViewModel = viewModel()

            NavHost(navController = navController, startDestination = "/first_screen") {
                composable(route = "/first_screen") {
                    LoginScreen(repoViewModel.repository) { userRole, employeeId ->
                        navController.navigate("/app-navigation/$userRole/$employeeId") {
                            popUpTo("/first_screen") { inclusive = true }
                        }
                    }
                }

                composable(route = "/app-navigation/{userRole}/{employeeId}") { backStackEntry ->
                    val userRole = backStackEntry.arguments?.getString("userRole") ?: ""
                    val employeeId = backStackEntry.arguments?.getString("employeeId")?.toInt() ?: -1
                    AppNavigation(navControl = navController, userRole = userRole, employeeId = employeeId, repo = repoViewModel.repository)
                }
            }
        }
    }
}
