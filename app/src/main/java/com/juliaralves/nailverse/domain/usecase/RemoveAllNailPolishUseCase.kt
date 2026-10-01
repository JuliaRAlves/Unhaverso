package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.repository.NailPolishRepository

class RemoveAllNailPolishUseCase(private val repository: NailPolishRepository) {
    suspend fun execute() {
        repository.clearData()
    }
}