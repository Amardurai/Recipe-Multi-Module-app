package com.example.feature.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Recipe(
    val idMeal: String?,
    val strArea: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strCategory: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strInstruction: String?
)

@Entity
data class RecipeDetails(
    @PrimaryKey(autoGenerate = false)
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