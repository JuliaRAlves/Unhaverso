package com.juliaralves.nailverse.domain.usecase

import com.juliaralves.nailverse.domain.model.NailPolishTagEnum
import com.juliaralves.nailverse.domain.repository.NailPolishRepository
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class AddNailPolishUseCase(private val repository: NailPolishRepository) {

    suspend fun execute(params: Params) {
        repository.addNailPolish(
            colorArgb = params.colorArgb,
            name = params.name,
            brand = params.brand,
            tagList = params.tagMap.mapNotNull { if (it.value) it.key else null },
        )
    }

    data class Params(
        val id: Long? = null,
        val colorArgb: Int,
        val name: String,
        val brand: String,
        val tagMap: Map<NailPolishTagEnum, Boolean>
    )
}