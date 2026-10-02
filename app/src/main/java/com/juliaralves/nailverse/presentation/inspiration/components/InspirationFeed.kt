package com.juliaralves.nailverse.presentation.inspiration.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.juliaralves.nailverse.domain.model.InspirationPictureVO
import com.juliaralves.nailverse.presentation.core.components.BaseFilterChip
import com.juliaralves.nailverse.presentation.inspiration.InspirationViewModel

@Composable
fun InspirationFeed(
    viewModel: InspirationViewModel,
    inspirationList: List<InspirationPictureVO>,
    filterTagsResourceText: List<Int>,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (filterTagsResourceText.isNotEmpty()) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filterTagsResourceText.forEach { tagRes ->
                        BaseFilterChip(
                            stringResource(tagRes),
                            isSelected = true,
                            showClear = true,
                        ) { viewModel.onFilterUnselected(tagRes) }
                    }
                }
            }
        }
        items(inspirationList) {
            GridItem(item = it)
        }
    }
}