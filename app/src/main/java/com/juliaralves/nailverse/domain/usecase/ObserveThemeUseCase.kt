package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.model.AppThemeEnum
import com.juliaralves.nailverse.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveThemeUseCase(private val appRepository: AppRepository) {
    fun execute(): Flow<AppThemeEnum> {
        return appRepository.observeTheme().map { it ?: AppThemeEnum.SYSTEM }
    }
}