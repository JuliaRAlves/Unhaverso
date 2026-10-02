package com.juliaralves.nailverse.presentation.core.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.nailverse.R
import com.juliaralves.nailverse.presentation.core.theme.NailverseTheme

@Composable
fun BaseFilterChip(
    text: String,
    isSelected: Boolean,
    showClear: Boolean = false,
    onSelect: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onSelect,
        trailingIcon = {
            if (showClear) {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        },
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
    NailverseTheme {
        BaseFilterChip("Teste", true) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BaseFilterChipUnselectedDarkMode() {
    NailverseTheme {
        BaseFilterChip("Teste", false) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseFilterChipSelectedLightMode() {
    NailverseTheme {
        BaseFilterChip("Teste", true) {

        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BaseFilterChipUnselectedLightMode() {
    NailverseTheme {
        BaseFilterChip("Teste", false) {

        }
    }
}