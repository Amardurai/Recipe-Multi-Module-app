package com.example.ui.di

import com.example.ui.navigation.SearchFeatureApi
import com.example.ui.navigation.SearchFeatureApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideSearchFeature(): SearchFeatureApi = SearchFeatureApiImpl()

}