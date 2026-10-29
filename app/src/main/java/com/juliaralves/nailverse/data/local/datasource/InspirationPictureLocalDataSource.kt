package com.juliaralves.nailverse.data.local.datasource

import com.juliaralves.nailverse.data.local.room.database.AppDatabase
import com.juliaralves.nailverse.data.local.room.entity.InspirationPicture
import com.juliaralves.nailverse.data.mapper.InspirationPictureDataMapper
import com.juliaralves.nailverse.data.model.InspirationPictureDto

class InspirationPictureLocalDataSource(
    private val database: AppDatabase,
    private val mapper: InspirationPictureDataMapper
) {
    suspend fun getInspirationPictureList(): List<InspirationPictureDto> {
        return getDao().getAll().map(mapper::mapInspirationPictureToDto)
    }

    suspend fun addInspirationPicture(id: Long, imageUrl: String, query: String) {
        val picture = InspirationPicture(
            uid = id,
            imageUrl = imageUrl,
            query = query
        )
        return getDao().insert(picture)
    }

    suspend fun removeAllInspirationPicture() {
        getDao().clear()
    }

    private fun getDao() = database.inspirationPictureDao()
}