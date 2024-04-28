package com.example.taskmanager.screens

import MyTeam_A
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskmanager.profileComponents.MyTeam_B
import com.example.taskmanager.profileComponents.MyTeam_C


//elif


// Define the teams
enum class Team {
    TEAM_A,
    TEAM_B,
    TEAM_C,
}

@Composable
fun CTOProfile() {
    val (selectedTeam, setSelectedTeam) = remember { mutableStateOf(Team.TEAM_A) }
    val (showNotification, setShowNotification) = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        val ctoName = "KYNC"
        CTOProfileHeader(ctoName, setShowNotification) // Pass setShowNotification function
        // Navigation bar
        TeamNavigationBar(selectedTeam, setSelectedTeam)
        // Display team details based on the selected team
        when (selectedTeam) {
            Team.TEAM_A -> MyTeam_A()
            Team.TEAM_B -> MyTeam_B()
            Team.TEAM_C -> MyTeam_C()
        }
    }
    if (showNotification) {
        NotificationScreen(onClose = { setShowNotification(false) })
    }
}

@Composable
fun TeamNavigationBar(selectedTeam: Team, onTeamSelected: (Team) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 50.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,

    )
    {
        Spacer(modifier = Modifier.height(150.dp))
        // Navigation buttons for each team
        TeamNavigationButton(
            team = Team.TEAM_A,
            isSelected = selectedTeam == Team.TEAM_A,
            onTeamSelected = onTeamSelected
        )
        TeamNavigationButton(
            team = Team.TEAM_B,
            isSelected = selectedTeam == Team.TEAM_B,
            onTeamSelected = onTeamSelected
        )
        TeamNavigationButton(
            team = Team.TEAM_C,
            isSelected = selectedTeam == Team.TEAM_C,
            onTeamSelected = onTeamSelected
        )
    }
}

@Composable
fun TeamNavigationButton(
    team: Team,
    isSelected: Boolean,
    onTeamSelected: (Team) -> Unit
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Button(
            onClick = { onTeamSelected(team) },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        ) {
            // Display team name as button text
            Text(text = when (team) {
                Team.TEAM_A -> "Team A"
                Team.TEAM_B -> "Team B"
                Team.TEAM_C -> "Team C"
            })
        }
    }
}


@Composable
fun CTOProfileHeader(ctoName: String, setShowNotification: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp)
    ) {
        LazyColumn {
            item {
                // Button for Notifications
                Button(onClick = { setShowNotification(true) }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Icon
                    ProfileIcon(icon = Icons.Default.Person)

                    Spacer(modifier = Modifier.width(25.dp))

                    // CTO Details
                    Column {
                        Text(
                            text = ctoName,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "CTO",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // You can add additional items or details here as needed
        }
    }
}