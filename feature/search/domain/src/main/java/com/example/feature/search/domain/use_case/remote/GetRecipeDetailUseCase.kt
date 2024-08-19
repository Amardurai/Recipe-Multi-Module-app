package com.example.feature.search.domain.use_case.remote

import com.example.common.utils.NetworkResult
import com.example.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    operator fun invoke(id: String) = flow {
        emit(NetworkResult.Loading)
        val response = searchRepository.getRecipeDetails(id)
        if (response.isSuccess)
            emit(NetworkResult.Success(response.getOrThrow()))
        else
            emit(NetworkResult.Error(response.exceptionOrNull()?.message ?: "Something went wrong"))
    }.catch {
        emit(NetworkResult.Error(it.message ?: "Something went wrong"))
    }.flowOn(Dispatchers.IO)
}