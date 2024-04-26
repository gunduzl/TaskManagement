package com.example.taskmanager.screens

import MyTeam_A
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp)) {
        // Navigation bar
        TeamNavigationBar(selectedTeam, setSelectedTeam)
        // Display team details based on the selected team
        when (selectedTeam) {
            Team.TEAM_A -> MyTeam_A()
            Team.TEAM_B -> MyTeam_B()
            Team.TEAM_C -> MyTeam_C()
        }
    }
}

@Composable
fun TeamNavigationBar(selectedTeam: Team, onTeamSelected: (Team) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
fun StaffItem(name: String, status: String, task: String?, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = statusColor.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
        )
        if (status == "Available") {
            Text(
                text = status,
                style = MaterialTheme.typography.headlineMedium,
                color = statusColor
            )
        } else {
            if (task != null) {
                Text(
                    text = task,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
fun TeamDetails(teamDetails: String) {
    Text(
        text = teamDetails,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 16.dp)
    )
}
