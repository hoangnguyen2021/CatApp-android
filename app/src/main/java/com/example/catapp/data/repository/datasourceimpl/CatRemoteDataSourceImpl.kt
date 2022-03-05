package com.example.catapp.data.repository.datasourceimpl

import com.example.catapp.data.api.CatAPIService
import com.example.catapp.data.model.catbreed.CatBreedDetails
import com.example.catapp.data.model.catbreeds.CatBreeds
import com.example.catapp.data.repository.datasource.CatRemoteDataSource
import retrofit2.Response

class CatRemoteDataSourceImpl(private val catAPIService: CatAPIService) : CatRemoteDataSource {
    override suspend fun getCatBreeds(): Response<CatBreeds> {
        return catAPIService.getCatBreeds()
    }

    override suspend fun getCatBreed(breedId: String): Response<CatBreedDetails> {
        return catAPIService.getCatBreed(breedId)
    }
}