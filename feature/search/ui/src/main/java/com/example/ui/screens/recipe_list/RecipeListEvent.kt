package com.example.ui.screens.recipe_list

sealed class RecipeListEvent {
    data class OnSearchQueryChange(val query: String) : RecipeListEvent()
    data class onRecipeItemSelected(val id: String) : RecipeListEvent()
}