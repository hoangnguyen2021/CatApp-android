package com.example.catapp.domain.usecase

import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.util.Resource
import com.example.catapp.domain.repository.CatRepository

class GetCatBreedUseCase(private val catRepository: CatRepository) {
    suspend fun execute(breedId: String): Resource<CatBreedDetails> {
        return catRepository.getCatBreed(breedId)
    }
}