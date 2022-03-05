package com.example.catapp.ui.di

import com.example.catapp.data.db.CatBreedDAO
import com.example.catapp.data.repository.datasource.CatLocalDataSource
import com.example.catapp.data.repository.datasourceimpl.CatLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(catBreedDAO: CatBreedDAO): CatLocalDataSource {
        return CatLocalDataSourceImpl(catBreedDAO)
    }
}