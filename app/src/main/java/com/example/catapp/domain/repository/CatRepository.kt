package com.example.catapp.domain.repository

import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.model.catbreeds.CatBreeds
import com.example.catapp.data.model.catbreeds.CatBreed
import com.example.catapp.data.util.Resource

interface CatRepository {
    suspend fun getCatBreeds() : Resource<CatBreeds>
    suspend fun getCatBreed(breedId: String) : Resource<CatBreedDetails>
    suspend fun saveCatBreed(catBreed: CatBreed)
}