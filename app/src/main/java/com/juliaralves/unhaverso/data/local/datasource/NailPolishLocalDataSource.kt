package com.juliaralves.unhaverso.data.local.datasource

import com.juliaralves.unhaverso.data.local.room.database.AppDatabase
import com.juliaralves.unhaverso.data.local.room.entity.NailPolish
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.data.model.NailPolishDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class NailPolishLocalDataSource(
    private val database: AppDatabase,
    private val mapper: NailPolishDataMapper
) {
    fun getNailPolishFilteredBy(text: String?): Flow<List<NailPolishDto>> {
        return getDao().getFilteredBy(text).map { it.map(mapper::mapNailPolishToDto) }
    }

    suspend fun addNailPolish(
        colorArgb: Int,
        name: String,
        brand: String,
        tagList: String
    ) {
        val nailPolish = NailPolish(
            colorArgb = colorArgb,
            name = name,
            brand = brand,
            tagList = tagList,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
        return getDao().insert(nailPolish)
    }

    suspend fun removeNailPolish(id: Long) {
        return getDao().delete(id)
    }

    suspend fun removeAllNailPolish() {
        getDao().clear()
    }

    private fun getDao() = database.nailPolishDao()
}