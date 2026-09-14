package com.juliaralves.unhaverso.di

import com.juliaralves.unhaverso.MainViewModel
import com.juliaralves.unhaverso.data.local.datasource.AppLocalDataSource
import com.juliaralves.unhaverso.data.local.datasource.NailPolishLocalDataSource
import com.juliaralves.unhaverso.data.local.datastore.ThemeDataStore
import com.juliaralves.unhaverso.data.local.room.database.AppDatabase
import com.juliaralves.unhaverso.data.local.room.database.getDatabaseBuilder
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.data.repository.AppRepositoryImpl
import com.juliaralves.unhaverso.data.repository.NailPolishRepositoryImpl
import com.juliaralves.unhaverso.domain.repository.AppRepository
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository
import com.juliaralves.unhaverso.domain.usecase.AddNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.GetNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.ObserveThemeUseCase
import com.juliaralves.unhaverso.domain.usecase.RemoveAllNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.RemoveNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.UpdateThemeUseCase
import com.juliaralves.unhaverso.presentation.inspiration.InspirationViewModel
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxViewModel
import com.juliaralves.unhaverso.presentation.settings.SettingsViewModel
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