package com.example.feature.search.ui.screens.details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.common.components.LoadingIndicator
import com.example.common.components.ObserveAsEvent
import com.example.feature.search.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias RecipeData = Pair<String, String>?

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    uiState: RecipeDetailState,
    events: Flow<RecipeDetailEvent>,
    onAction: (RecipeDetailAction) -> Unit,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    ObserveAsEvent(flow = events) { event ->
        when (event) {
            is RecipeDetailEvent.Error -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                .show()

            RecipeDetailEvent.GoToRecipeListScreen -> navHostController.popBackStack()
            RecipeDetailEvent.OnRecipeDeleted -> Toast.makeText(
                context,
                "Recipe is removed from favorite",
                Toast.LENGTH_SHORT
            ).show()

            RecipeDetailEvent.OnRecipeInserted -> Toast.makeText(
                context,
                "Recipe is added to favorite",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.recipe?.strMeal.orEmpty(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }, navigationIcon = {
                    IconButton(onClick = { onAction.invoke(RecipeDetailAction.OnBackButtonClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onAction.invoke(
                            RecipeDetailAction.OnFavoriteClicked(
                                uiState.recipe
                            )
                        )
                    }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                    }
                    IconButton(onClick = {
                        onAction.invoke(
                            RecipeDetailAction.OnDeleteClicked(
                                uiState.recipe
                            )
                        )
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (uiState.loading) {
                LoadingIndicator()
            }
            uiState.recipe?.let { recipe ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            contentScale = ContentScale.Crop,
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(recipe.strMealThumb).crossfade(true).build(),
                            contentDescription = "Recipe Image",
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = recipe.strInstruction.orEmpty(),
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        RecipeList(recipe = recipe)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Watch Youtube Video",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        recipe.strYoutube?.let { youtubeUrl ->
                            val videoId = getYoutubeVideoId(youtubeUrl)
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(youtubeUrl)
                                        )
                                        context.startActivity(intent)
                                    },
                                elevation = CardDefaults.cardElevation(4.dp),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data("https://img.youtube.com/vi/$videoId/hqdefault.jpg")
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = "YouTube Thumbnail",
                                        contentScale = ContentScale.Crop
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.PlayArrow,
                                        contentDescription = "Play",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(64.dp)
                                            .background(
                                                Color.White.copy(alpha = 0.6f),
                                                CircleShape
                                            )
                                            .padding(8.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


fun getYoutubeVideoId(url: String): String {
    return url.substringAfter("v=").substringBefore("&")
}

@Composable
fun RecipeList(recipe: RecipeDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Recipes",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(12.dp))

        recipe.ingredients.forEach {
            if (it?.first?.isNotEmpty() == true) {
                RecipeCard(it)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun RecipeCard(it: RecipeData) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = getIngredientImageUrl(it?.first.orEmpty()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = it?.first.orEmpty(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = it?.second.orEmpty(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    RecipeCard(it = Pair("Olive oil", "800G"))
}

fun getIngredientImageUrl(name: String) = "https://www.themealdb.com/images/ingredients/$name.png"

@Preview(showBackground = true)
@Composable
private fun RecipeDetailScreenPreview() {
    val sampleRecipe = RecipeDetails(
        strMeal = "Spaghetti Bolognese",
        strMealThumb = "https://www.themealdb.com/images/media/meals/sutysw1468247559.jpg",
        strInstruction = "Cook spaghetti and prepare the Bolognese sauce...Cook spaghetti and prepare the Bolognese sauce...Cook spaghetti and prepare the Bolognese sauce...",
        ingredients = listOf(
            Pair("Tomatoes", "4 large"),
        ),
        idMeal = "idMeal",
        strArea = "strArea",
        strCategory = "strCategory",
        strTags = "strTags",
        strYoutube = "strYoutube",

        )
    val sampleState = RecipeDetailState(
        recipe = sampleRecipe,
        loading = false,
    )
    RecipeDetailScreen(
        uiState = sampleState,
        events = flow { },
        onAction = {},
        navHostController = rememberNavController()
    )
}
