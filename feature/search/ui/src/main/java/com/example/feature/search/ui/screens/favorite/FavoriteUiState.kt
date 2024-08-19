package com.example.feature.search.ui.screens.favorite

import com.example.feature.search.domain.model.RecipeDetails

data class FavoriteUiState(
    val recipes: List<RecipeDetails> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
