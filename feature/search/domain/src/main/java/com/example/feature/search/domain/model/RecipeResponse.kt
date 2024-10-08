package com.example.feature.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = false)
    val idMeal: String,
    val strArea: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strCategory: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strInstruction: String?,
    val ingredients: List<Pair<String, String>?>,
)

data class RecipeDetails(
    val idMeal: String,
    val strArea: String?,
    val strMeal: String? = "",
    val strMealThumb: String?,
    val strCategory: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strInstruction: String?,
    val ingredients: List<Pair<String, String>?>,
)