package com.example.feature.search.domain.use_case.local

import com.example.feature.search.domain.model.RecipeDetails
import com.example.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(val searchRepository: SearchRepository) {

    operator fun invoke(recipe: RecipeDetails) = flow {
        val rowAffected = searchRepository.deleteRecipe(recipe)
        if (rowAffected > 0) emit(true) else emit(false)
    }.flowOn(Dispatchers.IO)

}