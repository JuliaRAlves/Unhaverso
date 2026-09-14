package com.juliaralves.unhaverso.domain.repository

import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO

interface NailPolishRepository {
    suspend fun addNailPolish(
        colorArgb: Int,
        name: String,
        brand: String,
        tagList: List<NailPolishTagEnum>
    )

    suspend fun deleteNailPolish(id: Long)
    suspend fun getNailPolishFilteredBy(text: String? = null): List<NailPolishVO>

    suspend fun clearData()
}