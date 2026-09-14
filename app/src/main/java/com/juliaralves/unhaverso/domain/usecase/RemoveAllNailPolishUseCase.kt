package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.repository.NailPolishRepository

class RemoveAllNailPolishUseCase(private val repository: NailPolishRepository) {
    suspend fun execute() {
        repository.clearData()
    }
}