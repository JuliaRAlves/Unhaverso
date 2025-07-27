package com.juliaralves.unhaverso.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juliaralves.unhaverso.navigation.Screen
import com.juliaralves.unhaverso.presentation.core.components.BaseNavigationBar
import com.juliaralves.unhaverso.presentation.core.components.BaseTopAppBar
import com.juliaralves.unhaverso.presentation.inspiration.InspirationScreen
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxScreen
import com.juliaralves.unhaverso.presentation.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BaseTopAppBar(scrollBehavior) },
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
            composable(route = Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}