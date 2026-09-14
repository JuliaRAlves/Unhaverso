package com.juliaralves.unhaverso.presentation.nailpolishbox.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.domain.model.NailPolishGroupByEnum
import com.juliaralves.unhaverso.domain.model.NailPolishSortByEnum
import com.juliaralves.unhaverso.presentation.core.components.BaseTextField
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun NailPolishBoxOptionsBar(
    modifier: Modifier = Modifier,
    searchInput: String,
    onSearchInputChange: (String) -> Unit,
    onGroupClick: (NailPolishGroupByEnum) -> Unit,
    onSortClick: (NailPolishSortByEnum) -> Unit
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var isGroupExpanded by remember { mutableStateOf(false) }
    var isSortExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSearchExpanded) {
            BaseTextField(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f),
                title = stringResource(R.string.nail_polish_box_options_search),
                showClearIcon = true,
                onClearText = { isSearchExpanded = false },
                errorText = "",
                showError = false,
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                value = searchInput,
                onValueChange = onSearchInputChange
            )
        } else {
            Spacer(Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { isSearchExpanded = true }),
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Box(Modifier.padding(horizontal = 8.dp)) {
            Icon(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = { isGroupExpanded = true }),
                painter = painterResource(R.drawable.ic_group),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            DropdownMenu(isGroupExpanded, onDismissRequest = { isGroupExpanded = false }) {
                NailPolishGroupByEnum.entries.forEach { group ->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(group.textRes)) },
                        onClick = { onGroupClick(group) }
                    )
                }
            }
        }

        Box() {
            Icon(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = { isSortExpanded = true }),
                painter = painterResource(R.drawable.ic_sort),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            DropdownMenu(isSortExpanded, onDismissRequest = { isSortExpanded = false }) {
                NailPolishSortByEnum.entries.forEach { sort ->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(sort.textRes)) },
                        onClick = { onSortClick(sort) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NailPolishBoxOptionsBarDarkPreview() {
    UnhaversoTheme {
        NailPolishBoxOptionsBar(Modifier.fillMaxSize(), "", {}, {}, {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NailPolishBoxOptionsBarLightPreview() {
    UnhaversoTheme() {
        NailPolishBoxOptionsBar(Modifier.fillMaxSize(), "", {}, {}, {})
    }
}
