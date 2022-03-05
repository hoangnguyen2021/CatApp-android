package com.example.catapp.domain.usecase

import com.example.catapp.data.model.catbreeds.CatBreed
import com.example.catapp.domain.repository.CatRepository

class SaveCatBreedUseCase(private val catRepository: CatRepository) {
    suspend fun execute(catBreed: CatBreed) {
        catRepository.saveCatBreed(catBreed)
    }
}