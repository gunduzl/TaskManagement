package com.example.taskmanager.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import com.example.taskmanager.components.pool.Pool

@Composable
fun HomeScreen(){

    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(top = 30.dp, start = 20.dp, end = 10.dp)
    ) {
        Button(onClick = { }) {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null )
        }
        Pool("Open", Color(0x666650a4), "Develop and admin panel",true)
        Pool("Active", Color(0x666790a4), "Develop and admin panel",true)
    }

}