package com.example.ui.screens.recipe_list

sealed class RecipeListEvent {
    data class OnSearchQueryChange(val query: String) : RecipeListEvent()
    object OnSearch : RecipeListEvent()
}