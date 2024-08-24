package com.example.feature.search.ui.navigation

import com.example.feature.search.domain.model.Recipe
import kotlinx.serialization.Serializable

sealed class Dest {

    @Serializable
    data object RecipeList : Dest()

    @Serializable
    data class RecipeDetail(val recipe: Recipe): Dest()

    @Serializable
    data object Favorite: Dest()
}