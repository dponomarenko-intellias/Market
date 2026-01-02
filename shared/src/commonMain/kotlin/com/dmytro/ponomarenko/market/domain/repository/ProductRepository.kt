package com.dmytro.ponomarenko.market.domain.repository

import com.dmytro.ponomarenko.market.domain.entity.Product

interface ProductRepository {
    suspend fun getProducts(page: Int, pageSize: Int, categoryId: String? = null): Result<List<Product>>
    suspend fun getProductById(id: String): Result<Product>
    suspend fun searchProducts(query: String, page: Int, pageSize: Int): Result<List<Product>>
}
