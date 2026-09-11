package com.juliaralves.unhaverso.domain.model

data class NailPolishVO(
    val hexColor: Long,
    val name: String,
    val brand: String,
    val tagList: List<NailPolishTagEnum>,
    val createdAt: Long
)