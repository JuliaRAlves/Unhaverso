package com.juliaralves.unhaverso.data.repository

import com.juliaralves.unhaverso.data.local.datasource.NailPolishLocalDataSource
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.domain.model.NailPolishVO
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getNailPolishFilteredBy(text: String?): Flow<List<NailPolishVO>> {
        return localDataSource.getNailPolishFilteredBy(text)
            .map { it.map(mapper::mapNailPolishDtoToVo) }
    }

    override suspend fun clearData() {
        localDataSource.removeAllNailPolish()
    }
}