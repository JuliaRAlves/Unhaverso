package com.juliaralves.unhaverso.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juliaralves.unhaverso.navigation.Screen
import com.juliaralves.unhaverso.presentation.core.BaseNavigationBar
import com.juliaralves.unhaverso.presentation.core.BaseTopAppBar
import com.juliaralves.unhaverso.presentation.inspiration.InspirationScreen
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { BaseTopAppBar() },
        bottomBar = { BaseNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Inspiration.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Inspiration.route) {
                InspirationScreen()
            }
            composable(route = Screen.NailPolishBox.route) {
                NailPolishBoxScreen()
            }
        }
    }
}