package com.juliaralves.unhaverso.di

import com.juliaralves.unhaverso.data.local.datasource.NailPolishLocalDataSource
import com.juliaralves.unhaverso.data.local.datasource.NailPolishLocalDataSourceImpl
import com.juliaralves.unhaverso.data.local.room.database.AppDatabase
import com.juliaralves.unhaverso.data.local.room.database.getDatabaseBuilder
import com.juliaralves.unhaverso.data.mapper.NailPolishDataMapper
import com.juliaralves.unhaverso.data.repository.NailPolishRepositoryImpl
import com.juliaralves.unhaverso.domain.repository.NailPolishRepository
import com.juliaralves.unhaverso.domain.usecase.AddNailPolishUseCase
import com.juliaralves.unhaverso.domain.usecase.GetNailPolishUseCase
import com.juliaralves.unhaverso.presentation.inspiration.InspirationViewModel
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Data
    single<AppDatabase> { getDatabaseBuilder(get()) }
    factoryOf(::NailPolishLocalDataSourceImpl) bind NailPolishLocalDataSource::class
    factoryOf(::NailPolishDataMapper)
    factoryOf(::NailPolishRepositoryImpl) bind NailPolishRepository::class

    // Domain
    factoryOf(::GetNailPolishUseCase)
    factoryOf(::AddNailPolishUseCase)

    // Presentation
    viewModelOf(::InspirationViewModel)
    viewModelOf(::NailPolishBoxViewModel)
}