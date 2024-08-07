package com.example.feature.search.domain.model

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

data class RecipeDetails(
    val idMeal: String?,
    val strArea: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strCategory: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strInstruction: String?,
    val ingredients: List<Pair<String, String>?>,
)