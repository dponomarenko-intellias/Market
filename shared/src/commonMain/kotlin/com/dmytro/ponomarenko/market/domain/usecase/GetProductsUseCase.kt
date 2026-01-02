package com.dmytro.ponomarenko.market.domain.usecase

import com.dmytro.ponomarenko.market.domain.entity.Product
import com.dmytro.ponomarenko.market.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(page: Int, pageSize: Int, categoryId: String? = null): Result<List<Product>> {
        return productRepository.getProducts(page, pageSize, categoryId)
    }
}
