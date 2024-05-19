package com.example.taskmanager

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmanager.presentation.components.navigation.AppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerApp()
        }
    }
}

@Composable
fun TaskManagerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginSuccess = { userRole, userId ->
                navController.navigate("home/$userRole/$userId")
            })
        }
        composable(
            "home/{userRole}/{userId}",
            arguments = listOf(
                navArgument("userRole") { type = NavType.StringType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userRole = backStackEntry.arguments?.getString("userRole") ?: ""
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            AppNavigation(navController, userRole, userId)
        }
    }
}