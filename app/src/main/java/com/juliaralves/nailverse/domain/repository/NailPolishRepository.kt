package com.juliaralves.nailverse.domain.repository

import com.juliaralves.nailverse.domain.model.NailPolishTagEnum
import com.juliaralves.nailverse.domain.model.NailPolishVO
import kotlinx.coroutines.flow.Flow

interface NailPolishRepository {
    suspend fun addNailPolish(
        colorArgb: Int,
        name: String,
        brand: String,
        tagList: List<NailPolishTagEnum>
    )

    suspend fun deleteNailPolish(id: Long)
    fun getNailPolishFilteredBy(text: String? = null): Flow<List<NailPolishVO>>

    suspend fun clearData()
}