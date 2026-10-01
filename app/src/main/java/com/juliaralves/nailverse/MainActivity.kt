package com.juliaralves.nailverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import com.juliaralves.nailverse.domain.model.AppThemeEnum
import com.juliaralves.nailverse.presentation.core.theme.NailverseTheme
import com.juliaralves.nailverse.presentation.main.MainScreen
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

            NailverseTheme(darkTheme = isDarkTheme) {
                MainScreen()
            }
        }
    }
}