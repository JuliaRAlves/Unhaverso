package com.juliaralves.nailverse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.nailverse.domain.model.AppThemeEnum
import com.juliaralves.nailverse.domain.usecase.ObserveThemeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(observeThemeUseCase: ObserveThemeUseCase) : ViewModel() {

    val theme: StateFlow<AppThemeEnum> = observeThemeUseCase.execute().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppThemeEnum.SYSTEM
    )

}