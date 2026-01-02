package com.dmytro.ponomarenko.market.data.repository

import com.dmytro.ponomarenko.market.domain.entity.Category
import com.dmytro.ponomarenko.market.domain.repository.CategoryRepository

class CategoryRepositoryImpl : CategoryRepository {
    // Mock data for demonstration
    private val mockCategories = listOf(
        Category("electronics", "Electronics", null),
        Category("clothing", "Clothing", null),
        Category("home", "Home & Kitchen", null),
        Category("accessories", "Accessories", null),
    )

    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            Result.success(mockCategories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCategoryById(id: String): Result<Category> {
        return try {
            val category = mockCategories.find { it.id == id }
            if (category != null) {
                Result.success(category)
            } else {
                Result.failure(Exception("Category not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
