package com.dmytro.ponomarenko.market.domain.usecase

import com.dmytro.ponomarenko.market.domain.entity.Product
import com.dmytro.ponomarenko.market.domain.repository.ProductRepository

class SearchProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(query: String, page: Int, pageSize: Int): Result<List<Product>> {
        return productRepository.searchProducts(query, page, pageSize)
    }
}
