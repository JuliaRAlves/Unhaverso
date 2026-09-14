package com.juliaralves.unhaverso.data.local.datasource

import com.juliaralves.unhaverso.data.local.room.database.AppDatabase
import com.juliaralves.unhaverso.data.local.room.entity.NailPolish
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.data.model.NailPolishDto
import kotlin.time.Clock

class NailPolishLocalDataSourceImpl(
    private val database: AppDatabase,
    private val mapper: NailPolishDataMapper
) : NailPolishLocalDataSource {
    override suspend fun getNailPolishFilteredBy(text: String?): List<NailPolishDto> {
        return getDao().getFilteredBy(text).map(mapper::mapNailPolishToDto)
    }

    override suspend fun addNailPolish(
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

    override suspend fun removeNailPolish(id: Long) {
        return getDao().delete(id)
    }

    override suspend fun removeAllNailPolish() {
        getDao().clear()
    }

    private fun getDao() = database.nailPolishDao()
}