package com.example.search.data.di

import com.example.search.data.remote.SearchAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://www.themealdb.com/"

@Module
@InstallIn(SingletonComponent::class)
object SearchModuleDI {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchAPIService {
        return retrofit.create(SearchAPIService::class.java)
    }

}