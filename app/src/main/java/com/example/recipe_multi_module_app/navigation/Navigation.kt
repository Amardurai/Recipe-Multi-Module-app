package com.example.recipe_multi_module_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.common.navigation.Dest

@Composable
fun RecipeNavigation(modifier: Modifier = Modifier, navigationSubGraphs: NavigationSubGraphs) {

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = Dest.RecipeList) {
        navigationSubGraphs.searchFeatureAPI.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }

}