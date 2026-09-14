package com.juliaralves.unhaverso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme
import com.juliaralves.unhaverso.presentation.main.MainScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val theme = viewModel.theme.collectAsState()
            val isDarkTheme = when (theme.value) {
                AppThemeEnum.DARK -> true
                AppThemeEnum.LIGHT -> false
                AppThemeEnum.SYSTEM -> isSystemInDarkTheme()
            }

            UnhaversoTheme(darkTheme = isDarkTheme) {
                MainScreen()
            }
        }
    }
}