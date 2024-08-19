package com.example.feature.search.ui.screens.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.NetworkResult
import com.example.feature.search.domain.use_case.remote.GetAllRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val gelAllRecipesUseCase: GetAllRecipeUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RecipeListState())
    val uiState :StateFlow<RecipeListState> get() = _uiState.asStateFlow()

    private val eventChannel = Channel<RecipeListEvent>()
    val events = eventChannel.receiveAsFlow()

    private var searchJob: Job? = null

    init {
        debounceSearch("Chicken")
    }

    private fun search(query: String) = gelAllRecipesUseCase(query).onEach { result ->
        when (result) {
            NetworkResult.Loading -> _uiState.update { RecipeListState(isLoading = true) }
            is NetworkResult.Error -> eventChannel.trySend(RecipeListEvent.OnError(result.message))
            is NetworkResult.Success -> _uiState.update { RecipeListState(recipes = result.data) }
        }
    }.launchIn(viewModelScope)

    private fun debounceSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            search(query)
        }
    }

    fun onAction(recipeListAction: RecipeListAction) {
        when (recipeListAction) {
            is RecipeListAction.OnSearchQueryChange -> debounceSearch(recipeListAction.query)

            is RecipeListAction.OnRecipeItemClicked -> {
                eventChannel.trySend(RecipeListEvent.GoToDetailScreen(recipeListAction.id))
            }

            RecipeListAction.OnFavoriteClicked -> eventChannel.trySend(RecipeListEvent.GoToFavoriteScreen)
        }

    }

}