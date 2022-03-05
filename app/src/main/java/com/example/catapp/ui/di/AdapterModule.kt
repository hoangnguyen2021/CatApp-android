package com.example.catapp.ui.di

import com.example.catapp.ui.adapter.CatBreedsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideCatBreedsAdapter(): CatBreedsAdapter {
        return CatBreedsAdapter()
    }
}