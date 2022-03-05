package com.example.catapp.ui.di

import com.example.catapp.domain.repository.CatRepository
import com.example.catapp.domain.usecase.GetCatBreedUseCase
import com.example.catapp.domain.usecase.GetCatBreedsUseCase
import com.example.catapp.domain.usecase.SaveCatBreedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetCatBreedsUseCase(catRepository: CatRepository): GetCatBreedsUseCase {
        return GetCatBreedsUseCase(catRepository)
    }

    @Singleton
    @Provides
    fun provideGetCatBreedUseCase(catRepository: CatRepository): GetCatBreedUseCase {
        return GetCatBreedUseCase(catRepository)
    }

    @Singleton
    @Provides
    fun provideSaveCatBreedUseCase(catRepository: CatRepository): SaveCatBreedUseCase {
        return SaveCatBreedUseCase(catRepository)
    }
}