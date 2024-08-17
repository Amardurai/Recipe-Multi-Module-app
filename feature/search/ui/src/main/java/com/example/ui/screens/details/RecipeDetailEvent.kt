package com.example.ui.screens.details

sealed interface RecipeDetailEvent {
    data class Error(val message:String):RecipeDetailEvent
    data object GoToRecipeListScreen : RecipeDetailEvent
}