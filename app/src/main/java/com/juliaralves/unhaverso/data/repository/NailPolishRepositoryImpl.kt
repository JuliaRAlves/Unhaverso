package com.juliaralves.unhaverso.data.repository

import com.juliaralves.unhaverso.data.local.datasource.NailPolishLocalDataSource
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository

class NailPolishRepositoryImpl(
    private val localDataSource: NailPolishLocalDataSource,
    private val mapper: NailPolishDataMapper
) : NailPolishRepository {
    override suspend fun addNailPolish(nailPolishVO: NailPolishVO) {
        localDataSource.addNailPolish(mapper.mapNailPolishVOToDto(nailPolishVO))
    }

    override suspend fun deleteNailPolish(nailPolishVO: NailPolishVO) {
        localDataSource.removeNailPolish(mapper.mapNailPolishVOToDto(nailPolishVO))
    }

    override suspend fun getAllNailPolish(): List<NailPolishVO> {
        return localDataSource.getAllNailPolish().map(mapper::mapNailPolishDtoToVo)
    }

    override suspend fun getNailPolishByText(text: String): List<NailPolishVO> {
        return localDataSource.getNailPolishByText(text).map(mapper::mapNailPolishDtoToVo)
    }

    override suspend fun getNailPolishByTag(tag: NailPolishTagEnum): List<NailPolishVO> {
        return localDataSource.getNailPolishByTag(tag).map(mapper::mapNailPolishDtoToVo)
    }

    override suspend fun clearData() {
        localDataSource.removeAllNailPolish()
    }
}