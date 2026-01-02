package com.dmytro.ponomarenko.market.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.dmytro.ponomarenko.market.presentation.HomeUiEvent
import com.dmytro.ponomarenko.market.presentation.HomeViewModel
import com.dmytro.ponomarenko.market.ui.components.CategoryPanel
import com.dmytro.ponomarenko.market.ui.components.ProductGrid
import com.dmytro.ponomarenko.market.ui.components.TopBar
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        
        HomeScreenContent(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun HomeScreenContent(
    uiState: com.dmytro.ponomarenko.market.presentation.HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { query ->
                    onEvent(HomeUiEvent.SearchQueryChanged(query))
                },
                onSearchClick = {
                    onEvent(HomeUiEvent.Search)
                }
            )
        }
    ) { paddingValues ->
        // Responsive layout based on window width
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val isWideScreen = maxWidth > 800.dp
            
            if (isWideScreen) {
                // Two-pane layout for Web/Desktop
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Left: Categories Panel (Fixed width)
                    CategoryPanel(
                        categories = uiState.categories,
                        selectedCategoryId = uiState.selectedCategoryId,
                        onCategorySelected = { categoryId ->
                            onEvent(HomeUiEvent.CategorySelected(categoryId))
                        },
                        modifier = Modifier
                            .width(250.dp)
                            .fillMaxHeight()
                            .padding(16.dp)
                    )
                    
                    // Right: Product Grid
                    ProductGrid(
                        products = uiState.products,
                        isLoading = uiState.isLoading,
                        hasMorePages = uiState.hasMorePages,
                        onLoadMore = {
                            onEvent(HomeUiEvent.LoadMoreProducts)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                // Single pane for Mobile/Android
                // For now, just show the product grid
                // Categories could be added as a horizontal scroll or drawer later
                ProductGrid(
                    products = uiState.products,
                    isLoading = uiState.isLoading,
                    hasMorePages = uiState.hasMorePages,
                    onLoadMore = {
                        onEvent(HomeUiEvent.LoadMoreProducts)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        
        // Error Snackbar
        uiState.error?.let { error ->
            Snackbar(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(error)
            }
        }
    }
}
