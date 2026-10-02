package com.juliaralves.nailverse.presentation.inspiration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juliaralves.nailverse.R
import com.juliaralves.nailverse.domain.model.ColorFamilyEnum
import com.juliaralves.nailverse.domain.model.NailPolishTagEnum
import com.juliaralves.nailverse.presentation.core.components.BaseFilterChip
import com.juliaralves.nailverse.presentation.core.components.BaseSecondaryButton

@Composable
fun ColumnScope.InspirationFilterBottomSheet(
    colorMap: Map<ColorFamilyEnum, Boolean>,
    tagMap: Map<NailPolishTagEnum, Boolean>,
    onColorClick: (ColorFamilyEnum) -> Unit,
    onTagClick: (NailPolishTagEnum) -> Unit,
    onSecondaryButtonClick: () -> Unit
) {
    Column(
        Modifier
            .weight(1f, fill = false)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.inspiration_filter_title),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.inspiration_filter_color),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        FlowRow(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            colorMap.forEach { (color, isSelected) ->
                BaseFilterChip(stringResource(color.textRes), isSelected) { onColorClick(color) }
            }
        }

        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = stringResource(id = R.string.inspiration_filter_tag),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        FlowRow(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            tagMap.forEach { (tag, isSelected) ->
                BaseFilterChip(stringResource(tag.textRes), isSelected) { onTagClick(tag) }
            }
        }
    }

    BaseSecondaryButton(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.inspiration_filter_clear),
        onClick = onSecondaryButtonClick
    )
}