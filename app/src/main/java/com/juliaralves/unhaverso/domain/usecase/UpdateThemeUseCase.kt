package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.domain.repository.AppRepository

class UpdateThemeUseCase(private val appRepository: AppRepository) {
    suspend fun execute(params: Params) {
        appRepository.updateTheme(params.theme)
    }

    data class Params(
        val theme: AppThemeEnum
    )
}