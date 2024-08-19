package com.example.feature.search.ui.screens.favorite

sealed interface FavoriteEvent {
    data class GoToRecipeDetail(val id: String) : FavoriteEvent
}