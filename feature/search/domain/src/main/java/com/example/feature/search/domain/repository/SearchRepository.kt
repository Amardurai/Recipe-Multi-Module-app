package com.example.feature.search.domain.repository

import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails

interface SearchRepository {

    suspend fun getRecipes(query: String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>
}