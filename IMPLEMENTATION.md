# Market - KMP E-commerce Application

## Implementation Summary

This implementation establishes the foundation for a Kotlin Multiplatform e-commerce application following Clean Architecture principles with MVVM pattern and unidirectional data flow.

## Architecture

### Tech Stack
- **Kotlin Multiplatform**: Shared business logic across Android and Web (WasmJS)
- **Compose Multiplatform**: Shared UI across all platforms
- **Voyager**: Type-safe navigation library
- **Koin**: Dependency injection
- **Ktor Client**: HTTP networking with ContentNegotiation
- **Kamel**: Image loading library for Compose Multiplatform
- **Kotlinx Serialization**: JSON serialization

### Project Structure

```
Market/
├── shared/                          # Shared business logic
│   └── src/commonMain/kotlin/
│       └── com/dmytro/ponomarenko/market/
│           ├── domain/              # Domain layer (business logic)
│           │   ├── entity/          # Domain entities
│           │   │   ├── Product.kt
│           │   │   └── Category.kt
│           │   ├── repository/      # Repository interfaces
│           │   │   ├── ProductRepository.kt
│           │   │   └── CategoryRepository.kt
│           │   └── usecase/         # Use cases
│           │       ├── GetProductsUseCase.kt
│           │       ├── GetCategoriesUseCase.kt
│           │       └── SearchProductsUseCase.kt
│           ├── data/                # Data layer (implementation)
│           │   └── repository/      # Repository implementations
│           │       ├── ProductRepositoryImpl.kt (with mock data)
│           │       └── CategoryRepositoryImpl.kt (with mock data)
│           └── di/                  # Dependency injection
│               └── SharedModule.kt  # Koin DI module
│
└── composeApp/                      # Compose UI application
    └── src/commonMain/kotlin/
        └── com/dmytro/ponomarenko/market/
            ├── presentation/        # Presentation layer (MVVM)
            │   ├── HomeViewModel.kt # ViewModel with state management
            │   └── HomeUiState.kt   # UI state and events
            ├── ui/
            │   ├── screens/         # Screen implementations
            │   │   └── HomeScreen.kt
            │   └── components/      # Reusable UI components
            │       ├── TopBar.kt
            │       ├── CategoryPanel.kt
            │       └── ProductGrid.kt
            ├── di/
            │   └── AppModule.kt     # Koin DI module for ViewModels
            └── App.kt               # Main app entry point
```

## Home Page Implementation

### Top Bar Features
- **Logo**: Shopping cart icon on the left
- **Search Field**: Expanded search input in the center with:
  - Search icon
  - Clear button when text is entered
  - Placeholder text
- **Action Buttons**: Four icon buttons on the right:
  - Notifications
  - Wishlist
  - Cart
  - Profile

### Responsive Layout

#### Desktop/Web (>800dp width)
- **Two-pane layout**:
  - **Left Panel** (250dp fixed width): Categories panel with:
    - "All Products" option
    - List of product categories
    - Selected category highlighting
  - **Right Panel** (flexible): Product grid with:
    - Adaptive columns (minimum 200dp per item)
    - Product cards with image, name, description, price, and rating

#### Mobile/Android (≤800dp width)
- **Single pane layout**:
  - Full-width product grid
  - Categories can be added as horizontal scroll or drawer (future enhancement)

### Product Grid Features
- **Infinite scroll**: Automatically loads more products as user scrolls
- **Product Cards** display:
  - Product image (with loading state)
  - Product name
  - Description
  - Price (formatted)
  - Rating and review count
- **Empty state**: Shows "No products found" message
- **Loading indicator**: Shows at bottom when loading more items

### State Management (Unidirectional Data Flow)

```kotlin
// State
data class HomeUiState(
    val isLoading: Boolean,
    val products: List<Product>,
    val categories: List<Category>,
    val selectedCategoryId: String?,
    val searchQuery: String,
    val currentPage: Int,
    val hasMorePages: Boolean,
    val error: String?
)

// Events
sealed class HomeUiEvent {
    data class SearchQueryChanged(val query: String)
    data class CategorySelected(val categoryId: String?)
    object LoadMoreProducts
    object RefreshProducts
    object Search
}
```

The ViewModel processes events and updates state in a unidirectional flow:
1. User triggers event → 
2. ViewModel processes event → 
3. ViewModel updates state → 
4. UI recomposes with new state

## Clean Architecture Layers

### 1. Domain Layer (shared module)
- **Entities**: Pure Kotlin data classes (Product, Category)
- **Repository Interfaces**: Define contracts for data access
- **Use Cases**: Business logic operations
  - Each use case has single responsibility
  - Interacts with repository interfaces
  - Returns Result type for error handling

### 2. Data Layer (shared module)
- **Repository Implementations**: Concrete implementations of repository interfaces
- Currently uses **mock data** for demonstration
- Can be easily replaced with real API calls using Ktor Client
- Error handling with Kotlin Result type

### 3. Presentation Layer (composeApp module)
- **ViewModels**: State management with StateFlow
- **UI State**: Immutable data classes representing UI state
- **UI Events**: Sealed classes for user interactions
- **Screens**: Voyager screen implementations
- **Components**: Reusable Composable functions

## Dependency Injection with Koin

### Shared Module
```kotlin
val sharedModule = module {
    // Repositories
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    
    // Use Cases
    singleOf(::GetProductsUseCase)
    singleOf(::GetCategoriesUseCase)
    singleOf(::SearchProductsUseCase)
}
```

### App Module
```kotlin
val appModule = module {
    // ViewModels
    viewModelOf(::HomeViewModel)
}
```

## Navigation with Voyager

The app uses Voyager for type-safe navigation:
- `HomeScreen` is the initial screen
- Navigator manages screen stack
- Screens are objects implementing `Screen` interface

## Features Implemented

✅ Clean Architecture structure with Domain, Data, and Presentation layers
✅ MVVM pattern with unidirectional data flow
✅ Dependency Injection with Koin
✅ Navigation with Voyager
✅ Home page UI with responsive layout
✅ Top bar with logo, search, and action buttons
✅ Category panel (for wide screens)
✅ Product grid with infinite scroll
✅ Image loading with Kamel
✅ Mock data repositories (ready for API integration)
✅ State management with StateFlow
✅ Error handling with Result type

## Next Steps

1. **API Integration**: Replace mock repositories with real Ktor Client implementation
2. **More Screens**: Add Product Detail, Cart, Profile screens
3. **Authentication**: Implement user authentication flow
4. **Persistent Storage**: Add local database for offline support
5. **Testing**: Add unit and integration tests
6. **Mobile Categories**: Add horizontal scroll or drawer for categories on mobile
7. **Animations**: Add transitions and animations
8. **Error Handling UI**: Improve error display with Snackbar or Dialog

## Running the Application

### Web (WasmJS)
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

### Desktop (JVM)
```bash
./gradlew :composeApp:run
```

### Android
```bash
./gradlew :composeApp:assembleDebug
```

## Dependencies Added

All required dependencies have been added to `gradle/libs.versions.toml`:
- Voyager 1.0.1 (navigator, screenmodel, koin integration)
- Koin 4.0.1 (core, compose, viewmodel)
- Ktor 3.3.3 (client-core, content-negotiation, serialization, logging)
- Kamel 0.9.5 (image loading)
- Kotlinx Serialization 1.7.3

## Code Quality

The implementation follows:
- Kotlin coding conventions
- Single Responsibility Principle
- Dependency Inversion Principle
- Separation of Concerns
- Clean Architecture principles
- MVVM pattern best practices
