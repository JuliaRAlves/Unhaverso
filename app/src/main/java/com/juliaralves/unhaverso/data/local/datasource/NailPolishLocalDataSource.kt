package com.juliaralves.unhaverso.data.local.datasource

import com.juliaralves.unhaverso.data.model.NailPolishDto

interface NailPolishLocalDataSource {
    suspend fun getNailPolishFilteredBy(text: String? = null): List<NailPolishDto>

    suspend fun addNailPolish(
        colorArgb: Int,
        name: String,
        brand: String,
        tagList: String
    )

    suspend fun removeNailPolish(id: Long)
    suspend fun removeAllNailPolish()

}