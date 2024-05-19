package com.example.taskmanager.presentation.components.pool

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.presentation.ui.theme.customPurple
import com.example.taskmanager.presentation.viewmodel.components.PoolViewModel

@Composable
fun Pool(name: String, rowColor: Color, isStaff: Boolean) {
    val viewModel: PoolViewModel = viewModel()
    val tasks by viewModel.tasks.observeAsState(emptyList())

    LaunchedEffect(name) {
        viewModel.loadTasksByStatus(name)
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .size(width = 800.dp, height = 280.dp)
                .padding(top = 10.dp, start = 20.dp, end = 10.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "$name Tasks", fontSize = 25.sp, fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .background(SolidColor(Color.White), shape = RoundedCornerShape(15.dp))
            ) {
                items(tasks) { task ->
                    TaskItem(
                        name = task.title,
                        description = task.description,
                        expectedFinishDate = task.deadline,
                        timeLeft = "8 days",  // Calculate this based on current date and task.deadline
                        difficulty = task.priority,
                        col = rowColor,
                        openOrNot = task.status,
                        isHelp = false,
                        isManager = !isStaff
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    name: String,
    description: String,
    expectedFinishDate: String,
    timeLeft: String,
    difficulty: String,
    col: Color,
    openOrNot: String,
    isHelp: Boolean,
    isManager: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(color = col, shape = RoundedCornerShape(15.dp))
            .border(2.dp, color = col, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(70.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isHelp) {
            Column {
                Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
                Row {
                    CustomButton(
                        text = "Confirm",
                        onClick = { /* Handle button click */ },
                        backgroundColor = Color(0x777730ff),
                        contentColor = Color.White,
                        cornerRadius = 15.dp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                    CustomButton(
                        text = "Reject",
                        onClick = { /* Handle button click */ },
                        backgroundColor = customPurple,  // 0x666650ff
                        contentColor = Color.White,
                        cornerRadius = 15.dp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        } else {
            Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(name, fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle(10)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row { Text("Description: ", fontWeight = FontWeight.Bold) }
                    Row { Text(description) }
                    Row { Text("Due Date:  ", fontWeight = FontWeight.Bold) }
                    Row { Text(expectedFinishDate) }
                    Row { Text("Time Left: ", fontWeight = FontWeight.Bold) }
                    Row { Text(timeLeft) }
                    Row { Text("Difficulty: ", fontWeight = FontWeight.Bold) }
                    Row { Text(difficulty) }
                }
            },
            confirmButton = {
                if (!isManager) {
                    Button(onClick = { showDialog = false }) {
                        Text("Take Task")
                    }
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    contentColor: Color = Color.White,
    cornerRadius: Dp = 15.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
