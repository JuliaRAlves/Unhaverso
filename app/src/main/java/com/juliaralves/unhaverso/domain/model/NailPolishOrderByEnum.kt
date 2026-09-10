package com.juliaralves.unhaverso.domain.model

import androidx.annotation.StringRes
import com.juliaralves.unhaverso.R

enum class NailPolishOrderByEnum(@param:StringRes val textRes: Int) {
    ALPHABETICAL(R.string.nail_polish_box_order_by_alphabetical),
    ALPHABETICAL_REVERSE(R.string.nail_polish_box_order_by_alphabetical_reverse),
    MOST_RECENT(R.string.nail_polish_box_order_by_most_recent),
    LEAST_RECENT(R.string.nail_polish_box_order_by_least_recent)
}