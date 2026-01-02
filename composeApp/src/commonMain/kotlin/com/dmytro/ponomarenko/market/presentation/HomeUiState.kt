package com.dmytro.ponomarenko.market.presentation

import com.dmytro.ponomarenko.market.domain.entity.Category
import com.dmytro.ponomarenko.market.domain.entity.Product

data class HomeUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: String? = null,
    val searchQuery: String = "",
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true,
    val error: String? = null
)

sealed class HomeUiEvent {
    data class SearchQueryChanged(val query: String) : HomeUiEvent()
    data class CategorySelected(val categoryId: String?) : HomeUiEvent()
    data object LoadMoreProducts : HomeUiEvent()
    data object RefreshProducts : HomeUiEvent()
    data object Search : HomeUiEvent()
}
