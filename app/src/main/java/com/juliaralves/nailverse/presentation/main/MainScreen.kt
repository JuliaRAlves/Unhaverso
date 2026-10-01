package com.juliaralves.nailverse.presentation.main

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
import com.juliaralves.nailverse.navigation.Screen
import com.juliaralves.nailverse.presentation.core.components.BaseNavigationBar
import com.juliaralves.nailverse.presentation.core.components.BaseTopAppBar
import com.juliaralves.nailverse.presentation.inspiration.InspirationScreen
import com.juliaralves.nailverse.presentation.nailpolishbox.NailPolishBoxScreen
import com.juliaralves.nailverse.presentation.settings.SettingsScreen

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
            startDestination = Screen.NailPolishBox.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            if (Screen.Inspiration.isEnabled) {
                composable(route = Screen.Inspiration.route) {
                    InspirationScreen()
                }
            }
            if (Screen.NailPolishBox.isEnabled) {
                composable(route = Screen.NailPolishBox.route) {
                    NailPolishBoxScreen()
                }
            }
            if (Screen.Settings.isEnabled) {
                composable(route = Screen.Settings.route) {
                    SettingsScreen()
                }
            }
        }
    }
}