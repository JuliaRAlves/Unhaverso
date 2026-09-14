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
    override suspend fun addNailPolish(
        colorArgb: Int,
        name: String,
        brand: String,
        tagList: List<NailPolishTagEnum>
    ) {
        localDataSource.addNailPolish(colorArgb, name, brand, mapper.formatTagListAsString(tagList))
    }

    override suspend fun deleteNailPolish(id: Long) {
        localDataSource.removeNailPolish(id)
    }

    override suspend fun getNailPolishFilteredBy(text: String?): List<NailPolishVO> {
        return localDataSource.getNailPolishFilteredBy(text).map(mapper::mapNailPolishDtoToVo)
    }

    override suspend fun clearData() {
        localDataSource.removeAllNailPolish()
    }
}