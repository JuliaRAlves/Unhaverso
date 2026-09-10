package com.juliaralves.unhaverso.data.local.datasource

import com.juliaralves.unhaverso.data.model.NailPolishDto
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum

interface NailPolishLocalDataSource {
    suspend fun getAllNailPolish(): List<NailPolishDto>
    suspend fun getNailPolishByText(text: String): List<NailPolishDto>
    suspend fun getNailPolishByTag(tag: NailPolishTagEnum): List<NailPolishDto>
    suspend fun addNailPolish(nailPolish: NailPolishDto)
    suspend fun removeNailPolish(nailPolish: NailPolishDto)
    suspend fun removeAllNailPolish()
}