package com.juliaralves.unhaverso.data.model

import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum

data class NailPolishDto(
    val hexColor: Long,
    val name: String,
    val brand: String,
    val tagList: List<NailPolishTagEnum>,
    val createdAt: Long
)