package com.dmytro.ponomarenko.market.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmytro.ponomarenko.market.domain.usecase.GetCategoriesUseCase
import com.dmytro.ponomarenko.market.domain.usecase.GetProductsUseCase
import com.dmytro.ponomarenko.market.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val pageSize = 10

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Load categories
            getCategoriesUseCase().fold(
                onSuccess = { categories ->
                    _uiState.value = _uiState.value.copy(categories = categories)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(error = error.message)
                }
            )
            
            // Load initial products
            loadProducts()
        }
    }

    private suspend fun loadProducts() {
        val currentState = _uiState.value
        
        getProductsUseCase(
            page = currentState.currentPage,
            pageSize = pageSize,
            categoryId = currentState.selectedCategoryId
        ).fold(
            onSuccess = { newProducts ->
                _uiState.value = _uiState.value.copy(
                    products = if (currentState.currentPage == 1) newProducts 
                              else currentState.products + newProducts,
                    isLoading = false,
                    hasMorePages = newProducts.size == pageSize,
                    error = null
                )
            },
            onFailure = { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
        )
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.SearchQueryChanged -> {
                _uiState.value = _uiState.value.copy(searchQuery = event.query)
            }
            
            is HomeUiEvent.CategorySelected -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        selectedCategoryId = event.categoryId,
                        currentPage = 1,
                        products = emptyList(),
                        isLoading = true
                    )
                    loadProducts()
                }
            }
            
            is HomeUiEvent.LoadMoreProducts -> {
                if (_uiState.value.hasMorePages && !_uiState.value.isLoading) {
                    viewModelScope.launch {
                        _uiState.value = _uiState.value.copy(
                            currentPage = _uiState.value.currentPage + 1,
                            isLoading = true
                        )
                        loadProducts()
                    }
                }
            }
            
            is HomeUiEvent.RefreshProducts -> {
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        currentPage = 1,
                        products = emptyList(),
                        isLoading = true
                    )
                    loadProducts()
                }
            }
            
            is HomeUiEvent.Search -> {
                viewModelScope.launch {
                    val query = _uiState.value.searchQuery
                    if (query.isNotBlank()) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            currentPage = 1,
                            products = emptyList()
                        )
                        
                        searchProductsUseCase(query, 1, pageSize).fold(
                            onSuccess = { products ->
                                _uiState.value = _uiState.value.copy(
                                    products = products,
                                    isLoading = false,
                                    hasMorePages = products.size == pageSize,
                                    error = null
                                )
                            },
                            onFailure = { error ->
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = error.message
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
