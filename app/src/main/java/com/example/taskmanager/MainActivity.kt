package com.example.taskmanager

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.components.navigation.AppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "/first_screen") {
                composable(route = "/first_screen") {
                    LoginScreen { userRole ->
                        // Navigate to the AppNavigation screen and pass the user role
                        navController.navigate("/app-navigation/$userRole")
                    }
                }
                composable(route = "/app-navigation/{userRole}") { backStackEntry ->
                    // Extract user role from the route arguments
                    val userRole = backStackEntry.arguments?.getString("userRole") ?: ""
                    // Pass the user role to the AppNavigation composable
                    AppNavigation(navControl = navController, userRole = userRole)
                }
            }
        }
    }
}
