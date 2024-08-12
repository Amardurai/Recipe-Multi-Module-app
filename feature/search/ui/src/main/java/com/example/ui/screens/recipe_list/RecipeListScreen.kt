package com.example.ui.screens.recipe_list

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RecipeListScreen(
    uiState: RecipeListState,
    onEvent: (RecipeListEvent) -> Unit
) {

    val query = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TextField(
                value = query.value, onValueChange = {
                    query.value = it
                    onEvent.invoke(RecipeListEvent.OnSearchQueryChange(it))
                },
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                placeholder = { Text(text = "Search Recipe") },
                trailingIcon = {
                    Icon(Icons.Filled.Search, "Search")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (uiState.error.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(uiState.error)
                }
            }

            RecipeList(uiState, onRecipeClick = {
                onEvent.invoke(RecipeListEvent.onRecipeItemSelected(it))
            })


        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipeList(uiState: RecipeListState, onRecipeClick: (String) -> Unit) {
    uiState.recipes.let { recipes ->
        LazyColumn {
            items(recipes, key = { it.idMeal.orEmpty() }) { recipe ->
                Card(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { onRecipeClick(recipe.idMeal.orEmpty()) }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(recipe.strMealThumb).crossfade(true).build(),
                        contentDescription = "Recipe Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = recipe.strMeal.orEmpty(),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = recipe.strInstruction.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (recipe.strTags?.isNotEmpty() == true) {
                        FlowRow {
                            recipe.strTags?.split(",")?.forEach {
                                Box(
                                    modifier = Modifier.background(
                                        Color.White,
                                        shape = RoundedCornerShape(12.dp)
                                    ).border(
                                        width = 1.dp,
                                        color = Color.Red,
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(it, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRecipeListScreen() {
    RecipeListScreen(RecipeListState()) {

    }
}