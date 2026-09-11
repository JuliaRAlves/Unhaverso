package com.juliaralves.unhaverso.domain.model

data class NailPolishVO(
    val colorArgb: Int,
    val name: String,
    val brand: String,
    val tagList: List<NailPolishTagEnum>,
    val createdAt: Long
)