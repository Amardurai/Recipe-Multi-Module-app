package com.example.feature.search.ui.screens.recipe_list

import com.example.feature.search.domain.model.Recipe

sealed interface RecipeListEvent {
    data class OnError(val message: String) : RecipeListEvent
    data class GoToDetailScreen(val recipe:Recipe) : RecipeListEvent
    data object GoToFavoriteScreen : RecipeListEvent
}