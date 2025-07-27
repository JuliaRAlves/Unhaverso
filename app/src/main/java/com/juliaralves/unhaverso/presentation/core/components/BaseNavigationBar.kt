package com.juliaralves.unhaverso.presentation.core.components

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
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun BaseNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(1)
    }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BaseNavigationBarDarkMode() {
    UnhaversoTheme {
        BaseNavigationBar(navController = NavController(LocalContext.current))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseNavigationBarLightMode() {
    UnhaversoTheme {
        BaseNavigationBar(navController = NavController(LocalContext.current))
    }
}