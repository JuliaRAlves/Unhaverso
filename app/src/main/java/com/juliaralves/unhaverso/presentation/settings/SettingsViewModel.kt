package com.juliaralves.unhaverso.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.domain.usecase.ObserveThemeUseCase
import com.juliaralves.unhaverso.domain.usecase.RemoveAllNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.UpdateThemeUseCase
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.HideClearConfirmation
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.HideThemePicker
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.ShowClearConfirmation
import com.juliaralves.unhaverso.presentation.settings.SettingsScreenEffect.ShowThemePicker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val removeAllNailPolishUseCase: RemoveAllNailPolishUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase,
    observeThemeUseCase: ObserveThemeUseCase
) : ViewModel() {

    val screenState: StateFlow<AppThemeEnum> = observeThemeUseCase.execute().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppThemeEnum.SYSTEM
    )

    private val _screenEffect = Channel<SettingsScreenEffect>(capacity = Channel.BUFFERED)
    val screenEffect: Flow<SettingsScreenEffect>
        get() = _screenEffect.receiveAsFlow()

    fun onThemeClicked() {
        viewModelScope.launch {
            _screenEffect.send(ShowThemePicker)
        }
    }

    fun onDismissTheme() {
        viewModelScope.launch {
            _screenEffect.send(HideThemePicker)
        }
    }

    fun onThemeChanged(theme: AppThemeEnum) {
        viewModelScope.launch {
            updateThemeUseCase.execute(UpdateThemeUseCase.Params(theme))
        }
    }

    fun onClearClicked() {
        viewModelScope.launch {
            _screenEffect.send(ShowClearConfirmation)
        }
    }

    fun onClearDismissed() {
        viewModelScope.launch {
            _screenEffect.send(HideClearConfirmation)
        }
    }

    fun onConfirmClear() {
        viewModelScope.launch {
            clearData()
            _screenEffect.send(HideClearConfirmation)
        }
    }

    private suspend fun clearData() {
        removeAllNailPolishUseCase.execute()
    }
}

sealed interface SettingsScreenEffect {
    data object ShowClearConfirmation : SettingsScreenEffect
    data object HideClearConfirmation : SettingsScreenEffect
    data object ShowThemePicker : SettingsScreenEffect
    data object HideThemePicker : SettingsScreenEffect
}