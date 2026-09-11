package com.juliaralves.unhaverso.presentation.nailpolishbox.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.domain.model.NailPolishTagEnum
import com.juliaralves.unhaverso.presentation.core.components.BaseFilterChip
import com.juliaralves.unhaverso.presentation.core.components.BasePrimaryButton
import com.juliaralves.unhaverso.presentation.core.components.BaseSecondaryButton
import com.juliaralves.unhaverso.presentation.core.components.BaseTextField
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun ColumnScope.AddNailPolishBottomSheet(
    selectedColor: Color,
    onEditColorClick: () -> Unit,
    nameInputText: String,
    onNameInputTextChange: (String) -> Unit,
    onClearNameInput: () -> Unit,
    showNameInputError: Boolean,
    brandInputText: String,
    onBrandInputTextChange: (String) -> Unit,
    showBrandInputError: Boolean,
    onClearBrandInput: () -> Unit,
    tagMap: Map<NailPolishTagEnum, Boolean>,
    onTagClick: (NailPolishTagEnum) -> Unit,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    isButtonEnabled: Boolean
) {
    Text(
        text = stringResource(id = R.string.add_nail_polish_title),
        modifier = Modifier
            .padding(vertical = 24.dp)
            .align(Alignment.CenterHorizontally),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 8.dp))
                .clickable(enabled = true, onClick = onEditColorClick)
                .background(color = selectedColor)
                .size(120.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 8.dp))
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .size(34.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Column(Modifier.padding(start = 16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            BaseTextField(
                title = stringResource(R.string.add_nail_polish_text_field_name_title),
                onClearText = onClearNameInput,
                showError = showNameInputError,
                errorText = stringResource(R.string.add_nail_polish_text_field_name_empty),
                value = nameInputText,
                onValueChange = onNameInputTextChange
            )

            BaseTextField(
                modifier = Modifier.padding(top = 8.dp),
                title = stringResource(R.string.add_nail_polish_text_field_brand_title),
                onClearText = onClearBrandInput,
                showError = showBrandInputError,
                errorText = stringResource(R.string.add_nail_polish_text_field_brand_empty),
                value = brandInputText,
                onValueChange = onBrandInputTextChange
            )
        }


    }

    FlowRow(
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        tagMap.forEach { (tag, isSelected) ->
            BaseFilterChip(stringResource(tag.textRes), isSelected) { onTagClick(tag) }
        }
    }

    BaseSecondaryButton(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.add_nail_polish_cancel_button),
        onClick = onSecondaryButtonClick
    )
    BasePrimaryButton(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.add_nail_polish_confirm_button),
        onClick = onPrimaryButtonClick,
        isEnabled = isButtonEnabled
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddNailPolishBottomSheetPreview() {
    UnhaversoTheme {
        Column {
            AddNailPolishBottomSheet(
                selectedColor = Color(137, 88, 38),
                onEditColorClick = {},
                tagMap = NailPolishTagEnum.entries.associateWith { false },
                onTagClick = {},
                onPrimaryButtonClick = {},
                onSecondaryButtonClick = {},
                nameInputText = "Teste",
                onNameInputTextChange = {},
                onClearNameInput = {},
                brandInputText = "Teste",
                onBrandInputTextChange = {},
                onClearBrandInput = {},
                showNameInputError = false,
                showBrandInputError = false,
                isButtonEnabled = true
            )
        }
    }
}