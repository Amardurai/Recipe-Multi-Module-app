package com.example.ui.screens.recipe_list

sealed interface RecipeListAction {
    data class OnSearchQueryChange(val query: String) : RecipeListAction
    data class OnRecipeItemClicked(val id: String) : RecipeListAction
}