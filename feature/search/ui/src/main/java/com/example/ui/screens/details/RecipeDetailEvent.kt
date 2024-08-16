package com.example.ui.screens.details

sealed class RecipeDetailEvent {
    data class FetchRecipeDetail(val id: String) : RecipeDetailEvent()
}