package com.juliaralves.unhaverso.domain.usecase

import com.juliaralves.unhaverso.domain.model.NailPolishGroupByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishSortByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository
import com.juliaralves.unhaverso.domain.utils.getColorFamilyByArgb

class GetNailPolishUseCase(private val repository: NailPolishRepository) {
    suspend fun execute(params: Params): Map<Int, List<NailPolishVO>> {
        val resultList = repository.getNailPolishFilteredBy(params.filterText)
        val sortedList = when (params.sortBy) {
            NailPolishSortByEnum.ALPHABETICAL -> resultList.sortedBy { it.name }
            NailPolishSortByEnum.ALPHABETICAL_REVERSE -> resultList.sortedByDescending { it.name }
            NailPolishSortByEnum.MOST_RECENT -> resultList.sortedByDescending { it.createdAt }
            NailPolishSortByEnum.LEAST_RECENT -> resultList.sortedBy { it.createdAt }
            null -> resultList
        }

        return when (params.groupBy) {
            NailPolishGroupByEnum.COLOR -> sortedList.groupBy { getColorFamilyByArgb(it.colorArgb).textRes }
            NailPolishGroupByEnum.TAG -> {
                NailPolishTagEnum.entries.mapNotNull { tag ->
                    val list = mutableListOf<NailPolishVO>()
                    list.addAll(sortedList.filter { it.tagList.contains(tag) })
                    if (list.isEmpty()) null else tag.textRes to list.toList()
                }.toMap()
            }

            NailPolishGroupByEnum.NONE, null -> mapOf(0 to sortedList)
        }

    }

    data class Params(
        val filterText: String? = null,
        val sortBy: NailPolishSortByEnum? = null,
        val groupBy: NailPolishGroupByEnum? = null
    )
}