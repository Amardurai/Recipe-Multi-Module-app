package com.example.feature.search.domain.use_case.local

import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails
import com.example.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertRecipeUseCase @Inject constructor(val searchRepository: SearchRepository) {

    operator fun invoke(recipe: Recipe) = flow {
        val id = searchRepository.insertRecipe(recipe)
        if (id != -1L) emit(true) else emit(false)
    }.flowOn(Dispatchers.IO)

}