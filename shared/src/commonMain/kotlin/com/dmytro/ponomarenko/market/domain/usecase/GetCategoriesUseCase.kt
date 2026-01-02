package com.dmytro.ponomarenko.market.domain.usecase

import com.dmytro.ponomarenko.market.domain.entity.Category
import com.dmytro.ponomarenko.market.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Result<List<Category>> {
        return categoryRepository.getCategories()
    }
}
