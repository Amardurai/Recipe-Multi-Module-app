package com.example.recipe_multi_module_app.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.feature.search.domain.model.Recipe
import com.example.search.data.local.RecipeDao

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}