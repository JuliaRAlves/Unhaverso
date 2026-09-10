package com.juliaralves.unhaverso.di

import com.juliaralves.unhaverso.presentation.inspiration.InspirationViewModel
import com.juliaralves.unhaverso.presentation.nailpolishbox.NailPolishBoxViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::InspirationViewModel)
    viewModelOf(::NailPolishBoxViewModel)
}