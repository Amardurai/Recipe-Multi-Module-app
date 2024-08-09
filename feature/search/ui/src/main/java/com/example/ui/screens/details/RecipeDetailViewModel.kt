package com.example.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.NetworkResult
import com.example.feature.search.domain.use_case.GetRecipeDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(private val getRecipeDetailUseCase: GetRecipeDetailUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RecipeDetailState())
    val uiState = _uiState.asStateFlow()

    fun getRecipeDetails(id: String) = getRecipeDetailUseCase(id).onEach { networkResult ->
        when (networkResult) {
            NetworkResult.Loading -> _uiState.update { RecipeDetailState(loading = true) }
            is NetworkResult.Error -> _uiState.update { RecipeDetailState(error = networkResult.message) }
            is NetworkResult.Success -> _uiState.update { RecipeDetailState(recipe = networkResult.data) }
        }
    }.launchIn(viewModelScope)

    fun onEvent(recipeDetailEvent: RecipeDetailEvent) {
        when (recipeDetailEvent) {
            is RecipeDetailEvent.OnRecipeSelected -> {
                getRecipeDetails(recipeDetailEvent.id)
            }
        }
    }

}