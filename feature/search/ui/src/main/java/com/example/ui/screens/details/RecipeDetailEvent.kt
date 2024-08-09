package com.example.ui.screens.details

import com.example.feature.search.domain.model.RecipeDetails

sealed class RecipeDetailEvent {
    data class OnRecipeSelected(val id: String) : RecipeDetailEvent()
}