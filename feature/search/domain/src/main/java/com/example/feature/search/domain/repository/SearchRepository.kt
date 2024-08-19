package com.example.feature.search.domain.repository

import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getRecipes(query: String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>

    suspend fun insertRecipe(recipe: RecipeDetails):Long

    suspend fun deleteRecipe(recipe: RecipeDetails):Int

    fun getAllRecipe(): Flow<List<RecipeDetails>>
}