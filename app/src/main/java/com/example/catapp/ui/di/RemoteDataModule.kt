package com.example.catapp.ui.di

import com.example.catapp.data.api.CatAPIService
import com.example.catapp.data.repository.datasource.CatRemoteDataSource
import com.example.catapp.data.repository.datasourceimpl.CatRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideCatRemoteDataSource(newsAPIService: CatAPIService): CatRemoteDataSource {
        return CatRemoteDataSourceImpl(newsAPIService)
    }
}