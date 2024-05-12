package com.example.taskmanager.presentation.components.pool

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.presentation.ui.theme.customGreen


@Composable
fun Pool(name: String, rowcolor: Color, text:String, isStaff: Boolean){
    MaterialTheme {
        Column(
            modifier = Modifier
                .size(width = 800.dp, height = 280.dp)
                .padding(top = 10.dp, start = 20.dp, end = 10.dp)
            ,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "$name Tasks", fontSize = 25.sp, fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
                    .background(SolidColor(Color.White), shape = RoundedCornerShape(15.dp))
                //.size(width = 800.dp, height = 250.dp)
            ) {
                items(10) { index ->

                    if((name == "Active") && (isStaff == false) ){
                        if(index < 3){
                            Task(name = text,
                                description = "Develop an admin panel by using Jetpack Compose via Android Studio",
                                expectedFinishDate = "5 May 2024",
                                timeLeft = "8 days",
                                difficulty = "Hard",
                                col = rowcolor,
                                openornot = name ,
                                isHelp = true)
                        }
                        else{
                            Task(name = text,
                                description = "Develop an admin panel by using Jetpack Compose via Android Studio",
                                expectedFinishDate = "5 May 2024",
                                timeLeft = "8 days",
                                difficulty = "Hard",
                                col = rowcolor,
                                openornot = name ,
                                isHelp = false)
                        }
                    }
                    else{
                        Task(name = text,
                            description = "Develop an admin panel by using Jetpack Compose via Android Studio",
                            expectedFinishDate = "5 May 2024",
                            timeLeft = "8 days",
                            difficulty = "Hard",
                            col = rowcolor,
                            openornot = name ,
                            isHelp = false)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }


}


@Composable
fun Task(name: String, description: String, expectedFinishDate: String, timeLeft: String, difficulty: String, col: Color, openornot: String, isHelp: Boolean){
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(color = col, shape = RoundedCornerShape(15.dp))
            .border(2.dp, color = col, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically


    ){

        if (isHelp == true){
            Column{
                Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Left , fontWeight = FontWeight.Bold)
                Row {
                    Button(
                        onClick = { },
                    ) {
                        Text("Confirm")
                    }
                    Button(
                        onClick = { },
                    ) {
                        Text("Reject")
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),

                contentAlignment = Alignment.BottomEnd
            ) {


            }
        }
        else{
            Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Left , fontWeight = FontWeight.Bold)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),

                contentAlignment = Alignment.BottomEnd
            ) {


            }


        }


    }
    if (showDialog && openornot == "Open") {
        AlertDialog(

            onDismissRequest = { showDialog = false },
            title = { Text(name, fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle(10))},
            text = {

               Column(
                   verticalArrangement = Arrangement.spacedBy(10.dp)
               ){
                   Row{
                       Text("Description: ", fontWeight = FontWeight.Bold )
                   }
                   Row{
                       Text("$description ")
                   }
                   Row{
                       Text("Due Date:  ", fontWeight = FontWeight.Bold)
                       Text("$expectedFinishDate ")
                   }
                   Row{
                       Text("Time Left: ", fontWeight = FontWeight.Bold)
                       Text("$timeLeft ")
                   }
                   Row {
                       Text("Difficulty: ", fontWeight = FontWeight.Bold)
                       Text(difficulty)
                   }

               }


                },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                ) {
                    Text("Take Task")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    if (showDialog && openornot == "Active") {
        AlertDialog(

            onDismissRequest = { showDialog = false },
            title = { Text(name, fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle(10))},
            text = {

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Row{
                        Text("Description: ", fontWeight = FontWeight.Bold )
                    }
                    Row{
                        Text("$description ")
                    }
                    Row{
                        Text("Due Date:  ", fontWeight = FontWeight.Bold)
                        Text("$expectedFinishDate ")
                    }
                    Row{
                        Text("Time Left: ", fontWeight = FontWeight.Bold)
                        Text("$timeLeft ")
                    }
                    Row{
                        Text("Difficulty: ", fontWeight = FontWeight.Bold)
                        Text(difficulty)
                    }
                    Row{
                        Text("Staff doing this Task: ",fontWeight = FontWeight.Bold)
                    }
                    Row{
                        Text("Alper Turunç -  Elif Çolak - Gündüz")
                    }

                }


            },
            confirmButton = {
                customGreen
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                ) {
                    Text("Close")
                }
            }
        )
    }

}