package com.example.feature.search.domain.use_case.local

import com.example.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    operator fun invoke()= searchRepository.getAllRecipe()
}