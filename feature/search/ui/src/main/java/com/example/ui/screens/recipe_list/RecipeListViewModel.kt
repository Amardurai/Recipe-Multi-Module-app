package com.example.ui.screens.recipe_list

import androidx.lifecycle.ViewModel
import com.example.feature.search.domain.use_case.GetAllRecipeUseCase
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val gelAllRecipesUseCase: GetAllRecipeUseCase) :
    ViewModel() {

}