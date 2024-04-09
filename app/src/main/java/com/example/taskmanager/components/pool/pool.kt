package com.example.taskmanager.components.pool

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun Pool(name: String, rowcolor: Color){
    MaterialTheme {
        Column(
            modifier = Modifier
                .size(width = 800.dp, height = 280.dp)
                .padding(top = 30.dp, start = 20.dp, end = 10.dp)
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
                    Task(name = "elif", col = rowcolor, name)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }


}

@Composable
fun Task(name: String, col: Color, openornot: String){
    Row(
        modifier = Modifier
            .background(color = col, shape = RoundedCornerShape(15.dp))
            .border(2.dp, color = col, shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable {  },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically


    ){
        Text(text = "Text $name", fontSize = 20.sp, textAlign = TextAlign.Left , fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),

            contentAlignment = Alignment.BottomEnd
        ) {
            if(openornot == "Open"){
                Button(
                    onClick = { /* Handle button click */ },

                    ) {
                    Icon(imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)  )
                }

            }

        }

    }
}