package com.juliaralves.nailverse.navigation

data class BottomNavigationItem(
    val titleRes: Int,
    val iconRes: Int,
    val route: String,
    val isEnabled: Boolean = true
)