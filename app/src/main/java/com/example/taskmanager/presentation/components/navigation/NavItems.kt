package com.example.taskmanager.presentation.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(val route: String, val icon: ImageVector, val label: String)

val listOfNavItems = listOf(
    NavItem(Screens.Leaderboard.name, Icons.Default.Star, "Leaderboard"),
    NavItem(Screens.HomeScreen.name, Icons.Default.Home, "Home"),
    NavItem(Screens.ProfileScreen.name, Icons.Default.Person, "Profile")
)
