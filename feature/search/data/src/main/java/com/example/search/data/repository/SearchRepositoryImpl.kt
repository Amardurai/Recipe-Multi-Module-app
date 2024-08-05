package com.example.search.data.repository

import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails
import com.example.feature.search.domain.repository.SearchRepository
import com.example.search.data.mapper.toDomain
import com.example.search.data.remote.SearchAPIService
import java.io.IOException
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl(val searchAPIService: SearchAPIService) : SearchRepository {
    override suspend fun getRecipes(query: String): Result<List<Recipe>> {
        return try {
            val response = searchAPIService.getRecipes(query)
            if (response.isSuccessful) {
                Result.success(response.body()?.meals?.toDomain() ?: emptyList())
            } else {
                Result.success(emptyList())
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return try {
            val response = searchAPIService.getRecipeDetails(id)
            if (response.isSuccessful) {
                response.body()?.meals?.first()?.toDomain()?.let { Result.success(it) }
                    ?: run { Result.failure(Exception("No data found")) }
            } else {
                Result.failure(Exception("No data found"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}