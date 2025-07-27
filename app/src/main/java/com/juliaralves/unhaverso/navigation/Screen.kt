package com.juliaralves.unhaverso.navigation

sealed class Screen(val route: String) {
    object Inspiration : Screen("inspiration_screen")
    object NailPolishBox : Screen("nail_polish_box_screen")
    object Settings : Screen("settings_screen")
}