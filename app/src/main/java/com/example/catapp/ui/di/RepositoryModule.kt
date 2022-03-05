package com.example.catapp.ui.di

import com.example.catapp.data.repository.CatRepositoryImpl
import com.example.catapp.data.repository.datasource.CatLocalDataSource
import com.example.catapp.data.repository.datasource.CatRemoteDataSource
import com.example.catapp.domain.repository.CatRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideCatRepository(
        catRemoteDataSource: CatRemoteDataSource,
        catLocalDataSource: CatLocalDataSource
    ): CatRepository {
        return CatRepositoryImpl(catRemoteDataSource, catLocalDataSource)
    }
}