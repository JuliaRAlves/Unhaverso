package com.juliaralves.nailverse.domain.model

import androidx.annotation.StringRes
import com.juliaralves.nailverse.R

enum class NailPolishTagEnum(@param:StringRes val textRes: Int, val isFilterable: Boolean) {
    HYPOALLERGENIC(R.string.nail_polish_box_tag_hypoallergenic, isFilterable = false),
    CREAMY(R.string.nail_polish_box_tag_creamy, isFilterable = false),
    SPARKLING(R.string.nail_polish_box_tag_sparkling, isFilterable = true),
    METALLIC(R.string.nail_polish_box_tag_metallic, isFilterable = true),
    MATTE(R.string.nail_polish_box_tag_matte, isFilterable = true),
    GLITTER(R.string.nail_polish_box_tag_glitter, isFilterable = true),
    HOLOGRAPHIC(R.string.nail_polish_box_tag_holographic, isFilterable = true),
    PEARLY(R.string.nail_polish_box_tag_pearly, isFilterable = true),
    NEON(R.string.nail_polish_box_tag_neon, isFilterable = true),
    GEL(R.string.nail_polish_box_tag_gel, isFilterable = true),
    ACRYLIC(R.string.nail_polish_box_tag_acrylic, isFilterable = false),
    THERMAL(R.string.nail_polish_box_tag_thermal, isFilterable = false)
}