package com.example.catapp.data.repository.datasource

import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.model.catbreeds.CatBreeds
import retrofit2.Response

interface CatRemoteDataSource {
    suspend fun getCatBreeds(): Response<CatBreeds>
    suspend fun getCatBreed(breedId: String): Response<CatBreedDetails>
}