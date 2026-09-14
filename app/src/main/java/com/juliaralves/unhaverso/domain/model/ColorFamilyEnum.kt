package com.juliaralves.unhaverso.domain.model

import androidx.annotation.StringRes
import com.juliaralves.unhaverso.R

enum class ColorFamilyEnum(@param:StringRes val textRes: Int) {
    RED(R.string.nail_polish_box_color_family_red),
    ORANGE(R.string.nail_polish_box_color_family_orange),
    YELLOW(R.string.nail_polish_box_color_family_yellow),
    GREEN(R.string.nail_polish_box_color_family_green),
    CYAN(R.string.nail_polish_box_color_family_cyan),
    BLUE(R.string.nail_polish_box_color_family_blue),
    PURPLE(R.string.nail_polish_box_color_family_purple),
    PINK(R.string.nail_polish_box_color_family_pink),
    BROWN(R.string.nail_polish_box_color_family_brown),
    GRAY(R.string.nail_polish_box_color_family_gray),
    BLACK(R.string.nail_polish_box_color_family_black),
    WHITE(R.string.nail_polish_box_color_family_white)
}