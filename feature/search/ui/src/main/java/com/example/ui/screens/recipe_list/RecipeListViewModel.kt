package com.example.ui.screens.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.NetworkResult
import com.example.feature.search.domain.use_case.GetAllRecipeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val gelAllRecipesUseCase: GetAllRecipeUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RecipeListState())
    val uiState = _uiState.asStateFlow()

    fun search(query: String) = gelAllRecipesUseCase(query).onEach { result ->
        when (result) {
            NetworkResult.Loading -> _uiState.update { RecipeListState(isLoading = true) }
            is NetworkResult.Error -> _uiState.update { RecipeListState(error = result.message) }
            is NetworkResult.Success -> _uiState.update { RecipeListState(recipes = result.data) }
        }
    }.launchIn(viewModelScope)


    fun onEvent(recipeListEvent: RecipeListEvent) {
        when (recipeListEvent) {
            is RecipeListEvent.OnSearchQueryChange -> search(recipeListEvent.query)
        }

    }

}