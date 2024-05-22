package com.example.taskmanager.presentation.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.data.repository.DbRepo
import com.example.taskmanager.presentation.screens.CTOHomeScreen
import com.example.taskmanager.presentation.screens.CTOProfile
import com.example.taskmanager.presentation.screens.HomeScreen
import com.example.taskmanager.presentation.screens.LeaderBoardScreen
import com.example.taskmanager.presentation.screens.ManagerHomeScreen
import com.example.taskmanager.presentation.screens.ManagerProfile
import com.example.taskmanager.presentation.screens.ProfileScreen
import com.example.taskmanager.presentation.screens.SystemAdministratorScreen



@Composable
fun AppNavigation(navControl: NavController, userRole: String) {
    val navController = rememberNavController()

    val repo = DbRepo(LocalContext.current)

    var test: String = "d"





    LaunchedEffect(Unit) {
        //repo.deleteAllStaff()
        repo.createStaff()
        test = repo.getStaffById(1).name
        println(test)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    // Conditionally show navigation items based on user role
                    if (shouldShowNavItem(navItem.route, userRole,test)) {
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(text = navItem.label)
                            })
                    }
                }

            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.Leaderboard.name) {
                LeaderBoardScreen()
            }
            composable(route = Screens.HomeScreen.name) {
                when (userRole) {
                    "staff" -> HomeScreen()
                    "manager" -> ManagerHomeScreen()
                    "cto" -> CTOHomeScreen()
                    "admin" -> HomeScreen()
                }
            }
            composable(route = Screens.ProfileScreen.name) {
                when (userRole) {
                    "staff" -> ProfileScreen()
                    "manager" -> ManagerProfile()
                    "cto" -> CTOProfile()
                    "admin" -> SystemAdministratorScreen()
                }
            }
        }
    }
}

private fun shouldShowNavItem(route: String, userRole: String, test: String): Boolean {
    // Define navigation items to show for each user role
    val navItemsToShow = mapOf(
        //"staff" to listOf(test, test, test),
        "staff" to listOf( Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name),
        "manager" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name ),
        "cto" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name),
        "admin" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name)
    )
    return navItemsToShow[userRole]?.contains(route) ?: false
}