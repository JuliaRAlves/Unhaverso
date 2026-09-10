package com.juliaralves.unhaverso.domain.model

import androidx.annotation.StringRes
import com.juliaralves.unhaverso.R

enum class NailPolishGroupByEnum(@param:StringRes val textRes: Int) {
    COLOR(R.string.nail_polish_box_group_by_color),
    TAG(R.string.nail_polish_box_group_by_tag),
    NONE(R.string.nail_polish_box_group_by_none)
}