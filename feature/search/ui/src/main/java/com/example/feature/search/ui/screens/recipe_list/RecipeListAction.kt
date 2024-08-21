package com.example.feature.search.ui.screens.recipe_list

import com.example.feature.search.domain.model.Country

sealed interface RecipeListAction {
    data class OnSearchQueryChange(val query: String) : RecipeListAction
    data class OnRecipeItemClicked(val id: String) : RecipeListAction
    data class OnCountryFilterChange(val selectedCountry: List<Country>) : RecipeListAction
    data object OnFavoriteClicked : RecipeListAction
}