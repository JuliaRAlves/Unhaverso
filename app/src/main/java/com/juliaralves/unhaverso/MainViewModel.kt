package com.juliaralves.unhaverso

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import com.juliaralves.unhaverso.domain.usecase.ObserveThemeUseCase
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