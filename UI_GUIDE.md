# Market KMP - UI Component Guide

## Home Screen Components

### 1. TopBar Component
**Location**: `composeApp/src/commonMain/kotlin/.../ui/components/TopBar.kt`

**Features**:
- Material3 TopAppBar
- Shopping cart icon logo on the left
- Expanded search field in the center
- Four action icon buttons on the right

**Props**:
```kotlin
@Composable
fun TopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Visual Structure**:
```
┌─────────────────────────────────────────────────────────────┐
│ 🛒 │ [🔍 Search products...                    ] [✖] │🔔💗🛒👤│
└─────────────────────────────────────────────────────────────┘
```

**Behavior**:
- Search field expands to fill available space
- Shows clear button (✖) when text is entered
- Single line input with placeholder
- Icons are clickable but not yet implemented

---

### 2. CategoryPanel Component
**Location**: `composeApp/src/commonMain/kotlin/.../ui/components/CategoryPanel.kt`

**Features**:
- Fixed 250dp width on desktop/web
- LazyColumn for category list
- "All Products" option at top
- Highlighted selected category

**Props**:
```kotlin
@Composable
fun CategoryPanel(
    categories: List<Category>,
    selectedCategoryId: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
)
```

**Visual Structure**:
```
┌──────────────────┐
│   Categories     │
├──────────────────┤
│ ◉ All Products   │ ← Selected (primary color)
│ ○ Electronics    │
│ ○ Clothing       │
│ ○ Home & Kitchen │
│ ○ Accessories    │
└──────────────────┘
```

**Behavior**:
- Click on category to filter products
- Selected category shows with primary container color
- "All Products" shows all items (no filter)
- Scrollable if many categories

---

### 3. ProductGrid Component
**Location**: `composeApp/src/commonMain/kotlin/.../ui/components/ProductGrid.kt`

**Features**:
- Adaptive columns (min 200dp per item)
- LazyVerticalGrid for performance
- Infinite scroll with pagination
- Empty state message
- Loading indicator

**Props**:
```kotlin
@Composable
fun ProductGrid(
    products: List<Product>,
    isLoading: Boolean,
    hasMorePages: Boolean,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Visual Structure**:
```
┌────────────────────────────────────────────────────────┐
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌────────┐ │
│  │  Image   │  │  Image   │  │  Image   │  │ Image  │ │
│  │ [150px]  │  │ [150px]  │  │ [150px]  │  │[150px] │ │
│  │          │  │          │  │          │  │        │ │
│  ├──────────┤  ├──────────┤  ├──────────┤  ├────────┤ │
│  │ Laptop   │  │Smartphone│  │Headphones│  │T-Shirt │ │
│  │ High-per │  │Latest sm │  │Noise-can │  │Cotton  │ │
│  │ $999.99  │  │ $699.99  │  │ $199.99  │  │ $29.99 │ │
│  │ ★ 4.5(120)│ │ ★ 4.7(250)│ │ ★ 4.3(80)│ │★4.0(45)│ │
│  └──────────┘  └──────────┘  └──────────┘  └────────┘ │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌────────┐ │
│  │  Image   │  │  Image   │  │  Image   │  │ Image  │ │
│  │   ...    │  │   ...    │  │   ...    │  │  ...   │ │
│  └──────────┘  └──────────┘  └──────────┘  └────────┘ │
│                      ⟳ Loading...                      │
└────────────────────────────────────────────────────────┘
```

---

### 4. ProductCard Component
**Location**: `composeApp/src/commonMain/kotlin/.../ui/components/ProductGrid.kt`

**Features**:
- Material3 Card with elevation
- Kamel image loading with states
- Product name (max 2 lines)
- Description (max 2 lines)
- Price in large text
- Star rating and review count

**Visual Structure**:
```
┌─────────────────────────┐
│                         │
│    Product Image        │
│      (150dp h)          │
│                         │
├─────────────────────────┤
│ Product Name            │
│ Product description     │
│ truncated if too long   │
│                         │
│ $999.99      ★ 4.5 (120)│
└─────────────────────────┘
```

**States**:
- **Loading**: Shows CircularProgressIndicator
- **Success**: Shows image
- **Error**: Shows "Image failed to load" text

---

## Screen Layouts

### Desktop/Web Layout (>800dp)
**File**: `HomeScreen.kt`

```
┌────────────────────────────────────────────────────────────┐
│                         TOP BAR                            │
│  🛒 Logo  [🔍 Search Field................] 🔔 💗 🛒 👤    │
└────────────────────────────────────────────────────────────┘
┌────────────┬───────────────────────────────────────────────┐
│            │                                                │
│ CATEGORIES │              PRODUCT GRID                     │
│  (250dp)   │           (Adaptive Columns)                  │
│            │                                                │
│ ╔════════╗ │  ┌────┐ ┌────┐ ┌────┐ ┌────┐                │
│ ║  All   ║ │  │ P1 │ │ P2 │ │ P3 │ │ P4 │                │
│ ╚════════╝ │  └────┘ └────┘ └────┘ └────┘                │
│ ┌────────┐ │  ┌────┐ ┌────┐ ┌────┐ ┌────┐                │
│ │Electro.│ │  │ P5 │ │ P6 │ │ P7 │ │ P8 │                │
│ └────────┘ │  └────┘ └────┘ └────┘ └────┘                │
│ ┌────────┐ │  ┌────┐ ┌────┐ ┌────┐ ┌────┐                │
│ │Clothing│ │  │ P9 │ │P10 │ │P11 │ │P12 │                │
│ └────────┘ │  └────┘ └────┘ └────┘ └────┘                │
│ ┌────────┐ │           ⟳ Loading more...                  │
│ │  Home  │ │                                                │
│ └────────┘ │                                                │
│            │                                                │
└────────────┴───────────────────────────────────────────────┘
```

**Responsive Behavior**:
- Categories panel: Fixed 250dp width
- Product grid: Fills remaining space
- Columns: Adaptive (min 200dp each)
- Grid adjusts number of columns based on available width

---

### Mobile/Android Layout (≤800dp)
**File**: `HomeScreen.kt`

```
┌──────────────────────┐
│      TOP BAR         │
│ 🛒 [🔍...] 🔔💗🛒👤  │
└──────────────────────┘
│                      │
│    PRODUCT GRID      │
│   (Full Width)       │
│                      │
│  ┌────────┐┌────────┐│
│  │   P1   ││   P2   ││
│  │ $99.99 ││$199.99 ││
│  │ ★ 4.5  ││ ★ 4.2  ││
│  └────────┘└────────┘│
│  ┌────────┐┌────────┐│
│  │   P3   ││   P4   ││
│  │ $49.99 ││$299.99 ││
│  │ ★ 4.7  ││ ★ 4.1  ││
│  └────────┘└────────┘│
│  ┌────────┐┌────────┐│
│  │   P5   ││   P6   ││
│  │ $79.99 ││$129.99 ││
│  │ ★ 4.3  ││ ★ 4.8  ││
│  └────────┘└────────┘│
│    ⟳ Loading...      │
│                      │
└──────────────────────┘
```

**Mobile-Specific Features**:
- No category panel (can be added as drawer or horizontal scroll)
- 2 columns on most phones
- Compact search field in TopBar
- Smaller action button icons

---

## Component States

### ProductGrid States

#### 1. Loading (Initial)
```
┌────────────────────┐
│                    │
│   ⟳ Loading...     │
│                    │
└────────────────────┘
```

#### 2. Products Loaded
```
┌────────────────────┐
│ [Products Grid]    │
│ [Products Grid]    │
│ [Products Grid]    │
└────────────────────┘
```

#### 3. Loading More (Bottom)
```
┌────────────────────┐
│ [Products Grid]    │
│ [Products Grid]    │
│ [Products Grid]    │
│   ⟳ Loading more...│
└────────────────────┘
```

#### 4. Empty State
```
┌────────────────────┐
│                    │
│ No products found  │
│                    │
└────────────────────┘
```

#### 5. Error State
```
┌────────────────────┐
│ [Products Grid]    │
│ ⚠️ Error message   │
└────────────────────┘
```

---

## Color Scheme (Material3)

The app uses Material3 theming with these semantic colors:

- **Primary**: Brand color (used for selected items, prices)
- **PrimaryContainer**: Selected category background
- **OnPrimaryContainer**: Text on selected category
- **Surface**: Card backgrounds, TopBar
- **OnSurface**: Primary text
- **OnSurfaceVariant**: Secondary text (descriptions)
- **Tertiary**: Star rating color

---

## Typography

- **TitleLarge**: "Categories" heading, Price
- **TitleMedium**: Product name
- **BodyLarge**: Category items
- **BodyMedium**: Rating value
- **BodySmall**: Product description, Review count

---

## Spacing

- **Card Elevation**: 2dp
- **Padding**: 8dp, 12dp, 16dp (consistent spacing)
- **Grid Spacing**: 16dp between items
- **Image Height**: 150dp
- **Category Width**: 250dp (desktop)
- **Min Card Width**: 200dp (adaptive)

---

## Interactions

### Search
1. User types in search field → `SearchQueryChanged` event
2. User presses Enter → `Search` event
3. ViewModel performs search
4. Products list updates

### Category Selection
1. User clicks category → `CategorySelected` event
2. ViewModel filters products
3. Grid shows filtered results
4. Selected category is highlighted

### Infinite Scroll
1. User scrolls to bottom
2. LazyGrid detects last item
3. `LoadMoreProducts` event triggered
4. ViewModel loads next page
5. New products appended to grid

### Refresh
1. User pulls down (can be added)
2. `RefreshProducts` event
3. ViewModel reloads from page 1
4. Grid shows fresh data

---

## Mock Data

The implementation includes 10 sample products across 4 categories:
- **Electronics**: Laptop, Smartphone, Headphones
- **Clothing**: T-Shirt, Jeans, Sneakers
- **Home & Kitchen**: Coffee Maker, Blender, Desk Lamp
- **Accessories**: Backpack

Each product has:
- Unique ID
- Name and description
- Price
- Placeholder image URL
- Category ID
- Rating (0-5 stars)
- Review count

---

## Performance Optimizations

1. **LazyColumn/LazyGrid**: Only renders visible items
2. **Kamel Image Loading**: Async loading with caching
3. **StateFlow**: Efficient state updates
4. **Immutable State**: Prevents unnecessary recompositions
5. **Pagination**: Loads data in chunks (10 items per page)

---

## Accessibility

- All icons have contentDescription
- Touch targets meet minimum size requirements
- Color contrast follows Material3 guidelines
- Text is readable at default sizes
- Semantic structure with proper composables

---

## Future UI Enhancements

1. **Animations**:
   - Fade in for new products
   - Smooth category selection
   - Loading skeleton screens

2. **Pull to Refresh**:
   - Swipe down gesture
   - Refresh indicator

3. **Filter & Sort**:
   - Price range filter
   - Sort by: Price, Rating, Popularity
   - Filter UI dialog

4. **Mobile Categories**:
   - Horizontal scroll chips
   - Or navigation drawer
   - Or bottom sheet

5. **Product Details**:
   - Click on card → Detail screen
   - Image gallery
   - Add to cart button
   - Reviews section

6. **Cart Badge**:
   - Show item count on cart icon
   - Animate when items added

7. **Wishlist**:
   - Heart icon on product cards
   - Toggle favorite state

8. **Empty States**:
   - Custom illustrations
   - Helpful messages
   - Action buttons
