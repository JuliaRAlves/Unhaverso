package com.juliaralves.unhaverso.data.local.datasource

import com.juliaralves.unhaverso.data.local.room.database.AppDatabase
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.data.model.NailPolishDto
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum

class NailPolishLocalDataSourceImpl(
    private val database: AppDatabase,
    private val mapper: NailPolishDataMapper
) : NailPolishLocalDataSource {
    override suspend fun getAllNailPolish(): List<NailPolishDto> {
        return getDao().getAll().map(mapper::mapNailPolishToDto)
    }

    override suspend fun getNailPolishByText(text: String): List<NailPolishDto> {
        return getDao().findByText(text).map(mapper::mapNailPolishToDto)
    }

    override suspend fun getNailPolishByTag(tag: NailPolishTagEnum): List<NailPolishDto> {
        return getDao().findByTag(tag.name).map(mapper::mapNailPolishToDto)
    }

    override suspend fun addNailPolish(nailPolish: NailPolishDto) {
        return getDao().insert(mapper.mapNailPolishDtoToEntity(nailPolish))
    }

    override suspend fun removeNailPolish(nailPolish: NailPolishDto) {
        return getDao().delete(mapper.mapNailPolishDtoToEntity(nailPolish))
    }

    override suspend fun removeAllNailPolish() {
        getDao().clear()
    }

    private fun getDao() = database.nailPolishDao()
}