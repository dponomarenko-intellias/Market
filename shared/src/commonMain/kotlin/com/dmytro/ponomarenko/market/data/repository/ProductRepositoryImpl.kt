package com.dmytro.ponomarenko.market.data.repository

import com.dmytro.ponomarenko.market.domain.entity.Product
import com.dmytro.ponomarenko.market.domain.repository.ProductRepository

class ProductRepositoryImpl : ProductRepository {
    // Mock data for demonstration
    private val mockProducts = listOf(
        Product("1", "Laptop", "High-performance laptop", 999.99, "https://placehold.co/600x400", "electronics", 4.5, 120),
        Product("2", "Smartphone", "Latest smartphone model", 699.99, "https://placehold.co/600x400", "electronics", 4.7, 250),
        Product("3", "Headphones", "Noise-cancelling headphones", 199.99, "https://placehold.co/600x400", "electronics", 4.3, 80),
        Product("4", "T-Shirt", "Cotton t-shirt", 29.99, "https://placehold.co/600x400", "clothing", 4.0, 45),
        Product("5", "Jeans", "Denim jeans", 59.99, "https://placehold.co/600x400", "clothing", 4.2, 67),
        Product("6", "Sneakers", "Running sneakers", 89.99, "https://placehold.co/600x400", "clothing", 4.6, 150),
        Product("7", "Coffee Maker", "Automatic coffee maker", 149.99, "https://placehold.co/600x400", "home", 4.4, 90),
        Product("8", "Blender", "High-speed blender", 79.99, "https://placehold.co/600x400", "home", 4.1, 55),
        Product("9", "Desk Lamp", "LED desk lamp", 39.99, "https://placehold.co/600x400", "home", 4.3, 70),
        Product("10", "Backpack", "Travel backpack", 49.99, "https://placehold.co/600x400", "accessories", 4.5, 100),
    )

    override suspend fun getProducts(page: Int, pageSize: Int, categoryId: String?): Result<List<Product>> {
        return try {
            val filtered = if (categoryId != null) {
                mockProducts.filter { it.categoryId == categoryId }
            } else {
                mockProducts
            }
            
            val start = (page - 1) * pageSize
            val end = minOf(start + pageSize, filtered.size)
            
            if (start >= filtered.size) {
                Result.success(emptyList())
            } else {
                Result.success(filtered.subList(start, end))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: String): Result<Product> {
        return try {
            val product = mockProducts.find { it.id == id }
            if (product != null) {
                Result.success(product)
            } else {
                Result.failure(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchProducts(query: String, page: Int, pageSize: Int): Result<List<Product>> {
        return try {
            val filtered = mockProducts.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.description.contains(query, ignoreCase = true)
            }
            
            val start = (page - 1) * pageSize
            val end = minOf(start + pageSize, filtered.size)
            
            if (start >= filtered.size) {
                Result.success(emptyList())
            } else {
                Result.success(filtered.subList(start, end))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
