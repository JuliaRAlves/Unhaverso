package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class AddNailPolishUseCase(private val repository: NailPolishRepository) {

    suspend fun execute(params: Params) {
        val nailPolishVO = NailPolishVO(
            colorArgb = params.colorArgb,
            name = params.name,
            brand = params.brand,
            tagList = params.tagMap.mapNotNull { if (it.value) it.key else null },
            createdAt = Clock.System.now().toEpochMilliseconds()
        )

        repository.addNailPolish(nailPolishVO)
    }

    data class Params(
        val colorArgb: Int,
        val name: String,
        val brand: String,
        val tagMap: Map<NailPolishTagEnum, Boolean>
    )
}