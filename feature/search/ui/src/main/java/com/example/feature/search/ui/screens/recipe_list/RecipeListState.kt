package com.example.feature.search.ui.screens.recipe_list

import com.example.feature.search.domain.model.Country
import com.example.feature.search.domain.model.Recipe

data class RecipeListState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val country: List<Country> = emptyList(),
)