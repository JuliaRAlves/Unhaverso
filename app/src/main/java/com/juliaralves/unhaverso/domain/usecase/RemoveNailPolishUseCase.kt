package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.repository.NailPolishRepository

class RemoveNailPolishUseCase(private val repository: NailPolishRepository) {

    suspend fun execute(params: Params) {
        repository.deleteNailPolish(params.id)
    }

    data class Params(
        val id: Long
    )
}