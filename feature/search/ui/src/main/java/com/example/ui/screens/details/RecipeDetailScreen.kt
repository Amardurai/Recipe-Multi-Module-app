package com.example.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.common.components.EmptyScreen
import com.example.common.components.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(uiState: RecipeDetailState) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.recipe?.strMeal.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    )
    { innerPadding ->
        Surface(Modifier.padding(innerPadding)) {
            when {
                uiState.loading -> LoadingIndicator()
                uiState.error.isNotEmpty() -> EmptyScreen(message = uiState.error)
                else -> {
                    uiState.recipe?.let { recipe ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            AsyncImage(
                                modifier = Modifier.size(300.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(recipe.strMealThumb).crossfade(true).build(),
                                contentDescription = "Recipe Image",
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = recipe.strInstruction.toString(),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            recipe.ingredients.forEach {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    AsyncImage(
                                        model = getIngredientImageUrl(it?.first.toString()),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .background(color = Color.White, shape = CircleShape)
                                            .clip(CircleShape),
                                    )
                                    Text(
                                        text = it?.second.toString(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Watch Youtube video",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

fun getIngredientImageUrl(name: String) = "https//www.themealdb.com/images/ingredients/$name.png"

@Preview
@Composable
private fun RecipeDetailScreenPreview() {

}