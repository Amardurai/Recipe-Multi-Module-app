package com.example.feature.search.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.NetworkResult
import com.example.feature.search.domain.use_case.local.DeleteRecipeUseCase
import com.example.feature.search.domain.use_case.local.InsertRecipeUseCase
import com.example.feature.search.domain.use_case.remote.GetRecipeDetailUseCase
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
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
) :
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

    fun onAction(recipeDetailAction: RecipeDetailAction) {
        when (recipeDetailAction) {
            is RecipeDetailAction.FetchRecipeDetail -> {
                getRecipeDetails(recipeDetailAction.id)
            }

            RecipeDetailAction.OnBackButtonClicked -> {
                eventChannel.trySend(RecipeDetailEvent.GoToRecipeListScreen)
            }

            is RecipeDetailAction.OnDeleteClicked -> {
                deleteRecipeUseCase(recipeDetailAction.recipe!!).onEach {
                    if (it) {
                        eventChannel.trySend(RecipeDetailEvent.OnRecipeDeleted)
                    }
                }.launchIn(viewModelScope)
            }

            is RecipeDetailAction.OnFavoriteClicked -> {
                insertRecipeUseCase(recipeDetailAction.recipe!!).onEach {
                    if (it) eventChannel.trySend(RecipeDetailEvent.OnRecipeInserted)
                }.launchIn(viewModelScope)
            }

            is RecipeDetailAction.OnPaletteExtracted -> {
                _uiState.update { it.copy(colorPalette = recipeDetailAction.palette) }
            }
        }
    }

}