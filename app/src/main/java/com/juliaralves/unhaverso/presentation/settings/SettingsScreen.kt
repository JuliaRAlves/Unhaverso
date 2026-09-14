package com.juliaralves.unhaverso.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.juliaralves.unhaverso.R
import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.presentation.core.components.BasePrimaryButton
import com.juliaralves.unhaverso.presentation.core.components.BaseSecondaryButton
import com.juliaralves.unhaverso.presentation.core.theme.UnhaversoTheme
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.HideClearConfirmation
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.HideThemePicker
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.ShowClearConfirmation
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.ShowThemePicker
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
    val state by viewModel.screenState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    var showConfirmation by remember { mutableStateOf(false) }
    var showThemePicker by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.screenEffect) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.screenEffect.collect {
                when (it) {
                    HideClearConfirmation -> showConfirmation = false
                    HideThemePicker -> showThemePicker = false
                    ShowClearConfirmation -> showConfirmation = true
                    ShowThemePicker -> showThemePicker = true
                }
            }
        }
    }

    Column {
        Row(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .clickable { viewModel.onThemeClicked() }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.settings_theme),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                textAlign = TextAlign.Start
            )
            Box(modifier = Modifier, contentAlignment = Alignment.BottomEnd) {
                Text(
                    text = stringResource(state.textRes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
                DropdownMenu(
                    showThemePicker,
                    onDismissRequest = { viewModel.onDismissTheme() }
                ) {
                    AppThemeEnum.entries.forEach { theme ->
                        DropdownMenuItem(
                            text = { Text(text = stringResource(theme.textRes)) },
                            onClick = { viewModel.onThemeChanged(theme) }
                        )
                    }
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                .clickable { viewModel.onClearClicked() }
                .padding(16.dp),
            text = stringResource(R.string.settings_clear_data),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            textAlign = TextAlign.Start
        )
    }

    if (showConfirmation) {
        ConfirmationDialog(
            onDismissed = { viewModel.onClearDismissed() },
            onConfirm = { viewModel.onConfirmClear() }
        )
    }
}

@Composable
private fun ConfirmationDialog(onDismissed: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismissed) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.settings_clear_data),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(R.string.settings_clear_data_dialog_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            BaseSecondaryButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.settings_clear_data_dialog_cancel),
                onClick = onDismissed
            )
            BasePrimaryButton(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.settings_clear_data_dialog_confirm),
                onClick = onConfirm
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NailPolishBoxCardCompactLightPreview() {
    UnhaversoTheme {
        SettingsScreen()
    }
}