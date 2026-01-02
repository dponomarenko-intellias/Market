package com.dmytro.ponomarenko.market.di

import com.dmytro.ponomarenko.market.presentation.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    // ViewModels
    viewModelOf(::HomeViewModel)
}
