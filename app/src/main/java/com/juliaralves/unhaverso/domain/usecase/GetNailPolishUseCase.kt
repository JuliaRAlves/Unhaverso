package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.model.NailPolishGroupByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishOrderByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository

class GetNailPolishUseCase(private val repository: NailPolishRepository) {
    suspend fun execute(params: Params): Map<String, List<NailPolishVO>> {
        // TODO: add filter logic
        return emptyMap()
    }

    data class Params(
        val filterText: String? = null,
        val filterTag: NailPolishTagEnum? = null,
        val orderBy: NailPolishOrderByEnum? = null,
        val groupBy: NailPolishGroupByEnum? = null
    )
}