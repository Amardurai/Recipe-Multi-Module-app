package com.example.recipe_multi_module_app.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipe_multi_module_app.local.AppDataBase
import com.example.recipe_multi_module_app.navigation.NavigationSubGraphs
import com.example.search.data.local.RecipeDao
import com.example.feature.search.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNavigationSubGraphs(searchFeatureApi: SearchFeatureApi): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi)
    }

    @Provides
    @Singleton
    fun provideRecipeDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "recipe_db").build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(roomDatabase: AppDataBase): RecipeDao {
        return roomDatabase.recipeDao
    }

}