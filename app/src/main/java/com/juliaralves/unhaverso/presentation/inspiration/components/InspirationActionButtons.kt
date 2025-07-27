package com.juliaralves.unhaverso.presentation.inspiration.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun InspirationActionButtons(showUp: Boolean = false, isFilterSelected: Boolean = false) {
    Column {
        FloatingActionButton(
            onClick = { },
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            containerColor = if (isFilterSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filtrar",
                tint = if (isFilterSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
        }
        if (showUp) {
            FloatingActionButton(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { },
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_up), "Voltar para o topo")
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FilterNotSelectedDarkMode() {
    UnhaversoTheme {
        InspirationActionButtons(showUp = true, isFilterSelected = false)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FilterNotSelectedLightMode() {
    UnhaversoTheme {
        InspirationActionButtons(showUp = true, isFilterSelected = false)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FilterSelectedDarkMode() {
    UnhaversoTheme {
        InspirationActionButtons(showUp = true, isFilterSelected = true)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun FilterSelectedLightMode() {
    UnhaversoTheme {
        InspirationActionButtons(showUp = true, isFilterSelected = true)
    }
}