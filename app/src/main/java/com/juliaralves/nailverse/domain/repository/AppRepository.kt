package com.juliaralves.nailverse.domain.repository

import com.juliaralves.nailverse.domain.model.AppThemeEnum
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun updateTheme(themeEnum: AppThemeEnum)
    fun observeTheme(): Flow<AppThemeEnum?>
}