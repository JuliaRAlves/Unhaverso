package com.juliaralves.unhaverso.presentation.core.components

import android.content.res.Configuration
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun BaseFilterChip(text: String, isSelected: Boolean, onSelect: () -> Unit) {
    FilterChip(
        selected = isSelected,
        onClick = onSelect,
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        colors = FilterChipDefaults.filterChipColors()
            .copy(
                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
        label = { Text(text) })
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BaseFilterChipSelectedDarkMode() {
    UnhaversoTheme {
        BaseFilterChip("Teste", true) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BaseFilterChipUnselectedDarkMode() {
    UnhaversoTheme {
        BaseFilterChip("Teste", false) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseFilterChipSelectedLightMode() {
    UnhaversoTheme {
        BaseFilterChip("Teste", true) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseFilterChipUnselectedLightMode() {
    UnhaversoTheme {
        BaseFilterChip("Teste", false) {

        }
    }
}