package com.juliaralves.nailverse.domain.model

import androidx.annotation.StringRes
import com.juliaralves.nailverse.R

enum class AppThemeEnum(@param:StringRes val textRes: Int) {
    DARK(R.string.settings_theme_dark),
    LIGHT(R.string.settings_theme_light),
    SYSTEM(R.string.settings_theme_system)
}