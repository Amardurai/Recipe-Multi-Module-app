package com.example.ui.screens.details

import com.example.feature.search.domain.model.RecipeDetails

data class RecipeDetailState(
    val loading: Boolean = false,
    val error: String = "",
    val recipe: RecipeDetails? = null
)