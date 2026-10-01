package com.juliaralves.nailverse.domain.model

import androidx.annotation.StringRes
import com.juliaralves.nailverse.R

enum class NailPolishGroupByEnum(@param:StringRes val textRes: Int) {
    COLOR(R.string.nail_polish_box_group_by_color),
    TAG(R.string.nail_polish_box_group_by_tag),
    NONE(R.string.nail_polish_box_group_by_none)
}