package com.example.search.data.di

import com.example.feature.search.domain.repository.SearchRepository
import com.example.search.data.remote.SearchAPIService
import com.example.search.data.repository.SearchRepositoryImpl
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
object SearchDataModuleDI {


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

    @Provides
    fun provideSearchRepository(searchAPIService: SearchAPIService): SearchRepository =
        SearchRepositoryImpl(searchAPIService)

}
