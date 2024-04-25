package com.example.taskmanager.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.screens.HomeScreen
import com.example.taskmanager.screens.ManagerHomeScreen
import com.example.taskmanager.screens.LeaderBoardScreen
import com.example.taskmanager.screens.ProfileScreen
import com.example.taskmanager.screens.SystemAdministratorScreen


@Composable
fun AppNavigation(navControl: NavController){
    val navController = rememberNavController()
    
    Scaffold(
         bottomBar = {
             NavigationBar {
                 val navBackStackEntry by navController.currentBackStackEntryAsState()
                 val currentDestination = navBackStackEntry?.destination

                 listOfNavItems.forEach{navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route} == true,
                            onClick = {
                                     navController.navigate(navItem.route){
                                         popUpTo(navController.graph.findStartDestination().id){
                                             saveState = true
                                         }
                                         launchSingleTop = true
                                         restoreState = true
                                     }
                            },
                            icon = { Icon(
                                imageVector = navItem.icon,
                                contentDescription = null
                            ) },
                            label = {
                                Text(text = navItem.label)
                            } )
                 }

             }
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ){

            composable(route = Screens.Leaderboard.name){
                LeaderBoardScreen()
            }
            composable(route = Screens.HomeScreen.name){
                //HomeScreen()
                ManagerHomeScreen()
            }
            composable(route = Screens.ProfileScreen.name){
                ProfileScreen()
            }
            composable(route = Screens.SystemAdministratorScreen.name){
                SystemAdministratorScreen()
            }
            /*
            composable(route = Screens.ManagerHomeScreen.name){
                ManagerHomeScreen()
            }*/

        }
        
    }
}