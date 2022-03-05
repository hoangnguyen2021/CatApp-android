package com.example.catapp.ui.di

import android.app.Application
import com.example.catapp.domain.usecase.GetCatBreedUseCase
import com.example.catapp.domain.usecase.GetCatBreedsUseCase
import com.example.catapp.domain.usecase.SaveCatBreedUseCase
import com.example.catapp.ui.viewmodel.LoginViewModelFactory
import com.example.catapp.ui.viewmodel.CatBreedsViewModelFactory
import com.example.catapp.ui.viewmodel.RegisterViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideCatBreedsViewModelFactory(
        application: Application,
        getCatBreedsUseCase: GetCatBreedsUseCase,
        getCatBreedUseCase: GetCatBreedUseCase,
        saveCatBreedUseCase: SaveCatBreedUseCase
    ): CatBreedsViewModelFactory {
        return CatBreedsViewModelFactory(
            application,
            getCatBreedsUseCase,
            getCatBreedUseCase,
            saveCatBreedUseCase
        )
    }

    @Singleton
    @Provides
    fun provideLoginViewModelFactory(auth: FirebaseAuth): LoginViewModelFactory {
        return LoginViewModelFactory(auth)
    }

    @Singleton
    @Provides
    fun provideRegisterViewModelFactory(auth: FirebaseAuth): RegisterViewModelFactory {
        return RegisterViewModelFactory(auth)
    }
}