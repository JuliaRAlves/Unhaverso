package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.model.AppThemeEnum
import com.juliaralves.nailverse.domain.repository.AppRepository

class UpdateThemeUseCase(private val appRepository: AppRepository) {
    suspend fun execute(params: Params) {
        appRepository.updateTheme(params.theme)
    }

    data class Params(
        val theme: AppThemeEnum
    )
}