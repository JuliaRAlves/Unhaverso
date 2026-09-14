package com.juliaralves.unhaverso.data.repository

import com.juliaralves.unhaverso.data.local.datasource.AppLocalDataSource
import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.domain.repository.AppRepository
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