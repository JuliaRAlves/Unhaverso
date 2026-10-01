package com.juliaralves.nailverse.data.local.datasource

import com.juliaralves.nailverse.data.local.datastore.ThemeDataStore
import com.juliaralves.nailverse.domain.model.AppThemeEnum
import kotlinx.coroutines.flow.Flow

class AppLocalDataSource(
    private val dataStore: ThemeDataStore
) {
    suspend fun updateTheme(themeEnum: AppThemeEnum) {
        dataStore.updateTheme(themeEnum)
    }

    fun observeTheme(): Flow<AppThemeEnum?> {
        return dataStore.observeTheme()
    }
}