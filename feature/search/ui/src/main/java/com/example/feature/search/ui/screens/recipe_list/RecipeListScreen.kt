package com.example.feature.search.ui.screens.recipe_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.common.components.EmptyScreen
import com.example.common.components.LoadingIndicator
import com.example.common.components.ObserveAsEvent
import com.example.common.navigation.Dest
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.ui.R
import kotlinx.coroutines.flow.Flow

@Composable
fun RecipeListScreen(
    uiState: RecipeListState,
    events: Flow<RecipeListEvent>,
    onAction: (RecipeListAction) -> Unit,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    ObserveAsEvent(events) { event ->
        when (event) {

            is RecipeListEvent.OnError -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }

            is RecipeListEvent.GoToDetailScreen -> navHostController.navigate(
                Dest.RecipeDetail(
                    event.mealID
                )
            )

            RecipeListEvent.GoToFavoriteScreen -> navHostController.navigate(
                Dest.Favorite
            )

        }
    }
    val query = rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            SearchBar(query = query.value, onQueryChange = {
                query.value = it
                onAction.invoke(RecipeListAction.OnSearchQueryChange(it))
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction.invoke(RecipeListAction.OnFavoriteClicked) }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when {
                uiState.isLoading -> LoadingIndicator()

                else -> RecipeList(uiState.recipes) { recipeId ->
                    onAction(RecipeListAction.OnRecipeItemClicked(recipeId))
                }

            }
        }

    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {

    TextField(
        value = query, onValueChange = {
            onQueryChange(it)
        },
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = { Text(text = "Search Recipe") },
        leadingIcon = {
            Icon(Icons.Filled.Search, "Search")
        },
        shape = RoundedCornerShape(40.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun RecipeList(recipes: List<Recipe>, onRecipeClick: (String) -> Unit) {

    LazyColumn {
        items(recipes, key = { it.idMeal.orEmpty() }) { recipe ->
            DishCard(recipe, onRecipeClick)
        }
    }

}

@Composable
fun DishCard(recipe: Recipe, onRecipeClick: (String) -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onRecipeClick(recipe.idMeal.orEmpty()) }
    ) {
        RecipeImage(recipe)
        Spacer(modifier = Modifier.height(12.dp))
        RecipeDetail(recipe)

    }

}

@Composable
fun RecipeImage(recipe: Recipe) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipe.strMealThumb)
            .crossfade(true)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .build(),
        contentDescription = "Recipe Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    )
}

@Composable
fun RecipeDetail(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
    ) {

        Text(
            text = recipe.strMeal.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = recipe.strInstruction.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(12.dp))

        recipe.strTags?.let { RecipeTags(it) }


    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipeTags(tags: String) {
    FlowRow {
        tags.split(",").forEach { tag ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(Color.White, shape = RoundedCornerShape(24.dp))
                    .border(1.dp, Color.Red, RoundedCornerShape(24.dp))
                    .padding(vertical = 6.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(tag, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewRecipeListScreen() {

}