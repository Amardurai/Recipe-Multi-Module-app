package com.example.feature.search.ui.screens.details

sealed interface RecipeDetailEvent {
    data class Error(val message:String): RecipeDetailEvent
    data object GoToRecipeListScreen : RecipeDetailEvent
    data object OnRecipeDeleted : RecipeDetailEvent
    data object OnRecipeInserted : RecipeDetailEvent
}