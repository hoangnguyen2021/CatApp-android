package com.example.catapp.domain.usecase

import com.example.catapp.data.model.catbreeds.CatBreeds
import com.example.catapp.data.util.Resource
import com.example.catapp.domain.repository.CatRepository

class GetCatBreedsUseCase(private val catRepository: CatRepository) {
    suspend fun execute(): Resource<CatBreeds> {
        return catRepository.getCatBreeds()
    }
}