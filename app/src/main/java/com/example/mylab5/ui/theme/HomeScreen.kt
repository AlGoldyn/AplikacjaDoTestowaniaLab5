package com.example.mylab5.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.personapp.ui.Screen

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Button(onClick = { onNavigate(Screen.Add.route) }, modifier = Modifier.fillMaxWidth()) {
            Text("Dodaj osobę")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { onNavigate(Screen.List.route) }, modifier = Modifier.fillMaxWidth()) {
            Text("Wyświetl listę danych")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { onNavigate(Screen.Delete.route) }, modifier = Modifier.fillMaxWidth()) {
            Text("Usuń osobę")
        }
    }
}
