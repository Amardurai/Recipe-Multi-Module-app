package com.example.feature.search.ui.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.search.domain.model.RecipeDetails
import com.example.feature.search.domain.use_case.local.GetAllRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val getAllRecipesUseCase: GetAllRecipesUseCase) :
    ViewModel() {

    private var originalList = listOf<RecipeDetails>()

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState : StateFlow<FavoriteUiState> get() = _uiState.asStateFlow()

    private val eventChannel = Channel<FavoriteEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() {
        getAllRecipesUseCase.invoke().onEach { recipes ->
            originalList = recipes
            _uiState.value = _uiState.value.copy(recipes = recipes)
        }.launchIn(viewModelScope)

    }

    private fun sortRecipes(sortType: SortType) {
        when (sortType) {
            SortType.AlphabeticalOrder -> _uiState.value =
                _uiState.value.copy(recipes = originalList.sortedBy { it.strMeal })

            SortType.LessIngredientOrder -> _uiState.value =
                _uiState.value.copy(recipes = originalList.sortedBy { it.ingredients.size })

            SortType.RestSort -> _uiState.value = _uiState.value.copy(recipes = originalList)
        }
    }

    fun onAction(action: FavoriteAction) {
        when (action) {
            FavoriteAction.AlphabeticalOrder -> sortRecipes(SortType.AlphabeticalOrder)
            FavoriteAction.LessIngredientOrder -> sortRecipes(SortType.LessIngredientOrder)
            FavoriteAction.RestSort -> sortRecipes(SortType.RestSort)
            is FavoriteAction.ShowDetail -> {
                eventChannel.trySend(FavoriteEvent.GoToRecipeDetail(action.id))
            }
        }
    }

    enum class SortType(val value: String) {
        AlphabeticalOrder("Alphabetical Order"),
        LessIngredientOrder("Less Ingredient"),
        RestSort("Reset")
    }

}