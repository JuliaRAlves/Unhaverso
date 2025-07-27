package com.juliaralves.unhaverso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme
import com.juliaralves.unhaverso.presentation.main.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            UnhaversoTheme {
                MainScreen()
            }
        }
    }
}