package com.juliaralves.unhaverso.presentation.core.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.presentation.core.theme.Transparent
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    title: String,
    showClearIcon: Boolean = false,
    onClearText: () -> Unit,
    showError: Boolean = false,
    errorText: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }
    TextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = { Text(title) },
        shape = RoundedCornerShape(size = 100.dp),
        singleLine = true,
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Transparent,
            errorIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedLabelColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledLabelColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondaryContainer,
            errorLabelColor = Color.Red,
            focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            errorTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
            focusedTrailingIconColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledTrailingIconColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondaryContainer,
            errorTrailingIconColor = Color.Red,
            errorSupportingTextColor = Color.Red,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            focusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface
        ),
        isError = showError,
        supportingText = {
            if (showError) {
                Text(errorText)
            }
        },
        trailingIcon = {
            if (showClearIcon) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = true, onClick = onClearText),
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BaseTextFieldDarkMode() {
    UnhaversoTheme {
        BaseTextField(Modifier, "Nome do esmalte", true, {}, true, "erro", "texto", {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun BaseTextFieldLightMode() {
    UnhaversoTheme {
        BaseTextField(Modifier, "Nome do esmalte", true, {}, true, "erro", "texto", {})
    }
}