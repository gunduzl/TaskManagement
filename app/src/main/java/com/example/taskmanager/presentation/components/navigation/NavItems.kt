package com.example.taskmanager.presentation.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String

)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Leaderboard",
        icon = Icons.Default.Star,
        route = Screens.Leaderboard.name
    ),
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Profile",
        icon = Icons.Default.Person,
        route = Screens.ProfileScreen.name
    ),
    NavItem(
        label = "SystemAdministrator",
        icon = Icons.Default.Share,
        route = Screens.SystemAdministratorScreen.name
    )/*
    NavItem(
        label = "Manager",
        icon = Icons.Default.Person,
        route = Screens.ManagerHomeScreen.name
    )*/
)