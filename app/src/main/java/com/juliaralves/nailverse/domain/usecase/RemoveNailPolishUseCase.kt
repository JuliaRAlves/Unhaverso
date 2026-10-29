package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.repository.InspirationRepository
import com.juliaralves.nailverse.domain.repository.NailPolishRepository

class RemoveNailPolishUseCase(
    private val repository: NailPolishRepository,
    private val inspirationRepository: InspirationRepository
) {

    suspend fun execute(params: Params) {
        repository.deleteNailPolish(params.id)
        inspirationRepository.clearInspirationFeed()
    }

    data class Params(
        val id: Long
    )
}