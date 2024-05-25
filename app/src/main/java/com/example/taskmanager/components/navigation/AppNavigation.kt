package com.example.taskmanager.components.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.profileComponents.out.Repository
import com.example.taskmanager.screens.CTOHomeScreen
import com.example.taskmanager.screens.CTOProfile
import com.example.taskmanager.screens.HomeScreen
import com.example.taskmanager.screens.LeaderBoardScreen
import com.example.taskmanager.screens.ManagerHomeScreen
import com.example.taskmanager.screens.ProfileScreen
import com.example.taskmanager.screens.SystemAdministratorScreen


@Composable
fun AppNavigation(navControl: NavController, userRole: String, employeeId: Int) {
    val navController = rememberNavController()
    val repo = remember { Repository() }

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
                            }
                        )
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
                LeaderBoardScreen(repo, employeeId)
            }
            composable(route = Screens.HomeScreen.name) {
                when (userRole) {
                    "staff" -> HomeScreen(repo, employeeId)
                    "manager" -> ManagerHomeScreen(repo, employeeId)
                    "cto" -> CTOHomeScreen(repo, employeeId)
                    "admin" -> HomeScreen(repo, employeeId)
                }
            }
            composable(route = Screens.ProfileScreen.name) {
                when (userRole) {
                    "staff" -> ProfileScreen(repo, employeeId, navControl)
                    "manager" -> ProfileScreen(repo, employeeId, navControl)
                    "cto" -> CTOProfile(repo, employeeId)
                    "admin" -> SystemAdministratorScreen(repo, employeeId)
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
