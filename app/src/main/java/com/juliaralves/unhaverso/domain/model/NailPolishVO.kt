package com.juliaralves.unhaverso.domain.model

data class NailPolishVO(
    val hexColor: String,
    val name: String,
    val brand: String,
    val tagList: List<NailPolishTagEnum>
)