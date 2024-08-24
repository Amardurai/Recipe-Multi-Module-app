package com.example.feature.search.ui.screens.favorite

import com.example.feature.search.domain.model.Recipe

sealed interface FavoriteEvent {
    data class GoToRecipeDetail(val recipe: Recipe) : FavoriteEvent
}