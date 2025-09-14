package com.juliaralves.unhaverso.navigation

sealed class Screen(val route: String, val isEnabled: Boolean = true) {
    object Inspiration : Screen("inspiration_screen", isEnabled = false)
    object NailPolishBox : Screen("nail_polish_box_screen")
    object Settings : Screen("settings_screen")
}