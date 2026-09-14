package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveThemeUseCase(private val appRepository: AppRepository) {
    fun execute(): Flow<AppThemeEnum> {
        return appRepository.observeTheme().map { it ?: AppThemeEnum.SYSTEM }
    }
}