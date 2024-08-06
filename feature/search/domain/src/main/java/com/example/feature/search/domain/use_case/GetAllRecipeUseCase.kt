package com.example.feature.search.domain.use_case

import com.example.common.NetworkResult
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecipeUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(query: String) = flow<NetworkResult<List<Recipe>>> {
        emit(NetworkResult.Loading)
        val response = searchRepository.getRecipes(query)
        if (response.isSuccess)
            emit(NetworkResult.Success(response.getOrDefault(emptyList())))
        else
            emit(NetworkResult.Error(response.exceptionOrNull()?.message ?: "Something went wrong"))
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Something went wrong"))
    }.flowOn(Dispatchers.IO)

}