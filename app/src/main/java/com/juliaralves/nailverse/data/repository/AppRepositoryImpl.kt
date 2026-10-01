package com.juliaralves.nailverse.data.repository

import com.juliaralves.nailverse.data.local.datasource.AppLocalDataSource
import com.juliaralves.nailverse.domain.model.AppThemeEnum
import com.juliaralves.nailverse.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val localDataSource: AppLocalDataSource
) : AppRepository {
    override suspend fun updateTheme(themeEnum: AppThemeEnum) {
        localDataSource.updateTheme(themeEnum)
    }

    override fun observeTheme(): Flow<AppThemeEnum?> {
        return localDataSource.observeTheme()
    }

}