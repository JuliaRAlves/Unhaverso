package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.repository.NailPolishRepository

class RemoveNailPolishUseCase(private val repository: NailPolishRepository) {

    suspend fun execute(params: Params) {
        repository.deleteNailPolish(params.id)
    }

    data class Params(
        val id: Long
    )
}