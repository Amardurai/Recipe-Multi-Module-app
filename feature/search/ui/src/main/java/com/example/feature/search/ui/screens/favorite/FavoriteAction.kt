package com.example.feature.search.ui.screens.favorite

import com.example.feature.search.domain.model.Recipe

sealed interface FavoriteAction {
    data object AlphabeticalOrder : FavoriteAction
    data object LessIngredientOrder : FavoriteAction
    data object RestSort : FavoriteAction
    data class ShowDetail(val recipe: Recipe) : FavoriteAction
}