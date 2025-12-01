package com.example.mylab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.mylab5.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = PersonDatabase.getDatabase(this)

        setContent {
            val nav = rememberNavController()

            val refreshTrigger = remember { mutableStateOf(false) }

            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = false,
                            onClick = { nav.navigate(Screen.Add.route) },
                            label = { Text("Dodaj") },
                            icon = {}
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { nav.navigate(Screen.List.route) },
                            label = { Text("Lista") },
                            icon = {}
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { nav.navigate(Screen.Delete.route) },
                            label = { Text("Usuń") },
                            icon = {}
                        )
                    }
                }
            ) { padding ->

                NavHost(
                    navController = nav,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(padding)
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen { route -> nav.navigate(route) }
                    }
                    composable(Screen.Add.route) {
                        AddPersonScreen(db) {
                            nav.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                            refreshTrigger.value = !refreshTrigger.value
                        }
                    }
                    composable(Screen.List.route) {
                        ListPersonScreen(db, refreshTrigger) {
                            nav.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                    }
                    composable(Screen.Delete.route) {
                        DeletePersonScreen(db, refreshTrigger) {
                            nav.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                    }
                }
            }
        }
    }
}
