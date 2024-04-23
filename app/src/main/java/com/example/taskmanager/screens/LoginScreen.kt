package com.example.taskmanager.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmanager.components.navigation.AppNavigation
import com.example.taskmanager.components.navigation.AppNavigation


@Composable
fun LoginScreen(navControl: NavController){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier= Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login to your account", fontSize = 24.sp ,fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value =email ,
            onValueChange = {email = it},
            label = {
            Text(text = "Email Address")
        })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value =password ,
            onValueChange = {password = it},
            label = {
            Text(text = "Password")
        })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navControl.navigate(route = "/app-navigation")

        }) {
            Text(text = "Login ")

        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { }) {
            Text(text = "Forgot Password?")
        }
    }

}