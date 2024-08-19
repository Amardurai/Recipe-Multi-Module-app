package com.example.feature.search.ui.screens.favorite

sealed interface FavoriteAction {
    data object AlphabeticalOrder : FavoriteAction
    data object LessIngredientOrder : FavoriteAction
    data object RestSort : FavoriteAction
    data class ShowDetail(val id: String) : FavoriteAction
}