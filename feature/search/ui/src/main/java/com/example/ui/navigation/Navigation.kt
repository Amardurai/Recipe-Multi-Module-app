package com.example.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.common.navigation.Dest
import com.example.common.navigation.FeatureApi
import com.example.common.navigation.SubGraphDest
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
                RecipeListScreen(uiState.value,events, viewModel::onAction,navHostController)
            }
            composable<Dest.RecipeDetail> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Recipe Detail")
                }
            }
        }
    }

}