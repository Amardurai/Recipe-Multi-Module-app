package com.example.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.common.navigation.Dest
import com.example.common.navigation.FeatureApi
import com.example.common.navigation.SubGraphDest
import com.example.ui.screens.details.RecipeDetailEvent
import com.example.ui.screens.details.RecipeDetailScreen
import com.example.ui.screens.details.RecipeDetailViewModel
import com.example.ui.screens.recipe_list.RecipeListScreen
import com.example.ui.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {

    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation<SubGraphDest.RecipeSearch>(startDestination = Dest.RecipeList) {
            composable<Dest.RecipeList> {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                val events = viewModel.events
                RecipeListScreen(uiState.value, events, viewModel::onAction, navHostController)
            }
            composable<Dest.RecipeDetail> {
                val viewModel = hiltViewModel<RecipeDetailViewModel>()
                val mealId = it.toRoute<Dest.RecipeDetail>().id

                viewModel.onEvent(RecipeDetailEvent.FetchRecipeDetail(mealId))

                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                RecipeDetailScreen(uiState.value)
            }
        }
    }

}