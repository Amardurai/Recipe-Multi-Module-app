package com.example.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.common.navigation.Dest
import com.example.common.navigation.FeatureApi
import com.example.common.navigation.SubGraphDest

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {

    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation<SubGraphDest.RecipeSearch>(startDestination = Dest.RecipeList) {
            composable<Dest.RecipeList> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Recipe List")
                }
            }
            composable<Dest.RecipeDetail> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Recipe Detail")
                }
            }
        }
    }

}