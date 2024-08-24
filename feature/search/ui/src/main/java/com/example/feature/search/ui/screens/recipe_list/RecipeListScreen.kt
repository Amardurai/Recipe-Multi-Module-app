@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.example.feature.search.ui.screens.recipe_list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.common.components.EmptyScreen
import com.example.common.components.LoadingIndicator
import com.example.common.components.ObserveAsEvent
import com.example.feature.search.domain.model.Country
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.ui.R
import com.example.feature.search.ui.navigation.Dest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    uiState: RecipeListState,
    events: Flow<RecipeListEvent>,
    onAction: (RecipeListAction) -> Unit,
    navHostController: NavHostController,
    ) {
    val context = LocalContext.current
    ObserveAsEvent(events) { event ->
        when (event) {

            is RecipeListEvent.OnError -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }

            is RecipeListEvent.GoToDetailScreen -> navHostController.navigate(
                Dest.RecipeDetail(
                    event.recipe
                )
            )

            RecipeListEvent.GoToFavoriteScreen -> navHostController.navigate(
                Dest.Favorite
            )

        }
    }
    var showFilterDialog by remember {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val query = rememberSaveable {
        mutableStateOf("")
    }


    if (showFilterDialog) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    showFilterDialog = false
                }
            },
            sheetState = bottomSheetState
        ) {
            FilterBottomSheet(
                uiState = uiState,
                onFilterChange = { selectedCountries ->

                    onAction(RecipeListAction.OnCountryFilterChange(selectedCountries))
                }
            )
        }
    }

    Scaffold(
        topBar = {
            SearchBar(query = query.value, onQueryChange = {
                query.value = it
                onAction.invoke(RecipeListAction.OnSearchQueryChange(it))
            }, onFilterToggle = {
                showFilterDialog = showFilterDialog.not()
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Favorites") },
                icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) },
                onClick = { onAction.invoke(RecipeListAction.OnFavoriteClicked) },
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when {
                uiState.isLoading -> LoadingIndicator()
                uiState.recipes.isEmpty() -> EmptyScreen(message = "No recipes found")
                else -> RecipeList(uiState.recipes) { recipeId ->
                    onAction(RecipeListAction.OnRecipeItemClicked(recipeId))
                }

            }
        }

    }
}
@Composable
fun FilterBottomSheet(
    uiState: RecipeListState,
    onFilterChange: (List<Country>) -> Unit
) {
    val countries = remember { mutableStateOf(uiState.country) }

    Column {
        Text(
            text = "Filter by country", style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            itemsIndexed(countries.value) { index, country ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = country.isSelect,
                        onCheckedChange = { isChecked ->
                            val updatedCountries = countries.value.mapIndexed { i, currentCountry ->
                                if (i == index) {
                                    currentCountry.copy(isSelect = isChecked)
                                } else currentCountry
                            }
                            countries.value = updatedCountries

                            onFilterChange(updatedCountries)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = country.name)
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onFilterToggle: () -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }

    TextField(
        value = query,
        onValueChange = onQueryChange,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.White,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary
        ),
        placeholder = {
            Text(
                text = "Search Recipe",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        },
        leadingIcon = {
            Icon(Icons.Filled.Search, "Search")
        },
        trailingIcon = {
            Row {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                }
                IconButton(onClick = {
                    onFilterToggle.invoke()
                }) {
                    Icon(
                        Icons.Filled.FilterList,
                        contentDescription = "Clear",
                        tint = Color.Gray
                    )
                }

            }
        },
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused.value = it.isFocused
            }
            .animateContentSize(animationSpec = tween(durationMillis = 300))
    )
}

@Composable
private fun RecipeList(recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {

    LazyColumn {
        items(recipes, key = { it.idMeal }) { recipe ->
            DishCard(recipe, onRecipeClick)
        }
    }

}

@Composable
fun DishCard(recipe: Recipe, onClick: (Recipe) -> Unit) {

    val animatable = remember {
        Animatable(0.5f)
    }
    LaunchedEffect(key1 = true) {
        animatable.animateTo(1f, tween(350, easing = FastOutSlowInEasing))
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer {
                scaleX = animatable.value
                scaleY = animatable.value
            }
            .clickable {
                onClick(recipe)
            }
    ) {
        Box(
            modifier = Modifier.height(250.dp)
        ) {
            RecipeImage(recipe)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 250f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = recipe.strMeal.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = recipe.strCategory.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun RecipeImage(recipe: Recipe) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipe.strMealThumb)
            .crossfade(true)
            .build(),
        contentDescription = "Recipe Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Error Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun PreviewRecipeListScreen() {

}