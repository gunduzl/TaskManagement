package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.components.navigation.AppNavigation
import com.example.taskmanager.screens.LoginScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val navControl = rememberNavController()

            NavHost(navController = navControl, startDestination = "/first_screen") {
                composable(route = "/first_screen") {
                    LoginScreen(navControl = navControl)
                }
                composable(route = "/app-navigation") {
                    AppNavigation(navControl = navControl)
                }
            }



            //LoginScreen()
            //AppNavigation()

        }
    }
}