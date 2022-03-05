package com.example.catapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catapp.domain.usecase.GetCatBreedUseCase
import com.example.catapp.domain.usecase.GetCatBreedsUseCase
import com.example.catapp.domain.usecase.SaveCatBreedUseCase

class CatBreedsViewModelFactory(
    private val app: Application,
    private val getCatBreedsUseCase: GetCatBreedsUseCase,
    private val getCatBreedUseCase: GetCatBreedUseCase,
    private val saveCatBreedUseCase: SaveCatBreedUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatBreedsViewModel::class.java)) {
            return CatBreedsViewModel(
                app,
                getCatBreedsUseCase,
                getCatBreedUseCase,
                saveCatBreedUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}