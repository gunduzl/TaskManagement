package com.example.taskmanager.presentation.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.presentation.screens.*



@Composable
fun AppNavigation(navControl: NavController, userRole: String, userId: Int, departmentId: Int? = null) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfNavItems.forEach { navItem ->
                    if (shouldShowNavItem(navItem.route, userRole)) {
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
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.Leaderboard.name) {
                LeaderBoardScreen()
            }
            composable(route = Screens.HomeScreen.name) {
                when (userRole.lowercase()) {
                    "staff" -> HomeScreen(userId)
                    "manager" -> ManagerHomeScreen(managerId = userId, departmentId = departmentId ?: 0)
                    "cto" -> CTOHomeScreen()
                    "admin" -> CTOHomeScreen()
                }
            }
            composable(route = Screens.ProfileScreen.name) {
                when (userRole.lowercase()) {
                    "staff" -> ProfileScreen(userRole, userId)
                    "manager" -> ManagerProfile()
                    "cto" -> CTOProfile()
                    "admin" -> SystemAdministratorScreen()
                }
            }
        }
    }
}

private fun shouldShowNavItem(route: String, userRole: String): Boolean {
    val navItemsToShow = mapOf(
        "staff" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name),
        "manager" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name),
        "cto" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name),
        "admin" to listOf(Screens.Leaderboard.name, Screens.HomeScreen.name, Screens.ProfileScreen.name)
    )
    return navItemsToShow[userRole]?.contains(route) ?: false
}
