package com.juliaralves.unhaverso.domain.model

import androidx.annotation.StringRes
import com.juliaralves.unhaverso.R

enum class AppThemeEnum(@param:StringRes val textRes: Int) {
    DARK(R.string.settings_theme_dark),
    LIGHT(R.string.settings_theme_light),
    SYSTEM(R.string.settings_theme_system)
}