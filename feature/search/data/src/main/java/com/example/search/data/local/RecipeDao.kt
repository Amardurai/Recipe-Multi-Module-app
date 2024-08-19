package com.example.search.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.feature.search.domain.model.Recipe
import com.example.feature.search.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeDetails):Long

    @Delete
    suspend fun delete(recipe: RecipeDetails):Int

    @Query("SELECT * FROM RecipeDetails")
    fun getAllRecipe(): Flow<List<RecipeDetails>>

}