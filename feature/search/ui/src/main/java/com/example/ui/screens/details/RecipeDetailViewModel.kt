package com.example.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.NetworkResult
import com.example.feature.search.domain.use_case.GetRecipeDetailUseCase
import com.example.ui.screens.recipe_list.RecipeListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val getRecipeDetailUseCase: GetRecipeDetailUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RecipeDetailState())
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<RecipeDetailEvent>()
    val events = eventChannel.receiveAsFlow()

    private fun getRecipeDetails(id: String) = getRecipeDetailUseCase(id).onEach { networkResult ->
        when (networkResult) {
            NetworkResult.Loading -> _uiState.update { RecipeDetailState(loading = true) }
            is NetworkResult.Error -> eventChannel.trySend(RecipeDetailEvent.Error(networkResult.message))
            is NetworkResult.Success -> _uiState.update { RecipeDetailState(recipe = networkResult.data) }
        }
    }.launchIn(viewModelScope)

    fun onAction(recipeDetailAction: RecipeDetailAction){
        when(recipeDetailAction){
            is RecipeDetailAction.FetchRecipeDetail ->  getRecipeDetails(recipeDetailAction.id)
            RecipeDetailAction.OnBackButtonClicked -> { eventChannel.trySend(RecipeDetailEvent.GoToRecipeListScreen) }
            is RecipeDetailAction.OnDeleteClicked -> { }
            is RecipeDetailAction.OnFavoriteClicked -> { }
        }
    }

}