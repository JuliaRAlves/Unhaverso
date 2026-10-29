package com.juliaralves.nailverse.domain.repository

import com.juliaralves.nailverse.domain.model.InspirationPictureVO

interface InspirationRepository {
    suspend fun getInspirationPicturesFilteredBy(query: String, isFiltered: Boolean): List<InspirationPictureVO>
    suspend fun clearInspirationFeed()
}