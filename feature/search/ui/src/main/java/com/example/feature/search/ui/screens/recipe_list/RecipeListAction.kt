package com.example.feature.search.ui.screens.recipe_list

sealed interface RecipeListAction {
    data class OnSearchQueryChange(val query: String) : RecipeListAction
    data class OnRecipeItemClicked(val id: String) : RecipeListAction
    data object OnFavoriteClicked : RecipeListAction
}