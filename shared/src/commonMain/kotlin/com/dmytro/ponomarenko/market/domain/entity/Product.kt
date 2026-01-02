package com.dmytro.ponomarenko.market.domain.entity

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String,
    val rating: Double = 0.0,
    val reviewCount: Int = 0
)
