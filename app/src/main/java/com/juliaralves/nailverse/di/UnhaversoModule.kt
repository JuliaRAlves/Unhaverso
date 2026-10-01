package com.juliaralves.nailverse.di

import com.juliaralves.nailverse.MainViewModel
import com.juliaralves.nailverse.data.local.datasource.AppLocalDataSource
import com.juliaralves.nailverse.data.local.datasource.NailPolishLocalDataSource
import com.juliaralves.nailverse.data.local.datastore.ThemeDataStore
import com.juliaralves.nailverse.data.local.room.database.AppDatabase
import com.juliaralves.nailverse.data.local.room.database.getDatabaseBuilder
import com.juliaralves.nailverse.data.mapper.NailPolishDataMapper
import com.juliaralves.nailverse.data.repository.AppRepositoryImpl
import com.juliaralves.nailverse.data.repository.NailPolishRepositoryImpl
import com.juliaralves.nailverse.domain.repository.AppRepository
import com.juliaralves.nailverse.domain.repository.NailPolishRepository
import com.juliaralves.nailverse.domain.usecase.AddNailPolishUseCase
import com.juliaralves.nailverse.domain.usecase.GetNailPolishUseCase
import com.juliaralves.nailverse.domain.usecase.ObserveThemeUseCase
import com.juliaralves.nailverse.domain.usecase.RemoveAllNailPolishUseCase
import com.juliaralves.nailverse.domain.usecase.RemoveNailPolishUseCase
import com.juliaralves.nailverse.domain.usecase.UpdateThemeUseCase
import com.juliaralves.nailverse.presentation.inspiration.InspirationViewModel
import com.juliaralves.nailverse.presentation.nailpolishbox.NailPolishBoxViewModel
import com.juliaralves.nailverse.presentation.settings.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Data
    single<AppDatabase> { getDatabaseBuilder(get()) }
    factoryOf(::NailPolishLocalDataSource)
    factoryOf(::NailPolishDataMapper)
    factoryOf(::NailPolishRepositoryImpl) bind NailPolishRepository::class
    factoryOf(::ThemeDataStore)
    factoryOf(::AppLocalDataSource)
    factoryOf(::AppRepositoryImpl) bind AppRepository::class

    // Domain
    factoryOf(::GetNailPolishUseCase)
    factoryOf(::AddNailPolishUseCase)
    factoryOf(::RemoveNailPolishUseCase)
    factoryOf(::RemoveAllNailPolishUseCase)
    factoryOf(::UpdateThemeUseCase)
    factoryOf(::ObserveThemeUseCase)

    // Presentation
    viewModelOf(::InspirationViewModel)
    viewModelOf(::NailPolishBoxViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::MainViewModel)
}