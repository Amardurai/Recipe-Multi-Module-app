package com.example.feature.search.ui.screens.details

import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails
import com.google.android.material.color.utilities.TonalPalette

data class RecipeDetailState(
    val loading: Boolean = false,
    val recipe: Recipe? = null,
    val colorPalette: Map<String,String> = mapOf()
)