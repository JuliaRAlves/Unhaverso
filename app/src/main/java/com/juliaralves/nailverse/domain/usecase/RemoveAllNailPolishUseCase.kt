package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.repository.InspirationRepository
import com.juliaralves.nailverse.domain.repository.NailPolishRepository

class RemoveAllNailPolishUseCase(
    private val repository: NailPolishRepository,
    private val inspirationRepository: InspirationRepository
) {
    suspend fun execute() {
        repository.clearData()
        inspirationRepository.clearInspirationFeed()
    }
}