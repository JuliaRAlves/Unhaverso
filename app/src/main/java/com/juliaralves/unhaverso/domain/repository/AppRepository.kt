package com.juliaralves.unhaverso.domain.repository

import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun updateTheme(themeEnum: AppThemeEnum)
    fun observeTheme(): Flow<AppThemeEnum?>
}