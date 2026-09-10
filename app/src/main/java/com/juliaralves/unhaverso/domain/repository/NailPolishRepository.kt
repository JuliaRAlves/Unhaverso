package com.juliaralves.unhaverso.domain.repository

import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO

interface NailPolishRepository {
    suspend fun addNailPolish(nailPolishVO: NailPolishVO)
    suspend fun deleteNailPolish(nailPolishVO: NailPolishVO)
    suspend fun getAllNailPolish(): List<NailPolishVO>
    suspend fun getNailPolishByText(text: String): List<NailPolishVO>
    suspend fun getNailPolishByTag(tag: NailPolishTagEnum): List<NailPolishVO>
    suspend fun clearData()
}