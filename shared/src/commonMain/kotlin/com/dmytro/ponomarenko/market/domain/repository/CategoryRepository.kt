package com.dmytro.ponomarenko.market.domain.repository

import com.dmytro.ponomarenko.market.domain.entity.Category

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getCategoryById(id: String): Result<Category>
}
