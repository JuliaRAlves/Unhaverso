package com.juliaralves.unhaverso.presentation.core

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.juliaralves.unhaverso.navigation.navigationItemList
import com.juliaralves.unhaverso.presentation.inspiration.ui.theme.UnhaversoTheme

@Composable
fun BaseNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    UnhaversoTheme {
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
            navigationItemList.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedNavigationIndex.intValue == index,
                    onClick = {
                        selectedNavigationIndex.intValue = index
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = stringResource(item.titleRes)
                        )
                    }

                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BaseNavigationBarDarkMode() {
    BaseNavigationBar(navController = NavController(LocalContext.current))
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseNavigationBarLightMode() {
    BaseNavigationBar(navController = NavController(LocalContext.current))
}