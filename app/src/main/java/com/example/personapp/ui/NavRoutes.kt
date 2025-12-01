package com.example.personapp.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object List : Screen("list")
    object Delete : Screen("delete")
}
