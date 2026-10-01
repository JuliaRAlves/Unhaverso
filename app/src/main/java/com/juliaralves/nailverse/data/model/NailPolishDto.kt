package com.juliaralves.nailverse.data.model

import com.juliaralves.nailverse.domain.model.NailPolishTagEnum

data class NailPolishDto(
    val id: Long,
    val colorArgb: Int,
    val name: String,
    val brand: String,
    val tagList: List<NailPolishTagEnum>,
    val createdAt: Long
)