package com.juliaralves.nailverse.data.model

import com.juliaralves.nailverse.domain.model.NailPolishTagEnum

data class InspirationPictureDto(
    val id: Long,
    val imageUrl: String,
    val query: String
)