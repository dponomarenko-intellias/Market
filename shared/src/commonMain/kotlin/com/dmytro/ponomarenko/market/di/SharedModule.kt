package com.dmytro.ponomarenko.market.di

import com.dmytro.ponomarenko.market.data.repository.CategoryRepositoryImpl
import com.dmytro.ponomarenko.market.data.repository.ProductRepositoryImpl
import com.dmytro.ponomarenko.market.domain.repository.CategoryRepository
import com.dmytro.ponomarenko.market.domain.repository.ProductRepository
import com.dmytro.ponomarenko.market.domain.usecase.GetCategoriesUseCase
import com.dmytro.ponomarenko.market.domain.usecase.GetProductsUseCase
import com.dmytro.ponomarenko.market.domain.usecase.SearchProductsUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule: Module = module {
    // Repositories
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    
    // Use Cases
    singleOf(::GetProductsUseCase)
    singleOf(::GetCategoriesUseCase)
    singleOf(::SearchProductsUseCase)
}
