package com.example.taskmanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.profileComponents.out.Staff

//data class Staff(val name: String, val score: Int)

@Composable
fun Leaderboard(leaderboard: List<Staff>) {
    val sortedLeaderboard = leaderboard.sortedByDescending { it.staffPoint }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            LeaderboardHeader()
            Spacer(modifier = Modifier.height(20.dp))
            LeaderboardContent(sortedLeaderboard)
        }
    }
}
@Composable
fun LeaderboardContent(sortedLeaderboard: List<Staff>) {
    /*
    sortedLeaderboard.forEachIndexed { index, staff ->
        LeaderboardEntry(index + 1, staff.name, staff.staffPoint)
        Spacer(modifier = Modifier.height(10.dp))
    }
    */
    var index = 0
    for(staff in sortedLeaderboard){
        if(index < 5){
            LeaderboardEntry(index + 1, staff.name, staff.staffPoint)
            Spacer(modifier = Modifier.height(10.dp))
            index++
        }

    }
}
@Composable
fun LeaderboardHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Rank", fontWeight = FontWeight.Bold)
        Text(text = "Name", fontWeight = FontWeight.Bold)
        Text(text = "Score", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LeaderboardEntry(rank:Int,name: String, score: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFFD0BCFF), shape = RoundedCornerShape(15.dp))
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
            .height(60.dp)
            .clickable { },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$rank",
            fontSize = 20.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$name",
            fontSize = 20.sp,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$score",
            fontSize = 20.sp,
            textAlign = TextAlign.Right,
            fontWeight = FontWeight.Bold
        )


    }
}