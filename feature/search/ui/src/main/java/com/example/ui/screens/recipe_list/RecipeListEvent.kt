package com.example.ui.screens.recipe_list

sealed interface RecipeListEvent {
    data class OnError(val message: String) : RecipeListEvent
    data class GoToDetailScreen(val mealID:String) : RecipeListEvent
}