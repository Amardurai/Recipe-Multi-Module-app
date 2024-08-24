package com.example.feature.search.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.common.navigation.FeatureApi
import com.example.common.navigation.SubGraphDest
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.ui.screens.details.RecipeDetailAction
import com.example.feature.search.ui.screens.details.RecipeDetailScreen
import com.example.feature.search.ui.screens.details.RecipeDetailViewModel
import com.example.feature.search.ui.screens.favorite.FavoriteScreen
import com.example.feature.search.ui.screens.favorite.FavoriteViewModel
import com.example.feature.search.ui.screens.recipe_list.RecipeListScreen
import com.example.feature.search.ui.screens.recipe_list.RecipeListViewModel
import kotlin.reflect.typeOf

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

            composable<Dest.RecipeDetail>(
                typeMap = mapOf(
                    typeOf<Recipe>() to CustomNavType.RecipeDetailsNavType
                )
            ) {
                val viewModel = hiltViewModel<RecipeDetailViewModel>()
                val mealId = it.toRoute<Dest.RecipeDetail>().recipe

                LaunchedEffect(Unit) {
                    viewModel.onAction(RecipeDetailAction.UpdateRecipeDetail(mealId))
                }

                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                val events = viewModel.events
                RecipeDetailScreen(uiState.value, events, viewModel::onAction, navHostController)
            }
            composable<Dest.Favorite> {
                val viewModel = hiltViewModel<FavoriteViewModel>()
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                val events = viewModel.events
                FavoriteScreen(uiState.value, events, viewModel::onAction, navHostController)
            }

        }
    }

}