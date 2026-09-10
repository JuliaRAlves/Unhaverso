package com.juliaralves.unhaverso.domain.model

import androidx.annotation.StringRes
import com.juliaralves.unhaverso.R

enum class NailPolishTagEnum(@param:StringRes val textRes: Int) {
    HYPOALLERGENIC(R.string.nail_polish_box_tag_hypoallergenic),
    CREAMY(R.string.nail_polish_box_tag_creamy),
    SPARKLING(R.string.nail_polish_box_tag_sparkling),
    METALLIC(R.string.nail_polish_box_tag_metallic),
    MATTE(R.string.nail_polish_box_tag_matte),
    GLITTER(R.string.nail_polish_box_tag_glitter),
    HOLOGRAPHIC(R.string.nail_polish_box_tag_holographic),
    PEARLY(R.string.nail_polish_box_tag_pearly),
    NEON(R.string.nail_polish_box_tag_neon),
    GEL(R.string.nail_polish_box_tag_gel),
    ACRYLIC(R.string.nail_polish_box_tag_acrylic),
    THERMAL(R.string.nail_polish_box_tag_thermal)
}