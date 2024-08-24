package com.example.feature.search.ui.screens.details

import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails

sealed interface RecipeDetailAction {
    data object OnBackButtonClicked : RecipeDetailAction
    data class OnDeleteClicked(val recipe: Recipe?) : RecipeDetailAction
    data class OnFavoriteClicked(val recipe: Recipe?) : RecipeDetailAction
    data class UpdateRecipeDetail(val recipe: Recipe): RecipeDetailAction
    data class OnPaletteExtracted(val palette: Map<String, String>) : RecipeDetailAction
}