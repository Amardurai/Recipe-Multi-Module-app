package com.example.ui.screens.details

import com.example.feature.search.domain.model.RecipeDetails

sealed interface RecipeDetailAction {
    data object OnBackButtonClicked : RecipeDetailAction
    data class OnDeleteClicked(val recipe: RecipeDetails) : RecipeDetailAction
    data class OnFavoriteClicked(val recipe: RecipeDetails) : RecipeDetailAction
    data class FetchRecipeDetail(val id: String):RecipeDetailAction
}