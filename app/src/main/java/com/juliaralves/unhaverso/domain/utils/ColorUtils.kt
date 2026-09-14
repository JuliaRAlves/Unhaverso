package com.juliaralves.unhaverso.domain.utils

import androidx.core.graphics.ColorUtils
import com.juliaralves.unhaverso.domain.model.ColorFamilyEnum

fun getColorFamilyByArgb(colorArgb: Int): ColorFamilyEnum {
    val hsl = FloatArray(3)
    ColorUtils.colorToHSL(colorArgb, hsl)

    val hue = hsl[0]
    val saturation = hsl[1]
    val lightness = hsl[2]

    if (lightness < 0.08f) return ColorFamilyEnum.BLACK
    if (lightness > 0.92f && saturation < 0.15f) return ColorFamilyEnum.WHITE
    if (saturation < 0.12f) return ColorFamilyEnum.GRAY

    return when (hue) {
        in 0f..<15f,
        in 345f..<360f -> ColorFamilyEnum.RED

        in 15f..<45f -> {
            if (lightness < 0.45f) ColorFamilyEnum.BROWN
            else ColorFamilyEnum.ORANGE
        }

        in 45f..<70f -> ColorFamilyEnum.YELLOW
        in 70f..<165f -> ColorFamilyEnum.GREEN
        in 165f..<195f -> ColorFamilyEnum.CYAN
        in 195f..<255f -> ColorFamilyEnum.BLUE
        in 255f..<290f -> ColorFamilyEnum.PURPLE
        in 290f..<345f -> ColorFamilyEnum.PINK

        else -> ColorFamilyEnum.RED
    }
}