package com.juliaralves.unhaverso.navigation

import androidx.collection.objectListOf
import com.juliaralves.unhaverso.R

val navigationItemList = objectListOf(
    BottomNavigationItem(
        titleRes = R.string.menu_nail_polish_box_title,
        iconRes = R.drawable.ic_nail_polish_box,
        route = Screen.NailPolishBox.route
    ),
    BottomNavigationItem(
        titleRes = R.string.menu_inspiration_title,
        iconRes = R.drawable.ic_inspiration,
        route = Screen.Inspiration.route
    ),
    BottomNavigationItem(
        titleRes = R.string.menu_settings_title,
        iconRes = R.drawable.ic_menu,
        route = Screen.Settings.route
    )
)