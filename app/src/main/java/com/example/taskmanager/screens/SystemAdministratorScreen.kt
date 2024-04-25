package com.example.taskmanager.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.SolidColor

@Composable
fun SystemAdministratorScreen(){
  MaterialTheme{
      Column(
          modifier = Modifier
              .fillMaxHeight()
              .width(800.dp)
              .padding(top = 30.dp, start = 20.dp, end = 10.dp)
          ,
          horizontalAlignment = Alignment.Start,
      ){

          LazyColumn(
              modifier = Modifier
                  .padding(10.dp)
                  .background(SolidColor(Color.White), shape = RoundedCornerShape(15.dp))

          ) {
              item{
                  CreateEmployee()
                  Spacer(modifier = Modifier.height(20.dp))
                  RemoveEmployee()
                  Spacer(modifier = Modifier.height(20.dp))
                  ChangeEmployeeRole()
                  Spacer(modifier = Modifier.height(20.dp))
                  AddDepartment()
                  Spacer(modifier = Modifier.height(20.dp))
                  DeleteDepartment()



              }
          }
      }
  }
}

@Composable
fun CreateEmployee(){
    var showDialog by remember { mutableStateOf(false) }
    Row(
       modifier = Modifier
           .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
           .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
           .fillMaxWidth()
           .padding(10.dp)
           .height(60.dp)
           .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
        
    ){
        Text(text = "Create Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }
        
    }
}

@Composable
fun RemoveEmployee(){
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Remove Employee", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
}

@Composable
fun ChangeEmployeeRole(){
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Change Employee Role", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
}
@Composable
fun AddDepartment(){
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Add Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
}

@Composable
fun DeleteDepartment(){
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .border(2.dp, Color(0xFFF0F8FF), shape = RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp)
            .clickable { showDialog = true },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ){
        Text(text = "Delete Department", fontSize = 20.sp, textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

        }

    }
}

