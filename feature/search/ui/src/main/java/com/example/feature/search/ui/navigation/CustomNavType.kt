package com.example.feature.search.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val RecipeDetailsNavType = object : NavType<Recipe>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Recipe? {
            return Json.decodeFromString(bundle.getString(key)?:return null)
        }

        override fun parseValue(value: String): Recipe {
            return Json.decodeFromString(value)
        }

        override fun serializeAsValue(value: Recipe): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Recipe) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}