package com.example.feature.search.ui.screens.favorite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.common.components.EmptyScreen
import com.example.common.components.LoadingIndicator
import com.example.common.components.ObserveAsEvent
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.ui.R
import com.example.feature.search.ui.navigation.Dest
import com.example.feature.search.ui.screens.recipe_list.DishCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteScreen(
    uiState: FavoriteUiState,
    events: Flow<FavoriteEvent>,
    onAction: (FavoriteAction) -> Unit,
    navHostController: NavHostController,
) {
    val showDropDown = rememberSaveable { mutableStateOf(false) }
    val selectedItem = rememberSaveable { mutableIntStateOf(0) }

    ObserveAsEvent(flow = events) { event ->
        when (event) {
            is FavoriteEvent.GoToRecipeDetail -> navHostController.navigate(Dest.RecipeDetail(event.recipe))
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.favorite_recipes)) },
                actions = {
                    IconButton(onClick = { showDropDown.value = showDropDown.value.not() }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }

                    if (showDropDown.value) {
                        DropdownMenu(
                            expanded = showDropDown.value,
                            onDismissRequest = { showDropDown.value = showDropDown.value.not() }) {
                            FavoriteViewModel.SortType.entries.forEachIndexed { index, sortType ->
                                DropdownMenuItem(
                                    text = { Text(text = sortType.name) },
                                    onClick = {
                                        handleSorting(sortType, onAction)
                                        selectedItem.intValue = index
                                        showDropDown.value = showDropDown.value.not()
                                    },
                                    leadingIcon = {
                                        RadioButton(
                                            selected = selectedItem.intValue == index,
                                            onClick = {
                                                handleSorting(sortType, onAction)
                                                selectedItem.intValue = index
                                                showDropDown.value = showDropDown.value.not()
                                            })
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when {
                uiState.loading -> LoadingIndicator()
                uiState.error?.isEmpty() == true -> EmptyScreen(message = stringResource(R.string.no_favorite_recipes_found))
                else -> uiState.recipes.let { recipes ->
                    if (recipes.isEmpty()) {
                        EmptyScreen(message = stringResource(R.string.no_favorite_recipes_found))
                    } else {
                        LazyColumn {

                            val recipesMapping = recipes.map {
                                Recipe(
                                    idMeal = it.idMeal,
                                    strArea = it.strArea,
                                    strMeal = it.strMeal,
                                    strMealThumb = it.strMealThumb,
                                    strCategory = it.strCategory,
                                    strTags = it.strTags,
                                    strYoutube = it.strYoutube,
                                    strInstruction = it.strInstruction,
                                    ingredients = it.ingredients,
                                )
                            }

                            items(recipesMapping) { recipe ->
                                DishCard(recipe) {
                                    onAction.invoke(FavoriteAction.ShowDetail(it))
                                }
                            }
                        }
                    }

                }
            }
        }

    }

}

private fun handleSorting(
    sortType: FavoriteViewModel.SortType,
    onAction: (FavoriteAction) -> Unit
) {
    when (sortType) {
        FavoriteViewModel.SortType.AlphabeticalOrder -> {
            onAction.invoke(FavoriteAction.AlphabeticalOrder)
        }

        FavoriteViewModel.SortType.LessIngredientOrder -> {
            onAction.invoke(FavoriteAction.LessIngredientOrder)
        }

        FavoriteViewModel.SortType.RestSort -> {
            onAction.invoke(FavoriteAction.RestSort)
        }
    }
}

@Preview
@Composable
private fun PreviewFavoriteScreen() {
    FavoriteScreen(
        uiState = FavoriteUiState(),
        events = flow { },
        onAction = {},
        rememberNavController()
    )
}
