package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.components.navigation.AppNavigation
import com.example.taskmanager.profileComponents.out.Repository


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val repo = Repository()

            NavHost(navController = navController, startDestination = "/first_screen") {
                composable(route = "/first_screen") {
                    LoginScreen(repo) { userRole, employeeId ->
                        // Navigate to the AppNavigation screen and pass the user role and employee ID
                        navController.navigate("/app-navigation/$userRole/$employeeId")
                    }
                }

                composable(route = "/app-navigation/{userRole}/{employeeId}") { backStackEntry ->
                    // Extract user role and employee ID from the route arguments
                    val userRole = backStackEntry.arguments?.getString("userRole") ?: ""
                    val employeeId = backStackEntry.arguments?.getString("employeeId")?.toInt() ?: -1
                    // Pass the user role and employee ID to the AppNavigation composable
                    AppNavigation(navControl = navController, userRole = userRole, employeeId = employeeId)
                }
            }
        }
    }
}